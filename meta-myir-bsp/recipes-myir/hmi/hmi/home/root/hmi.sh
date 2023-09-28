#!/bin/sh
export QT_WAYLAND_SHELL_INTEGRATION=xdg-shell
export QTWEBENGINE_DISABLE_SANDBOX=1
export QT_QPA_EGLFS_ALWAYS_SET_MODE=1
export WAYLAND_DISPLAY=/run/wayland-0
export XDG_RUNTIME_DIR=/run/user/0
flag=`cat /proc/cpuinfo  | grep processor | wc -l`
echo "flag ${flag}"

AutoDisabled()
{
file="/etc/systemd/system/graphical.target.wants/weston.service"
pam_name="PAMName=weston-autologin"

if [ ! -e "$file" ]; then
    touch "$file"
fi

if grep -q "$pam_name" "$file"; then
    if ! grep -q "#$pam_name" "$file"; then
        sed -i "s/$pam_name/#$pam_name/" "$file"
    fi
else
    echo "#$pam_name" >> "$file"
fi
}

if [ ${flag} -gt 1 ];then
        /home/root/mxapp2
else
        time=1
        while [ $time -lt 10 ];
        do
                if [ -L /dev/input/touchscreen0 ];then
                        break
                else
                        sleep 3     
                        echo ${time} 
                        time=$((time + 1))
                fi                
        done                                      
	systemctl stop weston
        AutoDisabled
        /home/root/lvgl_ethercat_demo
fi
