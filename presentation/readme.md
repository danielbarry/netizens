# LaTeX Presentation Read Me

## Contents

(1) Introduction

(2) Compile

(3) About

## (1) Introduction

The `blank.tex` file has been produced in order to allow for members to produce a LaTeX presentation in the Netizens formatting - white and black colour scheme. By doing this, many useful features are unlocked that allow for the quick production of documents, including the following benefits:

  * Automated contents page
  * Automated bibliography/citing
  * Clear code formatting
  * Consistent document formatting

## (2) Compile

### Linux

A compile will require the `pdflatex` package which can be installed via the following command:

    sudo apt-get install pdflatex

It is then recommended to have a way of reading the PDF file, `evince` is recommended and can be installed with the following command:

    sudo apt-get install evince

A compile must be done twice. The first time produces the documents, the second time links them. You'll see things such as references failing if the document is compiled only once. You can do a compile with the following command:

    pdflatex <DOCUMENT>.tex; pdflatex <DOCUMENT>.tex

If you want to view afterwards, probably worth just stringing the commands:

    pdflatex <DOCUMENT>.tex; pdflatex <DOCUMENT>.tex; evince <DOCUMENT>.pdf

Have fun.

### Windows

Probably.

### Mac

Maybe.

## (3) About

This was produced with extreme prejudice. Just sayin'. I'm sure an unfortunate user of one of those other OSes will be able to update the build instructions for them sooner or later.
