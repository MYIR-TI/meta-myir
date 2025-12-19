require u-boot-ti.inc

include ${@ 'recipes-bsp/u-boot/ti-extras.inc' if d.getVar('TI_EXTRAS') else ''}

PR = "r0"

# Use MYIR u-boot repo/branch, override defaults from meta-ti u-boot-ti.inc
UBOOT_GIT_URI = "git://github.com/MYIR-TI/myir-ti-uboot.git"
UBOOT_GIT_PROTOCOL = "https"

BRANCH = "develop-ym62x-u-boot-2025.01_V11.01.05.03"

SRCREV = "d73100b4a740f5d10d0b671a57017e0cb239f27e"
