./bin/ycsb load mongodb -s -P workloads/${1}/workloada > outputLoad.txt
mongo ycsb --eval "db.usertable.count()"
