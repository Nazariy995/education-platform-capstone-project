#!/bin/bash

rm -rf deploy
mkdir deploy
cp ./target/umd-astronomy-app.jar ./deploy/
cp -r ./src/main/js/ ./deploy/
cp -r ./.ebextensions/ ./deploy/
cd deploy
zip -r ./app.zip .
GLOBIGNORE=*.zip
rm -rf *
unset GLOBIGNORE
