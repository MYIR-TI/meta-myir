SUMMARY = "Auto wifi ap and sta"
DESCRIPTION = "Script to connect wifi sta and ap"
LICENSE = "PD"
PR = "r3"

SRC_URI = " \
    file://ifup_wifi_ap \
    file://COPYING \
    file://ifup_wifi_sta \
    file://myir_hostapd.conf \
    file://myir_udhcpd.conf \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

do_install () {
    install -d ${D}/usr/bin
		install -d ${D}/etc
		
    cp -r ${WORKDIR}/ifup_wifi*  ${D}/usr/bin
    cp -r ${WORKDIR}/myir_* ${D}/etc
}

#SYSTEMD_PACKAGES = "${PN}"
#SYSTEMD_SERVICE_${PN} = "mount-overlayfs.service"

#inherit allarch systemd

FILES:${PN} = "\
	     /usr/bin   \
"
