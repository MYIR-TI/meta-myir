#!/bin/sh
ldconfig /usr/local/lib
sleep 1
insmod /usr/share/nxp/mlan.ko
insmod /usr/share/nxp/moal.ko mod_para=nxp/wifi_mod_para.conf


