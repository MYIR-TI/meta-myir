#!/bin/sh

EMMC_NODE=/dev/mmcblk0

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

enable_bootpart(){
    mmc bootpart enable 1 1 /dev/mmcblk0
    mmc bootbus set single_backward x1 x8 /dev/mmcblk0
    mmc hwreset enable /dev/mmcblk0
}


burn_bootloader(){
    echo 0 > /sys/block/mmcblk0boot0/force_ro
    dd if=/dev/zero of=/dev/mmcblk0boot0 bs=1M count=4
    sleep $time
    echo 0 > /sys/block/mmcblk0boot0/force_ro

    dd if=/root/mfgimage/tiboot3.bin of=/dev/mmcblk0boot0 seek=0
    sleep $time
    dd if=/root/mfgimage/tispl.bin of=/dev/mmcblk0boot0 seek=1024
    sleep $time
    dd if=/root/mfgimage/u-boot.img of=/dev/mmcblk0boot0 seek=5120
    sleep $time
    cmd_check $? "burn bootloader faild"
    sync
}

burn_wic(){
    # start_time=`date +%s`
    dd if=/root/mfgimage/myir-image-full-myd-ym62lx-emmc.rootfs.wic of=/dev/mmcblk0
    cmd_check $? "burn wic faild"
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

    if [ x"$rootfs_hostname" != x"myd-ym62lx-emmc" ];then
       echo_fun "hostname not equal"
       reboot
    else
       echo_fun "hostname equal"
    fi
}

resize_partition() {
    fdisk /dev/mmcblk0 << EOF
        d
        2
        n
        p
        2


        N
        w
EOF
}

burn_start_ing &
LED_PID=$!
sleep 1
echo_fun "start burn bootloader"
burn_bootloader
echo_fun "start burn wic "
burn_wic
# echo_fun "start reszie2fs emmc"
umount /run/media/root-mmcblk0p2
resize_partition
reszie2fs_emmc
# check_rootfs
enable_bootpart
burn_succeed


