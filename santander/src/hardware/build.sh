#!/bin/bash

rm *.bin

gcc main.c -l fprint -o finger.bin
chmod +x finger.bin
