#!/bin/bash

# build()
#
# Build in the current directory.
#
# @param $1 The location of the style sheet.
# @param $2 The location of the javascript.
# @param $3 The location of the navigation HTML data.
function build {
  for filename in *.md; do
    pandoc --self-contained --standalone --section-divs -c $1 -H $2 -B $3 -o ${filename%.*}.html $filename
  done
}

# clean()
#
# Clean the working directory.
function clean {
  rm *.html
}

# help()
#
# Display the help for this script.
function help {
  echo "bash run.sh <CMD>"
  echo ""
  echo "  ComManD"
  echo ""
  echo "    build    Build the different directories"
  echo "    clean    Clean the directory of build files"
  echo "    help     Display the help"
}

# main()
#
# Handle the main program logic.
#
# @param $@ Arguments from the command line.
function main {
  style="$(pwd)/style.css"
  script="$(pwd)/netizens.js"
  header="$(pwd)/header.html"
  directories[0]="$(pwd)/"
  directories[1]="$(pwd)/blogs"
  # Pre-build the header if required
  if [[ "$1" == "build" ]]; then
    pandoc --self-contained --standalone --section-divs -o header.html header.md_nocompile
  fi
  # Iterate over the directories
  for dir in "${directories[@]}"; do
    cd $dir
    case "$1" in
      build)
        build $style $script $header
        ;;
      clean)
        clean
        ;;
      help)
        help
        exit 1
        ;;
      *)
        echo "Error: Use 'help' for more information"
        exit 1
        ;;
    esac
    cd ${directories[0]}
  done
}

main $@
