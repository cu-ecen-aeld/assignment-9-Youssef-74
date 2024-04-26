inherit update-rc.d 
inherit logging

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-Youssef-74.git;protocol=ssh;branch=main \
            file://init_aesdchar.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "bb0a3cf5961f9cce4de923022e4463275a9f92b8"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

FILES:${PN} += "${sysconfdir}/init.d/aesdchar"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesdchar"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload"

do_configure () {
	:
}

do_install() {
    bbwarn "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init_aesdchar.sh ${D}${sysconfdir}/init.d/aesdchar
    
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${B}/aesdchar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar_load ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/aesdchar_unload ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}
