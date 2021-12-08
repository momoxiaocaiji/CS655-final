#! /bin/bash
rm -r Worker
wget https://github.com/momoxiaocaiji/CS655-final/archive/refs/heads/worker.zip
unzip -q -o worker.zip
mv -f CS655-final-worker/Worker .
rm -r CS655-final-worker
rm worker.zip
cd Worker/src
javac -cp .:../lib/commons-codec-1.9.jar worker.java
java -cp .:../lib/commons-codec-1.9.jar worker
