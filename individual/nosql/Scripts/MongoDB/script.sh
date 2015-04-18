#!/bin/bash

mkdir -p ../Share/${1}
mkdir -p ../Share/${1}/outputs
mkdir -p ../Share/${1}/outputs/workload_${2}

for ((i=0; i < 30; i++)); do
	echo "Repetition #${i}"

	./bin/ycsb run mongodb -s -P workloads/${1}/workload$2 > outputRun.txt

	time=`grep 'Time' outputRun.txt | cut -d ' ' -f 3`
	time+=","
	#mkdir -p ../Share/${1}
	echo $time >> ../Share/${1}/workload_${2}.csv
	#put outputRun.txt into folder as well
	cp outputRun.txt ../Share/${1}/outputs/workload_${2}/rep_${i}_output.txt

done

