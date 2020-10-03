
# Jenkins Build Discarder Plugin
![Build](https://github.com/orjan/build-discarder/workflows/Build/badge.svg?branch=master)


## Introduction

TODO Describe what your plugin does here

## Getting started

TODO Tell users how to configure your plugin here, include screenshots, pipeline examples and
configuration-as-code examples.

### Configuration as Code
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
