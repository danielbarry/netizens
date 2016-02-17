#########################################
#	A script to see if there are active	#
#	admin panels on a web server. 		#
#										#
#	Usage: 								#
#		python admin_page_scan.py *url*	#
#########################################
import sys
from urllib2 import URLError, HTTPError, Request, urlopen

def search(url):
	f = open("pages.txt", "r")
	for page in f:
		link = "http://"+url+"/"+page
		req = Request(link)
		try:
			urlopen(req)
			print "\033[92m" + link + "\033[0m"
		except (HTTPError, URLError):
			continue

if len(sys.argv) < 2:
	url = raw_input("Site Name:\n> ")
else:
	url = sys.argv[1]

search(url)