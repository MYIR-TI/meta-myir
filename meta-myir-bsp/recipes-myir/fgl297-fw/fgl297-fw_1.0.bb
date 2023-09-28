DESCRIPTION = "Configuration utility for TI wireless drivers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

MYIR_FIRMWARE_SRC ?= "git://github.com/MYiR-Dev/myir-firmware.git;protocol=https"
SRCREV_myir-firmware = "65cbd809d32906c17c55ece3591be60e8c878735"
SRC_URI += " \
           ${MYIR_FIRMWARE_SRC};branch=myd-ym62x;destsuffix=myir-firmware;name=myir-firmware \
"

do_install() {
       # Install NXP Connectivity
       install -d ${D}/${nonarch_base_libdir}/firmware/nxp

       # Install NXP Connectivity PCIE8997 firmware
       
       install -m 0644 ${WORKDIR}/myir-firmware/nxp/FwImage_8997/sdiouart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/nxp
       install -m 0644 ${WORKDIR}/myir-firmware/nxp/FwImage_8997/sduart8987_combo.bin  ${D}${nonarch_base_libdir}/firmware/nxp
}


FILES:${PN} = " \
       ${nonarch_base_libdir}/firmware/nxp/* \
"





