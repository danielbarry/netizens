#!/bin/bash

echo ">>>> Removing old files..."
rm *.bin
echo ">>>> Old files removed..."

echo ">>>> Building binary..."
gcc -g keyboard.c -l X11 -o keyboard.bin
echo ">>>> Binary built..."

echo ">>>> Changing binary permissions..."
chmod +x keyboard.bin
echo ">>>> Binary permissions changed..."
