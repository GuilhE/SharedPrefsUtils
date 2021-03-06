# SharedPrefsUtils ([Kotlin version here!](https://github.com/GuilhE/SharedPrefs-ktx))
[![Build Status](https://travis-ci.org/GuilhE/SharedPrefsUtils.svg?branch=master)](https://travis-ci.org/GuilhE/SharedPrefsUtils) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SharedPrefsUtils-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6114)

Save and load objects from SharedPreferences in a faster and simpler way.

#### Version 1.x
    - November, 2018    - "Rebranding", changed lib name.
    - November, 2017    - Gradle update, added Codacy.
    - August, 2017      - SharedPrefsUtils.

*__NOTE:__ this repo will not be updated you should switch to [kotlin version](https://github.com/GuilhE/SharedPrefs-ktx)*

## Getting started

The first step is to include SharedPrefsUtils into your project, for example, as a Gradle compile dependency:

```groovy
implementation 'com.github.guilhe:SharedPrefsUtils:${LATEST_VERSION}'
```
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe/SharedPrefsUtils/badge.svg)](https://search.maven.org/search?q=a:SharedPrefsUtils)  [ ![Download](https://api.bintray.com/packages/gdelgado/android/SharedPrefsUtils/images/download.svg) ](https://bintray.com/gdelgado/android/SharedPrefsUtils/_latestVersion)
## Sample usage

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
        SharedPrefsUtils.putObject(prefs, "key", list);
        
        assertEquals(list, SharedPrefsUtils.getObject(prefs, "key", new TypeToken<List<Integer>>(){}, new ArrayList<Integer>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(prefs, "key", List.class, new ArrayList<Integer>()));
    }
    
    @Test
    public void testTwo() throws Exception {
        List<MyObjectType> list = new ArrayList<>();
        list.add(new MyObjectType("string", 1, true));
        SharedPrefsUtils.putObject(prefs, "key", list);
        
        assertEquals(list, SharedPrefsUtils.getObject(prefs, "key", new TypeToken<List<MyObjectType>>() {}, new ArrayList<MyObjectType>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(prefs, "key", List.class, new ArrayList<MyObjectType>()));
    }
    
    private static class MyObjectType implements Parcelable {
            private String mFieldString;
            private int mFieldInt;
            private boolean mFieldBoolean;
            ...
    }
```
Both tests will ran to completion.

Regarding `assertNotEquals(list, SharedPrefsUtils.getObject(prefs, "key", List.class, new ArrayList<Integer>()));` being true, I guess it's related with the fact that `public <T> T fromJson(JsonReader reader, Type typeOfT){}` method from `Gson.java` (line 886) is type unsafe\:
 _"Since Type is not parameterized by T, this method is type unsafe and should be used carefully"_.
 That's why I believe I'm getting `List<Double>` instead of `List<Integer>`.

Also:
```java
SharedPrefsUtils.putObject(prefs, "key", 1);
SharedPrefsUtils.getObject(prefs, "key", boolean.class, false);
```

Will throw `JsonParseException`.


## Binaries

Additional binaries and dependency information for can be found at [https://search.maven.org](https://search.maven.org/search?q=a:SharedPrefsUtils).

<a href='https://bintray.com/gdelgado/android/SharedPrefsUtils?source=watch' alt='Get automatic notifications about new "SharedPrefsUtils" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a>

## Dependencies

- [Gson](https://github.com/google/gson)

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GuilhE/SharedPrefsUtils/issues).

 
## LICENSE

Copyright (c) 2017-present, SharedPrefsUtils Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
