'''
title          webspider.py
description    Spider a given URL.
author:        Robert Northard
usage          ./webspider.py <url>
'''

import urllib.request
import sys

def get_page(url):
    try:
        return str(urllib.request.urlopen(url,timeout=1).read())
    except:
        return ''

def find_links(page):
    LINK_START = '<a href='
    LINK = '"'

    links = []

    start_link = page.find(LINK_START)
    while start_link != -1:
        start_quote = page.find(LINK, start_link)
        end_quote = page.find(LINK, start_quote + 1)
        start_link = page.find(LINK_START, end_quote + 1)
        links.append(page[start_quote + 1 :end_quote])

    return links

def main():
    if len(sys.argv) != 2:
        print("Usage: python webspider.py <url>")
        exit(1)

    url = sys.argv[1]

    print("####### Starting crawler #######")
    
    depth = [url]
    to_crawl = []
    crawled = []

    level = 0
    while level <= 1:
        while depth:
            url = depth.pop()
            print(url)
            if url not in crawled:
                content = get_page(url)
                if content != '':
                    to_crawl = to_crawl + find_links(content)
                    crawled.append(url)

        depth = to_crawl
        to_crawl = []
        level = level + 1
    
    print("####### Links found #######")
    print(crawled)

if __name__ == '__main__':
    main()
