#!/bin/bash
#title          ssh-tunnel.sh
#description    Change Media Access Control address of a specific network interface.
#author:        Robert Northard
#usage          ./macchange.sh -i <interface> -m <new mac address>
#
if [ "$(id -u)" != "0" ]; then
	echo "This script must be run as root."
	exit 1
fi

usage() { echo "[!] Usage: $0 [-i interface] [ -m new mac address]" 1>&2; exit 1; }

while getopts ":i:m:" o; do
    case "${o}" in
        i)
            i=${OPTARG}
            ;;
        m)
            m=${OPTARG}
            ;;
        *)
            usage
            ;;
    esac
done

if [ -z "${i}" ] || [ -z "${m}" ]; then
    usage
fi

previous_mac=`ifconfig ${i} | grep 'ether' | awk '{print $2}'`
echo "[+] Previous MAC: ${previous_mac}"

ifconfig ${i} ether ${m}

new_mac=$(ifconfig ${i} | grep 'ether' | awk '{print $2}')

if [ "$oldmac" = "$new_mac" ]; then
	echo "[!] MAC not changed."
	exit 1
else
    echo "[+] New mac: ${new_mac}"
    exit 0
fi

