#!/bin/bash

myconfsdir=~/.kilo

mkdir -p "$myconfsdir"
mkdir -p "$myconfsdir-test"

cp -vi sample-confs/* "$myconfsdir"
cp -vi sample-confs/* "$myconfsdir-test"
