![logo](https://user-images.githubusercontent.com/30006970/125198167-1c557600-e261-11eb-8086-eaf5e631d5b1.png)

# Rollback

Rollback is simple application, which uses [**Fake store API**](https://fakestoreapi.com/), to serve as demo e-commerce application which allows users to login to existing account; preview list of products, add and delete product.

## Disclaimer

The main purpose of the project, which follows variation of Clean Architecture, is to demonstrate how to break your application into several modules, how to use them, how to split the work and via dependency injection and interfaces use functions, classes and their methods etc. as sort of a black box.

## Install

Clone the repository, and run the project, no additional setup is needed. Simple as that.

## Clean architecture
This project is loosely following clean architecture approach as recommended by [Antonio Levia](https://antonioleiva.com/) in his article [Clean architecture for Android with Kotlin: a pragmatic approach for starters](https://antonioleiva.com/clean-architecture-android/). The idea is to split your app into several modules and via dependency injection and interfaces provide their usage (what we said in the intorduction).
Modules are as follows:
##### 1. data
All data related, from repositories, API calls, mappers for our models, datasource (only its interface) is in here. Essentially core of the app is in this module
##### 2. domain
Our response and request models, as well as our mapping classes.
##### 3. usecases
Given we have many API calls, to handle them nicely, we split each call into its use-case. That way we can focus on single flow.
##### 4. app - standard
Here we provide an implementation for our datasource (this can be local and/or remote); because we implement it in the app module we can use Android specific components (interface is in data module) so the data module (or core) is still isolated, while specific client provides the implementation however they want.
And here are all UI parts (fragments, view models, and adapters).

## Flow
Flow in this case is pretty simple:
api call -> datasource -> repository -> usecase -> viewmodel -> fragment

Since we, due to use-cases, only focus on one flow, following this won't be an issue. And we handle data, however we want, so we know what we get back (for instance, in datasource, we might want to re-map our response to some other model because we won't use all that information).

## Architecture

This app uses [***MVI (Model View Intent)***](https://hannesdorfmann.com/android/mosby3-mvi-1/) architecture.

![](https://miro.medium.com/max/500/1*yqiynGx9AADPPT52b37idQ.png)

## Contribute

If you want to contribute to this repository, you're always welcome!

## Contact

If you need any help, feel free to contact me: kenan.karic@outlook.com.

## License
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
