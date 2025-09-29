package com.ibm.example;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class H2Database implements Closeable {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS LOG_ENTRIES (" +
            "ID BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "TS TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            "CONTENT VARCHAR(4000) NOT NULL" +
            ")";

    private static final String INSERT_SQL = "INSERT INTO LOG_ENTRIES (CONTENT) VALUES (?)";

    private final Connection conn;

    public H2Database(String filePath) {
        String jdbcUrl = buildUrl(Objects.requireNonNull(filePath));
        System.out.println("Creating H2Database with URL: " + jdbcUrl);
        try {
            Class.forName("org.h2.Driver"); // H2 driver auto-registers since JDBC 4, but explicit load doesn't hurt:
            this.conn = DriverManager.getConnection(jdbcUrl, Config.DB_USERNAME.value(), Config.DB_PASSWORD.value());
            initSchema();

            org.h2.tools.Server web = org.h2.tools.Server.createWebServer("-web", "-webPort", Config.H2_WEB_PORT.value(), "-webDaemon").start();

            System.out.println("H2 Web Console started and listening on port " + Config.H2_WEB_PORT.value());

            Runtime.getRuntime().addShutdownHook(new Thread(web::stop));
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));
        } catch (Exception e) {
            throw new RuntimeException("Failed to open H2 database", e);
        }
    }

    private static String buildUrl(String filePath) {
        // Sensible defaults for a dev/demo DB:
        // - file mode (persists to disk)
        // - DB_CLOSE_DELAY=-1 keeps DB alive while JVM runs
        // - AUTO_SERVER=TRUE allows reconnects from other processes if needed
        // (optional)
        return "jdbc:h2:file:" + filePath + ";DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
    }

    private void initSchema() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute(CREATE_TABLE_SQL);
        }
    }

    public long log(String content) {
        Objects.requireNonNull(content, "content");
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, content);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new SQLException("No generated key returned for LOG_ENTRIES insert");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert log entry", e);
        }
    }

    @Override
    public void close() {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException ignored) {
        }
    }

}
