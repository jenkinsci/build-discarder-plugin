
# Jenkins Build Discarder Plugin
![Github Build](https://github.com/jenkinci/build-discarder-plugin/workflows/Build/badge.svg?branch=master)
[![Jenkins Build](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/master/)

## Introduction
The build in [Specific Build Discarder](https://github.com/jenkinsci/jenkins/blob/449c5aced523a6e66fe3d6a804e5dbfd5c5c67c6/core/src/main/java/jenkins/model/SimpleGlobalBuildDiscarderStrategy.java)
discards build independent of specific job discarders.

This plugin introduces a new build discarder that will only be applicable if there's no
job specific build discarder specified. This makes it possible to configure it with the
standard log rotator that have a tight retention policy. Then it's possible to override the
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

## Override the settings with a job specific discarder
Adding a [job specific discarder](https://stackoverflow.com/a/44155346) will
make sure the `Default Build Discarder` is not applicable.
```
# Jenkinsfile
pipeline {
  options {
    buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
  }
  ...
}
```

## Contributing
See the default [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) for Jenkins

## Issue
- [Jira](https://issues.jenkins-ci.org/issues/?filter=-1&jql=component%20%3D%20build-discarder-plugin%20)
