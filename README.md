
# Jenkins Build Discarder Plugin
![Github Build](https://github.com/jenkinsci/build-discarder-plugin/workflows/Build/badge.svg?branch=master)
[![Jenkins Build](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/master/badge/icon)](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/master/)
[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/build-discarder.svg)](https://plugins.jenkins.io/build-discarder)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/build-discarder-plugin.svg?label=changelog)](https://github.com/jenkinsci/build-discarder-plugin/releases/latest)

## Introduction
A global build discarder that's possible to override by adding a job
specific discarder. The goal is to be able to have a global sensible default, that's possible to override if needed.

## Getting started
The `Default Build Discarder` can be added here:   
`Manage Jenkins >> Configure System >> Global Build Discarders`
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

## Background
The build in [Specific Build Discarder](https://github.com/jenkinsci/jenkins/blob/449c5aced523a6e66fe3d6a804e5dbfd5c5c67c6/core/src/main/java/jenkins/model/SimpleGlobalBuildDiscarderStrategy.java)
discards build independent of specific job discarders.

## Contributing
See the default [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) for Jenkins

### Local development
Start Jenkins by running this command
```
mvn hpi:run
```
