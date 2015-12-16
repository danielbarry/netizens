#!/bin/bash
#title          ssh-tunnel.sh
#description    SSH tunnel/proxy enable and configure SOCKS proxy for default wifi/ethernet network interfaces for OSX.
#author:        Robert Northard
#usage          ./ssh-tunnel.sh username@host
#
if [ "$(id -u)" != "0" ]; then
	echo "[!] This script must be run as root."
	exit 1
fi

usage() { echo "Usage: $0 [-h username@host]" 1>&2; exit 1; }

while getopts ":h:" o; do
    case "${o}" in
        h)
            h=${OPTARG}
            ;;
        *)
            usage
            ;;
    esac
done

if [ -z "${h}" ]; then
    usage
fi

function reset_proxy(){
    networksetup -setsocksfirewallproxystate Wi-Fi off
    networksetup -setsocksfirewallproxystate "Thunderbolt Ethernet" off
    echo "[+] SOCKS proxy disabled."
    exit 1
}

networksetup -setsocksfirewallproxy Wi-Fi 127.0.0.1 9999
networksetup -setsocksfirewallproxy "Thunderbolt Ethernet" 127.0.0.1 9999
networksetup -setsocksfirewallproxystate Wi-Fi on
networksetup -setsocksfirewallproxystate Ethernet on
trap reset_proxy SIGINT SIGTERM

echo "[+] SOCKS proxy enabled."
echo "[+] Tunneling..."

ssh -ND 9999 ${h} -p 22
