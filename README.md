# Trialer

[![](https://jitpack.io/v/pzienowicz/Trialer.svg)](https://jitpack.io/#pzienowicz/Trialer)
[![](https://jitpack.io/v/pzienowicz/Trialer/month.svg)](https://jitpack.io/#pzienowicz/Trialer) 
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) 
![GitHub issues](https://img.shields.io/github/issues/pzienowicz/Trialer.svg?style=flat-square)
[![Build Status](https://travis-ci.org/pzienowicz/Trialer.svg?branch=master)](https://travis-ci.org/pzienowicz/Trialer)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Trialer-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7567)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-brightgreen.svg)](https://raw.githubusercontent.com/pzienowicz/Trialer/master/LICENSE)


A small and simple library for managing trial period in your android app.

Installation
------------

### Gradle
Add this to your root build.gradle file under repositories:
```
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
Add this to your app level build.gradle as dependency:

    implementation 'com.github.pzienowicz:Trialer:{latest.version}'
Latest version: ![](https://jitpack.io/v/pzienowicz/Trialer.svg)

## Usage

### Kotlin
```java
val trialer = Trialer(this)
trialer.trialEndedListener = object : TrialEndedListener {
	override fun onTrialEnded(validatorClass: ValidatorInterface) {
        Toast.makeText(
            applicationContext, 
            "Trial ended by: " + validatorClass::class.java.simpleName, 
            Toast.LENGTH_LONG
        ).show()
    }
}
trialer.addValidator(InstallDateValidator(10))
trialer.addValidator(StaticDateValidator(someFutureDate))
trialer.addValidator(RunTimesValidator(5))
trialer.addValidator(YourCustomValidator())
trialer.addAction(DisplayToastAction(context, "Toast by DisplayToastAction"))
trialer.valid()
```


### Java
```java
Trialer trialer = new Trialer(this);
trialer.setTrialEndedListener(validatorClass ->
    Toast.makeText(
        getApplicationContext(),
        "Trial ended by: " + validatorClass.getClass().getSimpleName(),
        Toast.LENGTH_LONG
    ).show()
);
trialer.addValidator(new InstallDateValidator(10));
trialer.addValidator(new StaticDateValidator(someFutureDate.getTime()));
trialer.addValidator(new RunTimesValidator(5));
trialer.addValidator(new YourCustomValidator());
trialer.addAction(new DisplayToastAction(context, "Toast by DisplayToastAction"))
trialer.valid();
```

## Validators

### StaticDateValidator
Simple validator - it fails if given date is in the past.
```java
val someFutureDate = Calendar.getInstance()
someFutureDate.add(Calendar.DATE, 30)
...
trialer.addValidator(StaticDateValidator(someFutureDate.time))
...
```

### InstallDateValidator
It fails when X days have passed since installation.
```java
val days = 10 //Int
...
trialer.addValidator(InstallDateValidator(days))
...
```

### RunTimesValidator
It fails when X times your activity has been launched.
```java
val runTimes = 5 //Int
...
trialer.addValidator(RunTimesValidator(runTimes))
...
```

### CustomValidators
You are able to create your custom validators. Just implement *ValidatorInterface* interface. You can run Trialer with as many validators as you want.

### More validators soon
Feel free to contribute and add your custom validators.


## Actions

If you don't want to use TrialEndedListener, you can just use simple action, which will be run if trial ended.
For now we have few actions specified below. If these actions don't meet your needs, please implement ActionInterface and create your own action.

### FinishActivityAction
Closes activity if trial ended.
```java
trialer.addAction(FinishActivityAction(activity))
```

### DisplayToastAction
Displays toast message if trial ended.
```java

trialer.addAction(DisplayToastAction(context, "Toast by DisplayToastAction"))
```
