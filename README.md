# jcprogress

Java Console Progress Indicator

Provides a java library for easily displaying different progress indicators on the console.

## getting started

Type `./gradlew jar` to compile and run
`java -jar demo/build/libs/demo.jar` to start a little demonstration (CTRL-C to abort)

The Code of the demonstration is in `demo/src/main/java/com/github/pmairif/jcprogress/demo/ConsoleTest.java`.
More usage examples can be found in `demo/.../ConsoleDemo.java`, which can be started with
`java -cp demo/build/libs/demo.jar com.github.pmairif.jcprogress.demo.ConsoleDemo`.

Have fun!

## use in your project

via gradle:
```
implementation 'com.github.pmairif:jcprogress:1.0.0'
```
or maven:
```
<dependency>
  <groupId>com.github.pmairif</groupId>
  <artifactId>jcprogress</artifactId>
  <version>1.0.0</version>
</dependency>
```
