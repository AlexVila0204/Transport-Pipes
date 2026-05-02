# History & Dependencies

This page contains the technical facts about the Transport-Pipes plugin.

## Current Version

**5.5.1** — Supports Minecraft 1.16.5 through 1.21.1.

<details>
<summary><b>View Changelog History</b></summary>

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
| [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) | Required for pipe rendering and interaction. |

## Optional Soft-Dependencies

These plugins are not required, but Transport-Pipes will integrate with them if they are present.

| Plugin | Integration |
|--------|-------------|
| LWC | Prevents pipes from being placed next to protected containers. |
| AuthMe | Prevents interaction with pipes while not authenticated. |
| WorldEdit | Compatibility with WorldEdit operations. |

