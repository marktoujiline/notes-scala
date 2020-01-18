#!/bin/sh

openssl aes-256-cbc -K $encrypted_40bed682e53c_key -iv $encrypted_40bed682e53c_iv -in travis-deploy-key.enc -out travis-deploy-key -d
chmod 600 travis-deploy-key;
cp travis-deploy-key ~/.ssh/id_rsa;