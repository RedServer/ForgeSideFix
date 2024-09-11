# Forge side fix

[![Release](https://jitpack.io/v/RedServer/ForgeSideFix.svg)](https://jitpack.io/#RedServer/ForgeSideFix)

Fixes Minecraft crash issue caused by incorrect `Side.BUKKIT`   
Issue: https://github.com/MinecraftForge/ForgeGradle/issues/748

```
net.minecraftforge.fml.common.LoaderExceptionModCrash: Caught exception from Forge Mod Loader (FML)
Caused by: java.lang.NullPointerException
    at net.minecraftforge.fml.common.network.NetworkRegistry.newChannel(NetworkRegistry.java:207)
    at net.minecraftforge.fml.common.network.internal.FMLNetworkHandler.registerChannel(FMLNetworkHandler.java:185)
    at net.minecraftforge.fml.common.FMLContainer.modConstruction(FMLContainer.java:92)
```

This mod removes `BUKKIT` side in runtime to prevent crash.

## Installation

1. You can [download the file](https://github.com/RedServer/ForgeSideFix/releases) and put it in your project mods directory (run/mods).
2. OR you can add this mod as dependency in classpath by adding in your **build.gradle**:

```groovy
repositories {
    // ...
    maven {
        url = 'https://jitpack.io'
        content {
            includeGroup 'com.github'
        }
    }
    // ...
}

dependencies {
    // ...
    runtimeOnly 'com.github.RedServer:ForgeSideFix:1.12.2-1.0'
    // ...
}
```
