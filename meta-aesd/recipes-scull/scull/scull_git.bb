inherit update-rc.d 
inherit logging

# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Youssef-74.git;protocol=ssh;branch=master \
           file://0001-Modified-Makefile-to-include-scull-and-other-changes.patch \
           file://init_scull.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "f510279af9056255eb9184e4ed4b1cff67404366"

S = "${WORKDIR}/git"

inherit module

FILES:${PN} += "${sysconfdir}/init.d/scull"


INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

FILES_${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull.ko"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull_load"
FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/scull_unload"

do_configure () {
	:
}

do_install() {
    bbwarn "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/"

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init_scull.sh ${D}${sysconfdir}/init.d/scull

    # install -d ${D}${bindir}
	# install -m 0755 ${S}/scull/scull_load ${D}${bindir}/
	# install -m 0755 ${S}/scull/scull_unload ${D}${bindir}/

    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0644 ${B}/scull/scull.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull/scull_load ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
    install -m 0755 ${S}/scull/scull_unload ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/
}