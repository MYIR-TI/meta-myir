SUMMARY = "add wifi driver"
DESCRIPTION = "Script to mount a writable partition to a folder in the squashfs, then mount overlayfs"
LICENSE = "PD"
PR = "r3"

SRC_URI = " \
    file://app.service \
    file://COPYING \
    file://app.sh \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

do_install () {
    install -d ${D}${systemd_unitdir}/system/
    install -d ${D}/usr/bin

    cp -r ${WORKDIR}/app.sh  ${D}/usr/bin
    install -m 0644 ${WORKDIR}/app.service ${D}${systemd_unitdir}/system
}


inherit allarch systemd

FILES:${PN} = "\
	      ${systemd_unitdir}/system/  \
	     /usr/bin   \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "app.service"
SYSTEMD_AUTO_ENABLE = "enable"