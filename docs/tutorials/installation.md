# Installation

Welcome to Transport-Pipes! This plugin adds several different pipes into Minecraft (similar to the BuildCraft mod) which can transport any kind of item.

## Prerequisites

Transport-Pipes depends only on **ProtocolLib**. In order for this plugin to work, make sure the correct version of ProtocolLib for your server is installed.
You can download ProtocolLib [here](https://www.spigotmc.org/resources/protocollib.1997/).

### ProtocolLib compatibility

| Server version | ProtocolLib | Java | Download |
|----------------|-------------|------|----------|
| 1.16.5 | **5.3.0** | 16+ | [ProtocolLib.jar (5.3.0)](https://github.com/dmulloy2/ProtocolLib/releases/download/5.3.0/ProtocolLib.jar) |
| 1.17 – 1.21.8 | **5.4.0** | 17+ (21+ from 1.20.5) | [ProtocolLib.jar (5.4.0)](https://github.com/dmulloy2/ProtocolLib/releases/download/5.4.0/ProtocolLib.jar) |
| 1.21.9 – 1.21.11 | **5.4.0** (logs a "not yet tested" warning, works) | 21+ | [ProtocolLib.jar (5.4.0)](https://github.com/dmulloy2/ProtocolLib/releases/download/5.4.0/ProtocolLib.jar) |
| 26.1 and newer | **5.5.0 dev build** | 25+ | Paper: [ProtocolLib.jar](https://github.com/dmulloy2/ProtocolLib/releases/download/dev-build/ProtocolLib.jar) · Spigot: [ProtocolLib-Spigot.jar](https://github.com/dmulloy2/ProtocolLib/releases/download/dev-build/ProtocolLib-Spigot.jar) |

All builds are published on the [ProtocolLib GitHub releases page](https://github.com/dmulloy2/ProtocolLib/releases). The `dev-build` tag is rolling: it always points to the latest snapshot.

::: info Minecraft 1.16.5
ProtocolLib 5.4.0 requires Java 17. On a 1.16.5 server (Java 16) use ProtocolLib **5.3.0**.
:::

::: warning Minecraft 26.1+
ProtocolLib 5.4.0 cannot construct several packets on 26.x servers, so pipes will not render. Use the **dev build** from the link above. On Paper use `ProtocolLib.jar`; on Spigot use `ProtocolLib-Spigot.jar`.
:::

::: info Resource pack on 1.21.4+
Minecraft 1.21.4 replaced the legacy `CustomModelData` overrides with a new item model system. Transport-Pipes automatically sends the matching resource pack for your server version, no configuration needed.
:::

## Step-by-Step Installation

1. Download the latest version of **Transport-Pipes** corresponding to your server version.
2. Download the correct version of **ProtocolLib**.
3. Place both `.jar` files into your server's `plugins/` folder.
4. Restart your server.
5. You should now see the `TransportPipes/` folder generated inside your `plugins/` directory!

You are now ready to jump into the game and build your first pipe system! Proceed to the [Getting Started](./getting-started.md) guide.
