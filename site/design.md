# Design

## Introduction

### Aims and Objectives

* Provide links to important resources, including the following:
  * Home page
  * Forum
  * Blogs/projects/updates
  * Event information
* Easily able to add new content
* Easily able to update the stylisation
* Able to run on old machines for backwards compatibility
* Not run Javascript (security)
* Display well on many types of devices

### Risks

1. Hacking into the web server
2. DDoS against the web server
3. Injection into the site

### Risk Management

1. Lock down all ports on web server and implement strong security
2. Limit size of resources for webpage to less than 1kB
3. Only support GET and HEAD commands for the server over HTTPS

## Design

### Basics For Each Page

* Navigation links
* Netizens logo
* Content title
* Stylisation

### Technologies

* Server-side - Any processing on the server will cost us money, having nothing
  is free
* Javascript - Limited and not depended on
* HTML - Of course
* CSS - Lightly, for all stylisation

### Solution

#### Offline Parser

Design and build an offline parser that builds all of the web pages from simple
files, meaning that each page is correctly generated automatically and follows
all changes made. Colouring, etc, can be done in pure HTML without any include
files.
