# Read Me

## Contents

  1. Introduction
  2. Required Packages
  3. Making Binary

## Introduction

This document should give information about building the software required for the finger print scanner.

## Required Packages

The following packages are required to be installed:

    sudo apt-get install libfprint0 libfprint-dev cmake ccmake

Once these are installed, you should be in a state to build the binary for the program.

## Making Binary

The below are instructions on how to make in both cases of first use and regular use.

### First Make

On the first make, you'll need to prepare the make system. You can do this with the following code:

    ccmake .

Press `c` to configure, then `g` to generate. If you get some error, don't worry, just `e` to exit and go back through those menus.

You should now be ready for the fun stuff.

### Normal Make

This should simply be a case of running the following command:

    make

To clean the directory, please run:

    make clean

For a faster compile, please run:

    make -j<N>

Where `<N>` is the number of cores your computer has plus one. (NOTE: This is to fully optimise the use of your machine due to disk operations locking compiling on cores in the make software.)
