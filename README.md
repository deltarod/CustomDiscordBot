My custom discord bot, commands are VERY basic for now

![alt text](https://cdn.discordapp.com/avatars/105555777107841024/1a7d122e5e343173d53ed509f8f7afb9.png)

Here is my pom for maven
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.javabot</groupId>
    <artifactId>discordbot</artifactId>
    <version>1.0.0</version>


    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.austinv11</groupId>
            <artifactId>Discord4J</artifactId>
            <version>2.7.0</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>


</project>
```