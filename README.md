Add it in your root build.gradle at the end of repositories:
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency
```java
dependencies {
	        implementation 'com.github.yudam:IMRouter:Tag'
	}
```
