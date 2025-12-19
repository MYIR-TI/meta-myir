# wpa_supplicant_2.10.bbappend
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://usr/lib/systemd/system/wpa_supplicant-custom.service"

do_install:append() {
    install -d ${D}${systemd_unitdir}/system/

    install -m 0644 ${WORKDIR}/usr/lib/systemd/system/wpa_supplicant-custom.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "${systemd_unitdir}/system/wpa_supplicant-custom.service"

SYSTEMD_SERVICE:${PN} = "wpa_supplicant-custom.service"

SYSTEMD_AUTO_ENABLE = "enable"
