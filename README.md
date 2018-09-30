## FinTx Identifier Generator

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-identifier/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/org.fintx/fintx-identifier/)
[![GitHub release](https://img.shields.io/github/release/fintx/fintx-identifier.svg)](https://github.com/fintx/fintx-identifier/releases)
![Apache 2](http://img.shields.io/badge/license-Apache%202-red.svg)
[![Join the chat at https://gitter.im/fintx/fintx-identifier](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/fintx/fintx-identifier?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/fintx/fintx-identifier.svg?branch=master)](https://travis-ci.org/fintx/fintx-identifier)
[![Coverage Status](https://coveralls.io/repos/github/fintx/fintx-identifier/badge.svg)](https://coveralls.io/github/fintx/fintx-identifier)
[![Dependency Status](https://www.versioneye.com/user/projects/598c0fa3368b0838815ce60c/badge.svg?style=flat)](https://www.versioneye.com/user/projects/598c0fa3368b0838815ce60c)


# FinTx[1]

## What's is FinTx?

FinTx is an open source group focus on financial technologies.

## What's is fintx-identifier

fintx-identifier is for generating unique id in high performance and distribution environment. It extends the mongodb's ObjectId that using full MAC address to prevent the duplicated Id. It does not depend on the seeds like snowflake id generator. It can generate both 20 charachers base64 URL safe id (recommend) and 30 characters hex character id. Both id characters are in sequence that not random.

## Structure

<p>Consists of 15 bytes, divided as follows:</p>
<table border="1">
<caption>UniqueId layout</caption>
<tr>
<td>0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td><td>11</td><td>12</td><td>13</td><td>14</td>
</tr>
<tr>
<td colspan="4">time</td><td colspan="6">machine</td><td colspan="2">pid</td><td colspan="3">counter</td>
</tr>
</table>

## Limitations
1. ProcessId on os could not bigger then 65535 (the default max value in most linux OS).    
2. Only in one bundle of same JVM when using OSGI.    
3. Id requirement could not more then about 16777215 per second per JVM.    
4. Maybe it will generate duplicated id every (2^32 - 1)/(60 * 60 * 24 * 365)≈136.19 years with same machine and same process(no possible).

## Using
This is something that you get for free just by adding the following dependency inside your project:

```xml
<dependency>
    <groupId>org.fintx</groupId>
    <artifactId>fintx-identifer</artifactId>
    <version>${latest.version></version>
</dependency>
```
## Example
1. Get a 20 characters length unique id.

```java
String id = UniqueId.get().toBase64String();
```
2. Parse id to get timestamp, machine identifier (physical MAC address), process identifier, counter number.

```java
UniqueId uniqueId = UniqueId.fromBase64String(id);    
long timestamp = uniqueId.getTimestamp();    
long machineId = uniqueId.getMachineIdentifier();    
int processId = uniqueId.getProcessIdentifier();    
long counter = uniqueId.getCounter();    
```

[1] FinTx https://www.fintx.org/    
[2] Maven https://maven.apache.org/    
