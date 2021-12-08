#! /bin/bash
rm service-0.0.3.jar
wget https://github.com/momoxiaocaiji/CS655-final/archive/refs/heads/jar.zip
unzip -q -o jar.zip
mv -f CS655-final-jar/service-0.0.3.jar .
mv -f CS655-final-jar/application.yml .
rm -r CS655-final-jar
rm jar.zip
java -jar service-0.0.3.jar
