#!/bin/bash
set -e

git config --global user.email "mark.toujiline@gmail.com"
git config --global user.name "marktoujiline"
git config --global push.default simple

sbt docs/publishMicrosite