require u-boot-ti.inc

include ${@ 'recipes-bsp/u-boot/ti-extras.inc' if d.getVar('TI_EXTRAS') else ''}

PR = "r0"

BRANCH = "feature-ti-u-boot-2025.01-V11.00.15.02"

SRCREV = "${AUTOREV}"

UBOOT_GIT_PROTOCOL = "https"

UBOOT_GIT_URI = "git://migit.goho.co/MYD-YM62LX-LINUX/myir-ti-uboot.git"
