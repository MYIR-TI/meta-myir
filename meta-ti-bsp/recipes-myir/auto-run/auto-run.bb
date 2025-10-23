SUMMARY = "auto-run configure files"
DESCRIPTION = "auto-run files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd"
inherit systemd

SRC_URI = " \
		file://usr/bin/auto-run.sh \
		file://usr/share/myir/Video/ \
           	file://usr/share/myir/Music/ \
           	file://usr/share/myir/Capture/ \
		file://usr/lib/systemd/system/auto-run.service \
		file://licenses/GPL-2 \
"

S = "${WORKDIR}"

dirs755= "/usr \
		  /usr/bin \
		  /usr/lib \
		  /usr/lib/systemd \
		  /usr/lib/systemd/system \
		  /usr/share \
		  /usr/share/myir \
		  /usr/share/myir/Video \
		  /usr/share/myir/Music \
		  /usr/share/myir/Capture \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done

	install -m 0755 ${WORKDIR}/usr/bin/auto-run.sh ${D}/usr/bin
	install -m 0755 ${WORKDIR}/usr/share/myir/Video/* ${D}/usr/share/myir/Video
	install -m 0755 ${WORKDIR}/usr/share/myir/Music/* ${D}/usr/share/myir/Music
	install -m 0755 ${WORKDIR}/usr/share/myir/Capture/* ${D}/usr/share/myir/Capture
	install -m 0644 ${WORKDIR}/usr/lib/systemd/system/auto-run.service ${D}/usr/lib/systemd/system 
}

FILES:${PN} = "\
		/usr \
		/usr/bin \
		/usr/share \
		/usr/share/myir \
		/usr/share/myir/Video \
		/usr/share/myir/Music \
		/usr/share/myir/Capture \
		/usr/bin/auto-run.sh \
		/usr/lib \
		/usr/lib/systemd \
		/usr/lib/systemd/system \
		/usr/lib/systemd/system/auto-run.service \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "auto-run.service"
SYSTEMD_AUTO_ENABLE = "enable"

