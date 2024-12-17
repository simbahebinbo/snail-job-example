#!/usr/bin/env bash

CurrentDir=$(dirname $0)

find ${CurrentDir} -name "target" |  xargs rm -rf $(xargs)
mvn clean package -DskipTests=true



