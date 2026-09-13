# History & Dependencies

This page contains the technical facts about the Transport-Pipes plugin.

## Current Version

**5.5.1** — Supports Minecraft 1.16.5 through 26.1.2.

<details>
<summary><b>View Changelog History</b></summary>

### 5.5.1 (multi-version update)
- Added support for Minecraft 1.21.2 through 1.21.11 and 26.1 through 26.1.2.
- Added a 1.21.4+ resource pack using the new item model definition system; the plugin picks the right pack per server version.
- Version-specific modules for 1.21.9+ no longer rely on versioned CraftBukkit or NMS classes, so they run on Paper builds that do not remap plugins.
- Head item creation supports the record-based `GameProfile` introduced with authlib 7 (1.21.9+).
- Server version detection tolerates Paper 26.x version strings (e.g. `26.1.2.build.74`).

### 5.5.1
- Enhanced protocol support for 1.16.5 with metadata updates and ProtocolLib compatibility.
- Revised and completed plugin documentation.

### 5.5.0-beta
- Added support for Minecraft 1.18.2, 1.19.4, 1.20.6, and 1.21/1.21.1.
- Removed unused sound effect in `IronPipe` class.

### 5.4.x (Previous Updates)
- Added support for 1.20 and 1.20.1.
- Fixed player memory leaks and optimized TPS performance.
- Fixed issues with settings and pipe items not clearing on logout.
</details>

## Required Dependencies

| Plugin | Notes |
|--------|-------|
| [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) | Required for pipe rendering and interaction. 5.4.0 up to 1.21.11; the [5.5.0 dev build](https://github.com/dmulloy2/ProtocolLib/releases/tag/dev-build) for 26.1+. See [Installation](../tutorials/installation.md#protocollib-compatibility). |

## Optional Soft-Dependencies

These plugins are not required, but Transport-Pipes will integrate with them if they are present.

| Plugin | Integration |
|--------|-------------|
| LWC | Prevents pipes from being placed next to protected containers. |
| AuthMe | Prevents interaction with pipes while not authenticated. |
| WorldEdit | Compatibility with WorldEdit operations. |

