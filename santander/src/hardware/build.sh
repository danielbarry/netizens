#!/bin/bash

echo ">>>> Removing old files..."
rm *.bin
echo ">>>> Old files removed..."

echo ">>>> Building binary..."
gcc main.c -l fprint -o finger.bin
echo ">>>> Binary built..."

echo ">>>> Changing binary permissions..."
chmod +x finger.bin
echo ">>>> Binary permissions changed..."
