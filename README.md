# random-number-keyboard

How to
To get a Git project into your build:

## Gradle

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```shell
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### Step 2. Add the dependency
```shell
  dependencies {
	        implementation 'com.github.Hexlant:random-number-keyboard:1.0.0'
	}
```


## Maven

### Step 1.
```shell
  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
### Step 2. Add the dependency
```shell
  <dependency>
	    <groupId>com.github.Hexlant</groupId>
	    <artifactId>random-number-keyboard</artifactId>
	    <version>1.0.0</version>
	</dependency>
```
