DESCRIPTION = "myir locale data"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=309cc7bace8769cfabdd34577f654f8e"

SRC_URI = "file://etc/udhcpd.conf \
	   file://etc/myir_test \
	   file://etc/myir_test/wifi_ap_sta \
	   file://usr/sbin \
	   file://usr/lib/locale/zh_CN \
	   file://usr/lib/locale/en_US \
	   file://usr/lib/locale/en_GB \
	   file://LICENSE \
"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/usr/lib/locale/
	install -d ${D}/usr/sbin/
	install -d ${D}/etc/
	install -d ${D}/etc/myir_test/
	install -d ${D}/etc/myir_test/wifi_ap_sta/

	install -m 755 ${S}/etc/udhcpd.conf ${D}/etc/
	install -m 755 ${S}/etc/myir_test/myir_audio_play ${D}/etc/myir_test/myir_audio_play
	install -m 755 ${S}/etc/myir_test/myir_video_play ${D}/etc/myir_test/myir_video_play
	install -m 755 ${S}/etc/myir_test/wifi_on_sta ${D}/etc/myir_test/wifi_on_sta
	install -m 755 ${S}/etc/myir_test/wifi_on_ap ${D}/etc/myir_test/wifi_on_ap
	install -m 755 ${S}/etc/myir_test/wifi_ap_sta/* ${D}/etc/myir_test/wifi_ap_sta/
	install -m 755 ${S}/usr/sbin/* ${D}/usr/sbin/
	cp -r ${S}/usr/lib/locale/zh_CN ${D}/usr/lib/locale/
	cp -r ${S}/usr/lib/locale/en_US ${D}/usr/lib/locale/
   	cp -r ${S}/usr/lib/locale/en_GB ${D}/usr/lib/locale/
}

FILES:${PN} = "/etc/myir_test/myir_audio_play \
	       /etc/myir_test/myir_video_play \
	       /etc/myir_test/wifi_on_sta \
	       /etc/myir_test/wifi_on_ap \
	       /etc/myir_test/wifi_ap_sta/* \
	       /etc/ \
	       /usr/sbin/* \
	       /usr/sbin/framebuffer_test \
	       /usr/lib/locale/zh_CN/* \
	       /usr/lib/locale/en_US/* \
	       /usr/lib/locale/en_GB/* \
"

