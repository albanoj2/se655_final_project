#!/bin/bash

./script.sh ${1} a
./script.sh ${1} b
./script.sh ${1} f
mongo ycsb --eval "db.usertable.count()"

