# Human Input Android

For CS-4001 at Saint Lawrence University

### Installation
For complete functionalty, this app must be installed on an Android device with HIAndroidKernel installed.
HIAndroidKernel contains modifications to the USB HID gadget drivers from the Linux kernel and the Android composite drivers.
The HIAndroidKernel repository is based on the flo (Nexus 7 2014 WiFi) branch of the Android fork of the Linux kernel.
For installation on other devices, the kernel source for another device should be patched with the output of
    
    git diff HIANDROID_FORKED HIANDROID_PATCH

This app will also need superuser permissions, so a superuser helper is also required, such as SuperSU.
The Android app (this repository) has no build or install dependencies, it may just be built in Android Studio and deployed.

