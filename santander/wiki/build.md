# Build

## Introduction

This document exists to support new comers to the project in building the source code. In the future, this may be part of a larger build as it's likely we will need to build the code for multiple languages.

## Build Design Choices

### Ant

#### Use

Ant was used not for preference, but for ease. It seemed like the simplest to get going, does everything we need it to for now and has been around for a long time. It's made by Apache, make of that as you will.

#### Folders

The build folders aren't what people are used to seeing, it's just a matter of looking at the minimal amount of text on the screen. The shorter names encapsulate exactly what task they have in the building process.

## Install Build Tools

### Ant

To install `ant` on a Debian system, simply type:

    sudo apt-get update
    sudo apt-get upgrade
    sudo apt-get install ant

## Command Line Build

This should be the same for whichever Operating System you run in.

### Clean

To clean your build environment, simply run:

    ant clean

### Java Docs

To build JavaDocs for the source code, simply run:

    ant doc

### Build Class Files

To build just the class files, run:

    ant compile

### Jar File

To build the runnable jar file, run:

    ant jar

### Run

To run the code, simply run:

    ant

Or:

    ant run
