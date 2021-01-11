![Build](https://img.shields.io/badge/Build-Automated-2980b9.svg?style=for-the-badge)
![Latest Version](https://img.shields.io/badge/Latest_Version-v1.0.4-27ae60.svg?style=for-the-badge)
![License](https://img.shields.io/badge/License-Apache_2.0-e74c3c.svg?style=for-the-badge)</br>
![Java CI with Gradle](https://github.com/myConsciousness/entity-validator/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=main)

# Envali: Entity Validator

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

**Table of Contents**

- [What is it?](#what-is-it)
- [Benefits](#benefits)
- [How To Use](#how-to-use)
  - [1. Add the dependencies](#1-add-the-dependencies)
- [License](#license)
- [More Information](#more-information)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## What is it?

**_Provides intuitive and productive Entity validation capabilities!_**

`Envali` is a Java-based framework that provides an intuitive and productive way to validate Entity's field elements.</br>
Validation items can be configured by adding annotations to the target field in `AOP`.

## Benefits

- Intuitive addition of validation annotations
- Easy to manage validation items per Entity
- Safe execution of the validation by the framework
- Improve source code maintainability by centralizing the validation

## How To Use

### 1. Add the dependencies

> **_Note:_**</br>
> Replace version you want to use. Check the latest [Packages](https://github.com/myConsciousness/entity-validator/packages).</br>
> Please contact me for a token to download the package.

**_Maven_**

```xml
<dependency>
  <groupId>org.thinkit.framework.envali</groupId>
  <artifactId>entity-validator</artifactId>
  <version>v1.0.4</version>
</dependency>

<servers>
  <server>
    <id>github</id>
    <username>myConsciousness</username>
    <password>xxxxxxxxxxxxxxxxxx</password>
  </server>
</servers>
```

**_Gradle_**

```gradle
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/myConsciousness/entity-validator")
        credentials {
          username = "myConsciousness"
          password = "xxxxxxxxxxxxxxxxxx"
        }
    }
}

dependencies {
    implementation 'org.thinkit.framework.envali:entity-validator:v1.0.4'
}
```

## License

```license
Copyright 2020 Kato Shinya.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
```

## More Information

`Envali: Entity Validator` was designed and implemented by Kato Shinya, who works as a freelance developer.

Regardless of the means or content of communication, I would love to hear from you if you have any questions or concerns. I do not check my email box very often so a response may be delayed, anyway thank you for your interest!

- [Creator Profile](https://github.com/myConsciousness)
- [Creator Website](https://myconsciousness.github.io/)
- [License](https://github.com/myConsciousness/entity-validator/blob/master/LICENSE)
- [Release Note](https://github.com/myConsciousness/entity-validator/releases)
- [Package](https://github.com/myConsciousness/entity-validator/packages)
- [File a Bug](https://github.com/myConsciousness/entity-validator/issues)
- [Reference](https://myconsciousness.github.io/entity-validator/)
