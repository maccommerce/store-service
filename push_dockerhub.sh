#!/bin/bash

RED_COLOR='\033[0;31m'
BLUE_COLOR='\033[0;34m'
GREEN_COLOR='\033[0;32m'
NO_COLOR='\033[0m'
BOLD=$(tput bold)
NORMAL=$(tput sgr0)

if ! [ -x "$(command -v docker)" ]; then
  echo "${RED_COLOR}${BOLD}Error: docker is not installed.${NO_COLOR}${NORMAL}" >&2
  exit 1
fi

PROJECT_VERSION=$(./gradlew properties -q | grep "version:" | awk '{print $2}')

echo "${BLUE_COLOR}${BOLD}Building docker image from Dockerfile...${NO_COLOR}${NORMAL}"
docker build -t maccommerce/store-service:latest -t maccommerce/store-service:"$PROJECT_VERSION" .

echo "${BLUE_COLOR}${BOLD}Pushing docker image to Docker Hub...${NO_COLOR}${NORMAL}"
docker push maccommerce/store-service:latest
docker push maccommerce/store-service:"$PROJECT_VERSION"

echo "${GREEN_COLOR}${BOLD}Done!${NO_COLOR}${NORMAL}"