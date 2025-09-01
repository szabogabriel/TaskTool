#!/bin/bash

FLAG_BUILD=true
FLAG_PACKAGE=true

init () {
    source ./env.sh
}

check_args() {
  while [[ $# -gt 0 ]]; do
    case $1 in
      --skip-build) FLAG_BUILD=false ;;
      --skip-package) FLAG_PACKAGE=false ;;
    esac
    shift
  done
}

check_env() {
  if [ "$FLAG_PACKAGE" = false ] ; then
    echo "Skipping package step."
    return
  fi
  if ! command -v docker &> /dev/null; then
    COMMAND_FOR_CONTAINER_BUILD=podman
    if ! command -v podman &> /dev/null; then
      echo "Neither Docker nor Podman is installed. Please install one of them to proceed."
      exit 1
    fi
  fi
}

build_app() {
  if [ "$FLAG_BUILD" = false ] ; then
    echo "Skipping build step."
    return
  fi
  mvn clean package
}

build_container_image() {
  if [ "$FLAG_PACKAGE" = false ] ; then
    echo "Skipping package step."
    return
  fi
  $COMMAND_FOR_CONTAINER_BUILD build -t ${TARGET_CONTAINER_NAME} .
}

main() {
  init
  check_args $@
  check_env
  build_app
  build_container_image
}

main $@