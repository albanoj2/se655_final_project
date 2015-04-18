#!/bin/bash

redis-cli flushall
redis-cli script flush

./load.sh 1k_1k
./runallworkloads.sh 1k_1k

redis-cli info keyspace > ../Share/keyspace_post_1k_1k.txt

redis-cli flushall
redis-cli script flush

./load.sh 10k_1k
./runallworkloads.sh 10k_1k

redis-cli info keyspace > ../Share/keyspace_post_10k_1k.txt

redis-cli flushall
redis-cli script flush

./load.sh 100k_10k
./runallworkloads.sh 100k_10k

redis-cli info keyspace > ../Share/keyspace_post_100k_10k.txt

redis-cli flushall
redis-cli script flush

./load.sh 1M_100k
./runallworkloads.sh 1M_100k

redis-cli info keyspace > ../Share/keyspace_post_1M_100k.txt

redis-cli flushall
redis-cli script flush

