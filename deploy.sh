#!/usr/bin/env bash

gradle distTar
docker build -t docker.io/thomasmatecki/cs5356:latest .
docker push docker.io/thomasmatecki/cs5356:latest
