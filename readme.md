ReadMe
======

Contents
--------

1. Introduction
2. Compiling
3. Development
4. About

1) Introduction
---------------

### What is the repository about?

This repository has been created to capture the code written by the pen-testing team in order to collaborate and improve ideas.

### History

The group was started in November 2015 by Stilianos Vidalis as a pre-cursor to the Security orientated course at the University of Hertfordshire.

2) Compiling
------------

### Requirements

#### Ubuntu

The very basics you will require is the following:

    sudo apt-get install cmake ccmake gcc g++ git python

To compile documents you will require:

    sudo apt-get install pandoc

### Building C/C++ files

To compile, you'll first need to create a `MakeFile` with the following command in the directory you want to compile:

    cmake .

Optionally, you can set the flags of the compiler with the following command:

    ccmake .

Then you'll want to build the files:

    make

You should now have built the files correctly.

3) Development
--------------

For files that are yet to be matured, please use the directory `test`. When files become more mature they can be moved into a more appropriate folder.

4) About
--------

A University run pen-test group by the name "Netizens", meaning "Citizens of the internet".
