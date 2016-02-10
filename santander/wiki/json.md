# JSON

## Introduction

We use JSON because of it's simplicity and wide-support. As well as being nicer to view than some languages such as XML, some features such as arrays can also make JSON a much more appropriate choice.

## Submodule

We currently use a simple version of JSON, where a few simple packages are offered allowing for objects and arrays. These allow us to easily use and write the data in JSON files.

## Main Config File

It's very likely that we may have to change very small configuration settings depending on the machines we run on, therefore having the ability to quickly swap out the different modules is very useful.

In the distribution folder (`dist`), you should see that there are multiple `.json` files. The one we're interesting in for the moment is the `main.json`. Here we will look at why things are the way they are and what this allows us to do.

An example of the `main.json` file is the following:

    {
      "main" : {
        "window" : "window.json"
      }
    }

The `"main"` section is telling the reader that this is the correct file. They may be named anything, so the way we check that somebody believes this to be the right type is by defining the type at the start.

Next, we define where the additional settings files are. If for example we want different configurations for different machines, we simply just create a new main file, such as `"main-custom.json"` and link to different configuration files.

## JSON in UI

We use JSON to define the visuals in the UI by specifying how elements behave depending on values given relative to the displays current width and height.

### Buttons

TODO: Write this section.

### Pictures

TODO: Write this section.
