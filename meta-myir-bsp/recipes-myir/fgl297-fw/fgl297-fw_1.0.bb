DESCRIPTION = "NXP IMX firmware for FGL297 wireless drivers"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Disable license checksum QA check for proprietary firmware
INSANE_SKIP:${PN} += "license-checksum"

SRC_URI = "file://sduart8997_combo_v4.bin;subdir=firmware"
SRC_URI[md5sum] = "3492cdef6fd0676ad915ab36404416b2"

do_install() {
       # Create mrvl directory
       install -d ${D}${nonarch_base_libdir}/firmware/mrvl

       # Install and rename NXP Connectivity PCIE8997 firmware
       # Rename sduart8997_combo_v4.bin to sdiouart8997_combo_v4.bin
       install -m 0644 ${WORKDIR}/firmware/sduart8997_combo_v4.bin ${D}${nonarch_base_libdir}/firmware/mrvl/sdiouart8997_combo_v4.bin
}

FILES:${PN} = " \
       ${nonarch_base_libdir}/firmware/mrvl/* \
"