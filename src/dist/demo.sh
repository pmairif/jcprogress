#!/bin/bash

CP=jcprogress.jar
for i in lib/*.jar; do
  CP=$CP:$i
done

java -cp $CP com.github.pmairif.jcprogress.demo.ConsoleDemo
