#!/usr/bin/pythonw

#========================================================
#title          :live.py
#description    :scan subnet for live nodes
#author:        :s.vidalis@herts.ac.uk
#timestamp      :201512162003Z
#version        :0.0.1
#usage          :python live.py -N <target network>
#notes          :scans subnet for live nodes
#========================================================

import optparse, nmap, ipaddress

def validateIP(ip):
	a = ip.split('.')
	if len(a) != 4:
		return False
	for x in a:
		if not x.isdigit():
			return False
		i = int(x)
		if i < 0 or i > 255:
			return False
	return True

def main():
	parser = optparse.OptionParser('usage %prog ' + '-N <target network>')
	parser.add_option('-N', dest='tgtNet', type='string', help='specify target network')
	(options, args) = parser.parse_args()
	tgtNet = options.tgtNet
	if (tgtNet == None):
		print(parser.usage)
		exit(0)

	if not validateIP(tgtNet):
		print('Invalid IP address\n')
		exit(0)
	
	tgtNet = '.'.join(str(ipaddress.IPv4Address(tgtNet)).split('.')[0:3]) + '.1/24'

	nm = nmap.PortScanner()
	nm.scan(hosts=tgtNet, arguments='-sn -PS')
	for host in nm.all_hosts():
		print(host)

if __name__ == '__main__':
	main()
