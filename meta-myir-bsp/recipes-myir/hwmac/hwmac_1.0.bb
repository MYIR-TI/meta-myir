SUMMARY = "factory hw mac"
DESCRIPTION = "print sn and mac"
LICENSE = "PD"
PR = "r3"

SRC_URI = " \
    file://hwmac.service \
    file://hwmac.sh \
    file://COPYING \
    file://hwmac-R \
"
#DEPENDS:${PN} = "glibc"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=1c3a7fb45253c11c74434676d84fe7dd"

do_install () {
    install -d ${D}${systemd_unitdir}/system/
    install -d ${D}/usr/bin

    install -m 0755 ${WORKDIR}/hwmac-R  ${D}/usr/bin/hwmac-R
    install -m 0755 ${WORKDIR}/hwmac.sh  ${D}/usr/bin/hwmac.sh
    install -m 0644 ${WORKDIR}/hwmac.service ${D}${systemd_unitdir}/system
}


inherit  systemd

FILES:${PN} = "\
	      ${systemd_unitdir}/system/  \
	     /usr/bin   \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "hwmac.service"
SYSTEMD_AUTO_ENABLE = "enable"

INSANE_SKIP:${PN} = "file-rdeps"
