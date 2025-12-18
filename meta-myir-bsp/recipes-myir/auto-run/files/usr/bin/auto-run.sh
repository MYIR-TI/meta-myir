#!/bin/sh
exec > /dev/ttyS2 2>&1
sleep 1

/usr/share/myir_testunit/MEasyListen-DEV eth0
