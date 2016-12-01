#!/bin/sh
echo "Compiling source files"
javac -d bin code/*.java
echo "Compilation successful"
echo "Creating index and secondary index files" 
#java -Xmx5220m -cp bin iiit.ire.miniproj.indexer.main
java -Xmx5220m -cp bin iiit.ire.miniproj.queryprocessor.WikiSearch
