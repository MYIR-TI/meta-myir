SUMMARY = "fw-env-emmc configure files"
DESCRIPTION = "fw-env-emmc files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd"
inherit systemd

SRC_URI = " \
		file://etc/fw_env.config \
		file://etc/u-boot-initial-env \
		file://licenses/GPL-2 \
"

S = "${WORKDIR}"

dirs755= "/etc \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done

	install -m 0755 ${WORKDIR}/etc/fw_env.config ${D}/etc 
	install -m 0755 ${WORKDIR}/etc/u-boot-initial-env ${D}/etc 
}

FILES:${PN} = "\
		/etc \
"

