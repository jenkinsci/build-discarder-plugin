
# Jenkins Build Discarder Plugin

[![Github Build](https://github.com/jenkinsci/build-discarder-plugin/actions/workflows/cd.yaml/badge.svg?branch=main)](https://github.com/jenkinsci/build-discarder-plugin/actions/workflows/cd.yaml)
[![Jenkins Build](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/main/badge/icon)](https://ci.jenkins.io/job/Plugins/job/build-discarder-plugin/job/main/)
[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/build-discarder.svg)](https://plugins.jenkins.io/build-discarder)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/build-discarder-plugin.svg?label=changelog)](https://github.com/jenkinsci/build-discarder-plugin/releases/latest)

## Introduction

A global build discarder that's possible to override by adding a job
specific discarder. The goal is to be able to have a global sensible default, that's possible to override if needed. The minimum supported version of Jenkins is `2.346.3`, the goal is to support the two latest versions of Jenkins LTS.

## Getting started

The `Default Build Discarder` can be added here:  

`Manage Jenkins >> Configure System >> Global Build Discarders`  

![Alt text](docs/img/configure-default-discarder.png?raw=true "Title")

### Configuration as Code

Add `defaultBuildDiscarder` to your `configuredBuildDiscarders` with the standard `logRotator` settings.

```yml
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

```groovy
# Jenkinsfile
pipeline {
  options {
    buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
  }
  ...
}
```

## Background

The built in [Specific Build Discarder](https://github.com/jenkinsci/jenkins/blob/449c5aced523a6e66fe3d6a804e5dbfd5c5c67c6/core/src/main/java/jenkins/model/SimpleGlobalBuildDiscarderStrategy.java) discards builds independent of specific job discarders.

This discarder will not discard any builds if there's a job specific discarder specified.
To goal is to be able provide as sensible default that can be overriden on job level when needed.

## Contributing

This plugin is probably close to done but if you have an idea, please open an issue so we can
discuss the extension.

See the default [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md) for Jenkins

## Local development

Start Jenkins by running this command

```bash
mvn hpi:run
```

## Release management

The releases is automated via the the github action, using the [JEP-229: Continuous Delivery of Jenkins Components and Plugins](https://github.com/jenkinsci/jep/blob/master/jep/229/README.adoc) flow.

### Supported versions of Jenkins

We're trying to support the last two LTS versions of Jenkins, see the [offical documentation](https://www.jenkins.io/doc/developer/plugin-development/choosing-jenkins-baseline/) for further details.

Upgrading is done by changing the jenkins version with the matching [bom](https://github.com/jenkinsci/bom). See this [example](https://github.com/jenkinsci/build-discarder-plugin/pull/32/files#diff-9c5fb3d1b7e3b0f54bc5c4182965c4fe1f9023d449017cece3005d3f90e8e4d8R17-R28) for how to update
the version.
