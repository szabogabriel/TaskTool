#!/bin/bash

source ./env.sh

check_env() {
  if ! command -v docker &> /dev/null; then
    COMMAND_FOR_CONTAINER_BUILD=podman
    if ! command -v podman &> /dev/null; then
      echo "Neither Docker nor Podman is installed. Please install one of them to proceed."
      exit 1
    fi
  fi
}


run_conatiner() {
  $COMMAND_FOR_CONTAINER_BUILD run -e SPRING_PROFILES_ACTIVE=prod --rm -p 8080:8080 ${TARGET_CONTAINER_NAME}
}

main() {
  check_env
  run_conatiner
}

main $@
