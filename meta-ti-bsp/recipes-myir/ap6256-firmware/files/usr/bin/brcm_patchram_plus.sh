#!/bin/sh

time=0.2
rfkill0_path="/sys/class/rfkill/rfkill0/name"

if [ ! -f "$rfkill0_path" ]; then
    exit 1
fi

label=$(tr -d '\n' < "$rfkill0_path")

if [ "$label" = "rfkill-bluetooth" ]; then
    echo 0 > /sys/class/rfkill/rfkill0/state
    sleep $time
    echo 1 > /sys/class/rfkill/rfkill0/state
    sleep $time
    brcm_patchram_plus -d --tosleep 400000 --baudrate 500000 --use_baudrate_for_download --no2bytes --enable_hci --patchram /lib/firmware/brcm/BCM4345C5.hcd /dev/ttyS4
else
    exit 1
fi
