
# Jenkins Build Discarder Plugin
![Build](https://github.com/orjan/build-discarder/workflows/Build/badge.svg?branch=master)

## Introduction
The build in [Specific Build Discarder](https://github.com/jenkinsci/jenkins/blob/449c5aced523a6e66fe3d6a804e5dbfd5c5c67c6/core/src/main/java/jenkins/model/SimpleGlobalBuildDiscarderStrategy.java)
discards build independent of specific job discarders.

This plugin introduces a new build discarder that will only be applicable if there's not
job specific build discarder specified. This makes it possible to configure it with the
standard log rotator have a tight retention policy. Then it's posssible to override the
default setting by creating job specific discarders.

## Getting started
The `Default Build Discarder` is found under the `Manage Jenkins >> Configure System >> Global Build Discarders`
![Alt text](docs/img/configure-default-discarder.png?raw=true "Title")

### Configuration as Code
Add `defaultBuildDiscarder` to your `configuredBuildDiscarders` with the standard `logRotator` settings.
```
unclassified:
  buildDiscarders:
    configuredBuildDiscarders:
    - "jobBuildDiscarder"
    - defaultBuildDiscarder:
        discarder:
          logRotator:
            artifactDaysToKeepStr: "50"
            artifactNumToKeepStr: "5"
            daysToKeepStr: "100"
            numToKeepStr: "10"
```

## Contributing
See the default [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) for Jenkins
