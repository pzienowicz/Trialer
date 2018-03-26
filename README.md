# Trialer

[![](https://jitpack.io/v/pzienowicz/Trialer.svg)](https://jitpack.io/#pzienowicz/Trialer)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) 
![GitHub issues](https://img.shields.io/github/issues/pzienowicz/Trialer.svg?style=flat-square)
[![Build Status](https://travis-ci.org/pzienowicz/Trialer.svg?branch=master)](https://travis-ci.org/pzienowicz/Trialer)  


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

    implementation 'com.github.pzienowicz:Trialer:1.0.0'

## Usage

### Kotlin
```java
val trialer = Trialer(this)
trialer.trialEndedListener = object : TrialEndedListener {
	override fun onTrialEnded(validatorClass: ValidatorInterface) {
                Toast.makeText(applicationContext, "Trial ended by: " + validatorClass::class.java.simpleName  , Toast.LENGTH_LONG).show()
        }
}
trialer.addValidator(InstallDateValidator(10))
trialer.addValidator(BuildDateValidator(30))
trialer.addValidator(StaticDateValidator(someFutureDate))
trialer.valid()
```


### Java
```java
Trialer trialer = new Trialer(this);
trialer.setTrialEndedListener(new TrialEndedListener() {
        @Override
        public void onTrialEnded(@NotNull ValidatorInterface validatorClass) {
                Toast.makeText(getApplicationContext(), "Trial ended by: " + validatorClass.getClass().getSimpleName()  , Toast.LENGTH_LONG).show()
	}
});
trialer.addValidator(new InstallDateValidator(10));
trialer.addValidator(new BuildDateValidator(10));
trialer.addValidator(new StaticDateValidator(someFutureDate.getTime()));
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

### CustomValidators
You are able to create your custom validators. Just implement *ValidatorInterface* interface. You can run Trialer with as many validators as you want.

### More validators soon
Feel free to contribute and add your custom validators.
