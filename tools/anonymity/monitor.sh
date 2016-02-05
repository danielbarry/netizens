#!/bin/bash

# Start graphical firewall window
gufw &

# Check for blocked connections
tail -f /var/log/ufw.log | GREP_COLOR='01;31' egrep --color=always 'BLOCK|$'

# Should never get here
echo "!!SHOULD NEVER GET HERE!!"
