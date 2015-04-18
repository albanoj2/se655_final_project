./bin/ycsb load redis -s -P workloads/${1}/workloada -p "redis.host=127.0.0.1" -p "redis.port=6379" > outputLoad.txt
redis-cli info keyspace

