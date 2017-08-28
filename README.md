# Android Utils:
[![Build Status](https://travis-ci.org/GuilhE/android-utils-lib.svg?branch=master)](https://travis-ci.org/GuilhE/android-utils-lib)  [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe/android-utils/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.guilhe%22a%3A%22android-utils%22)  [ ![Download](https://api.bintray.com/packages/gdelgado/android/android-utils/images/download.svg) ](https://bintray.com/gdelgado/android/android-utils/_latestVersion)  [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--utils--lib-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6114)

A set of utility classes to hopefully help you save your time. 

#### Version 1.x

  - **August, 2017** - SharedPrefsUtils


## Getting started

The first step is to include AndroidUtils into your project, for example, as a Gradle compile dependency:

```groovy
compile 'com.github.guilhe:android-utils:1.0.0'
```

The second is to start using it :)

#### SharedPrefsUtils

```java
public static boolean putObject(SharedPreferences prefs, String key, Object object) {}
public static <T> T getObject(SharedPreferences prefs, String key, TypeToken<T> type, T defaultValue) {}
public static <T> T getObject(SharedPreferences prefs, String key, Class<T> object, T defaultValue) {}
```

To save and load primitive types:
```java
SharedPrefsUtils.putObject(prefs, "key", 1);
int a = SharedPrefsUtils.getObject(prefs, "key", int.class, 1);
```

To save and load object types:
```java
List<T> list = new ArrayList<>();
SharedPrefsUtils.putObject(prefs, "key", list);
list = SharedPrefsUtils.getObject(prefs, "key", new TypeToken<List<T>>(){}, new ArrayList<T>()));
```

When __not__ using primitive types you should use `TypeToken` instead of `T.class`, for example:
```java
    @Test
    public void testOne() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        SharedPrefsUtils.putObject(mPrefs, "key", list);
        
        assertEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", new TypeToken<List<Integer>>(){}, new ArrayList<Integer>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", List.class, new ArrayList<Integer>()));
    }
    
    @Test
    public void testTwo() throws Exception {
        List<MyObjectType> list = new ArrayList<>();
        list.add(new MyObjectType("string", 1, true));
        SharedPrefsUtils.putObject(mPrefs, "key", list);
        
        assertEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", new TypeToken<List<MyObjectType>>() {}, new ArrayList<MyObjectType>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", List.class, new ArrayList<MyObjectType>()));
    }
    
    private static class MyObjectType implements Parcelable {
            private String mFieldString;
            private int mFieldInt;
            private boolean mFieldBoolean;
            ...
    }
```
Both tests will ran to completion.

Regarding `assertNotEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", List.class, new ArrayList<Integer>()));` being true, I guess it's related with the fact that `public <T> T fromJson(JsonReader reader, Type typeOfT){}` method from `Gson.java` (line 886) is type unsafe\:
 _"Since Type is not parameterized by T, this method is type unsafe and should be used carefully"_.
 That's why I believe I'm getting `List<Double>` instead of `List<Integer>`.

Also:
```java
SharedPrefsUtils.putObject(prefs, "key", 1);
SharedPrefsUtils.getObject(prefs, "key", boolean.class, false);
```

Will throw `JsonParseException`.


## Binaries

Binaries and dependency information for Gradle, Maven, Ivy and others can be found at [https://search.maven.org](https://search.maven.org/#artifactdetails%7Ccom.github.guilhe%7Candroid-utils%7C1.0.0%7Caar).

For Gradle:

```groovy
repositories {
    jcenter()
 }

dependencies {
    compile 'com.github.guilhe:android-utils:1.0.0'
}
```

and for Maven:
```groovy
<dependency>
    <groupId>com.github.guilhe</groupId>
    <artifactId>android-utils</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

and for Ivy:
```groovy
<dependency org='com.github.guilhe' name='android-utils' rev='1.0.0'>
  <artifact name='android-utils' ext='pom' ></artifact>
</dependency>
```

<a href='https://bintray.com/gdelgado/android/android-utils?source=watch' alt='Get automatic notifications about new "android-utils" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a>

## Dependencies

- [Gson](https://github.com/google/gson)

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GuilhE/android-utils-lib/issues).

 
## LICENSE

Copyright (c) 2017-present, AndroidUtils Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
