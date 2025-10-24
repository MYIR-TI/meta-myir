#!/bin/sh

led1=usr0
led2=usr1

LED_PID=-1
time=0.5

ECHO_TTY="/dev/ttyS0"

burn_start_ing(){

    echo "***********************************************" >> ${ECHO_TTY} 
    echo "*************    SYSTEM UPDATE    *************" >> ${ECHO_TTY} 
    echo "***********************************************" >> ${ECHO_TTY} 
    echo "***********************************************" >> ${ECHO_TTY} 
    echo "*************   Update starting   *************" >> ${ECHO_TTY}  
    echo "***********************************************" >> ${ECHO_TTY} 
    echo "                                               " >> ${ECHO_TTY} 
    echo "                                               " >> ${ECHO_TTY} 
    echo "                                               " >> ${ECHO_TTY} 

    #核心板上的蓝灯闪烁：烧写中
    echo 0 > /sys/class/leds/${led1}/brightness
    echo 0 > /sys/class/leds/${led2}/brightness


    while [ 1 ]
    do
        echo 1 > /sys/class/leds/${led1}/brightness
        echo 1 > /sys/class/leds/${led2}/brightness
        sleep $time
        echo 0 > /sys/class/leds/${led1}/brightness
        echo 0 > /sys/class/leds/${led2}/brightness
        sleep $time
        echo "*************   Updating   *************" >> ${ECHO_TTY} 
    done
}

burn_faild(){
    kill $LED_PID

    # 熄灭
    echo 0 > /sys/class/leds/${led1}/brightness
    echo 0 > /sys/class/leds/${led2}/brightness

    echo "Update faild..."   >> ${ECHO_TTY} 
    echo "Update faild..."   >> ${ECHO_TTY} 
    echo "Update faild..."   >> ${ECHO_TTY} 
}

burn_succeed(){
    
    kill $LED_PID

	# 常亮
	echo 1 > /sys/class/leds/${led1}/brightness
    echo 1 > /sys/class/leds/${led2}/brightness

	echo "***********************************************" >> ${ECHO_TTY} 
	echo "********    SYSTEM UPDATE  SUCCEED  ***********" >> ${ECHO_TTY} 
    echo "********    SYSTEM UPDATE  SUCCEED  ***********" >> ${ECHO_TTY} 
    echo "********    SYSTEM UPDATE  SUCCEED  ***********" >> ${ECHO_TTY} 
	echo "***********************************************" >> ${ECHO_TTY} 
    echo "***********************************************" >> ${ECHO_TTY} 
    echo "                                               " >> ${ECHO_TTY} 

}

echo_fun(){
	echo "***********************************************" >> ${ECHO_TTY} 
	echo "********************   "$1 "   ****************" >> ${ECHO_TTY}
    echo "***********************************************" >> ${ECHO_TTY} 
}

cmd_check()
{
	if [ $1 -ne 0 ];then
		echo "$2 failed!"   >> ${ECHO_TTY}
        echo "$2 failed!"   >> ${ECHO_TTY}
        echo "$2 failed!"   >> ${ECHO_TTY}
		burn_faild 
        exit -1
	fi
}

burn_bootloader(){
    flash_erase /dev/mtd0 0 0 ; sleep 1 ; nandwrite -a -p /dev/mtd0 /root/mfgimage/tiboot3.bin
    sleep $time
    flash_erase /dev/mtd1 0 0 ; sleep 1 ; nandwrite -a -p /dev/mtd1 /root/mfgimage/tispl.bin
    sleep $time
    flash_erase /dev/mtd2 0 0 ; sleep 1 ; nandwrite -a -p /dev/mtd2 /root/mfgimage/u-boot.img
    sleep $time
    cmd_check $? "burn bootloader faild"
    sync
}

attach_env(){
    flash_erase /dev/mtd3 0 0
    ubiformat /dev/mtd3 -y
    ubiattach -m 3
    ubimkvol /dev/ubi0 -N uboot_env -s 512KiB
}

burn_kernel_dtb(){
    flash_erase /dev/mtd4 0 0 ; sleep 1 ; nandwrite -a -p /dev/mtd4 /root/mfgimage/Image
    sleep $time
    flash_erase /dev/mtd5 0 0 ; sleep 1 ; nandwrite -a -p /dev/mtd5 /root/mfgimage/myd-ym62lx-nand.dtb
    sleep $time
}

burn_ubi(){
    # start_time=`date +%s`
    flash_erase /dev/mtd6 0 0 ; sleep 1 ; ubiformat /dev/mtd6 -f /root/mfgimage/myir-image-core-myd-ym62lx-rt-nand.rootfs.ubi
    cmd_check $? "burn ubi faild"
    sync
    # end_time=`date +%s`
    # echo rootfs time:$(($end_time - $start_time))
}

reszie2fs_emmc(){
    resize2fs /dev/mmcblk0p2
    cmd_check $? "reszie2fs mmc faild"
    sync
}

check_rootfs(){
    mkdir -p /run/media/mmcblk0p2
    mount /dev/mmcblk0p2 /run/media/mmcblk0p2
    rootfs_hostname=`cat /run/media/mmcblk0p2/etc/hostname`
    echo_fun "rootfs_hostname:$rootfs_hostname"

    if [ x"$rootfs_hostname" != x"$HOSTNAME" ];then
       echo_fun "hostname not equal"
       reboot
    else
       echo_fun "hostname equal"
    fi
}

burn_start_ing &
LED_PID=$!
sleep 1
echo_fun "start burn bootloader"
burn_bootloader
attach_env
burn_kernel_dtb
echo_fun "start burn ubi "
burn_ubi
# echo_fun "start reszie2fs emmc"
# reszie2fs_emmc
# check_rootfs
burn_succeed


