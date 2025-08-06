SUMMARY = "ap6256 configure files"
DESCRIPTION = "ap6256 files"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=94d55d512a9ba36caa9b7df079bae19f"
PV = "0.1"
PR = "v1"

DEPENDS += "systemd"
inherit systemd

SRC_URI = " \
		file://usr/lib/firmware/brcm/brcmfmac43456-sdio.bin \
		file://usr/lib/firmware/brcm/brcmfmac43456-sdio.clm_blob \
		file://usr/lib/firmware/brcm/brcmfmac43456-sdio.ti,am62l3-evm.bin \
		file://usr/lib/firmware/brcm/brcmfmac43456-sdio.txt \
		file://usr/lib/firmware/brcm/fw_bcm43456c5_ag.bin \
		file://usr/lib/firmware/brcm/nvram_ap6256.txt \
		file://usr/lib/firmware/brcm/BCM4345C5.hcd \
		file://usr/lib/systemd/system/brcm_patchram_plus.service \
		file://usr/bin/brcm_patchram_plus \
		file://usr/bin/brcm_patchram_plus.sh \
		file://licenses/GPL-2 \
"

S = "${WORKDIR}"

dirs755= "/usr/lib/firmware/brcm \
		  /usr/lib/systemd \
		  /usr/lib/systemd/system \
		  /usr/bin \
"

do_install (){
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done
	
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac43456-sdio.bin ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac43456-sdio.clm_blob ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac43456-sdio.ti,am62l3-evm.bin ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/brcmfmac43456-sdio.txt ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/fw_bcm43456c5_ag.bin ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/nvram_ap6256.txt ${D}/usr/lib/firmware/brcm
	install -m 0755 ${WORKDIR}/usr/lib/firmware/brcm/BCM4345C5.hcd ${D}/usr/lib/firmware/brcm

	install -m 0644 ${WORKDIR}/usr/lib/systemd/system/brcm_patchram_plus.service ${D}/usr/lib/systemd/system 
	install -m 0755 ${WORKDIR}/usr/bin/brcm_patchram_plus ${D}/usr/bin
	install -m 0755 ${WORKDIR}/usr/bin/brcm_patchram_plus.sh ${D}/usr/bin
}

FILES:${PN} = "\
		/usr/bin/brcm_patchram_plus \
		/usr/bin/brcm_patchram_plus.sh \
		/usr/lib/firmware/brcm \
		/usr/lib/firmware/brcm/* \
		/usr/lib/systemd \
		/usr/lib/systemd/system \
		/usr/lib/systemd/system/brcm_patchram_plus.service \
"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "brcm_patchram_plus.service"
SYSTEMD_AUTO_ENABLE = "enable"
