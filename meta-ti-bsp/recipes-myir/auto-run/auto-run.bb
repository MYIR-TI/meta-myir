SUMMARY = "auto-run configure files"
DESCRIPTION = "auto-run files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd"
inherit systemd

SRC_URI = " \
		file://usr/share/myir_testunit/auto-run.sh \
		file://usr/lib/systemd/system/auto-run.service \
		file://licenses/GPL-2 \
"

S = "${WORKDIR}"

dirs755= "/usr \
		  /usr/lib \
		  /usr/lib/systemd \
		  /usr/lib/systemd/system \
		  /usr/share \
		  /usr/share/myir_testunit \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done

	install -m 0755 ${WORKDIR}/usr/share/myir_testunit/auto-run.sh ${D}/usr/share/myir_testunit
	
	install -m 0644 ${WORKDIR}/usr/lib/systemd/system/auto-run.service ${D}/usr/lib/systemd/system 
}

FILES:${PN} = "\
		/usr \
		/usr/share \
		/usr/share/myir_testunit \
		/usr/share/myir_testunit/auto-run.sh \
		/usr/lib \
		/usr/lib/systemd \
		/usr/lib/systemd/system \
		/usr/lib/systemd/system/auto-run.service \
"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "auto-run.service"
SYSTEMD_AUTO_ENABLE = "enable"

