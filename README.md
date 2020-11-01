# Kotlin Android Template

This template provides starting point for Kotlin Android app, following Klika quality guidelines, with implemented authentication following OAuth2 standard.

## Setup

### Dependencies

* Latest Android Studio
* Homebrew (for setup script - required for installation of ktlint)
* Ruby + overcommit gem

```
scripts/setup
```

This script will:
 * Setup overcommit hooks
 * Install ktlint (required for hooks)

Android lint is recommended to be run only on CI, since it can be slow for larger projects, while ktlint can be run on every commit.

### Configuration

To get basic idea about configuration approach read [12factor](https://12factor.net/).

Different build types are expected to have different configuration and it is stored in `.properties` files. Inside app module, by default, `debug.properties` and `release.properties` are expected. Example is provided in `config-example.properties`.

## Getting started

Use Klika quality guidelines for general development references.

### Kotlin styleguides

This project is following **official Kotlin codestyle**, which is also enforced by [ktlint](https://github.com/shyiko/ktlint). It follows both codestyle from [kotlinlang.org](https://kotlinlang.org/docs/reference/coding-conventions.html) and [Android Kotlin styleguide](https://developer.android.com/kotlin/style-guide).

### Naming guidelines

This project is following naming conventions from [ribot android guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md).

### New feature

Use [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow) development workflow with tests included.

### Pull request quality gates

- no conflicts with target branch
- pass CI tests
- code review approval

## Tools

### Networking with OkHttp + Retrofit

Networking is implemented via [Retrofit](https://square.github.io/retrofit/), with Http client provided by [OkHttp](http://square.github.io/okhttp/) that provides easy API communication and response parsing using [Gson](https://github.com/google/gson).

### Databinding + Android Architecture Components ViewModel

MVVM is implemented with help of [databinding](https://developer.android.com/topic/libraries/data-binding/) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) of [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

### Reactive with Kotlin coroutines Flow

Project is implemented with [Kotlin Coroutines + Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html).

### Dependency injection with Dagger2

All dependencies in application are injected using [JSR-330](https://www.jcp.org/en/jsr/detail?id=330)(`@Inject`, `@Singleton`) annotations. [Dagger2](https://google.github.io/dagger/) is used to make all of it easier. 

### Quality gates

This project will run static code analyser on every commit and full test suite on git push.

### Static code analyser

This template is using [ktlint](https://github.com/shyiko/ktlint) for static code analysis. It is also using default [android lint](https://developer.android.com/studio/write/lint). All offenses are automatically tracked and prevented on every commit. This feature is handled by [Overcommit](https://github.com/brigade/overcommit) git hook manager.

### Testing

We are using [JUnit5 Framework](https://junit.org/junit5/docs/current/user-guide/).

### Continuous Integration

[AppCenter](https://appcenter.ms) is recommended for CI.

## Maintainers

- [Ensar Sarajcic](https://github.com/esensar)
