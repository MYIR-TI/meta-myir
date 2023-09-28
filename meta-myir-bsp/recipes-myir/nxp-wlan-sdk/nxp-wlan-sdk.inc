SUMMARY = "NXP Wi-Fi SDK for 88w8997 and 88w8987"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://gpl-2.0.txt;md5=ab04ac0f249af12befccb94447c08b77"

SRCBRANCH = "master"
MRVL_SRC ?= "git://github.com/MYIR-TI/wlan_src.git;protocol=https"
SRC_URI = "${MRVL_SRC};branch=${SRCBRANCH}"
SRCREV = "42e783e24b0a018f81bb6c838ca7532862528a07"
inherit module

S = "${WORKDIR}/git"

#EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR}"
EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'
#RRECOMMENDS_${PN} = "wireless-tools"
