# Render Systems: Vanilla vs Modeled

Minecraft servers must balance beautiful visuals with server-side performance. Transport-Pipes tackles this by offering two entirely different ways to render pipes.

## The Problem
Rendering custom shaped blocks (like Pipes) in Minecraft plugins usually requires spawning an invisible **Armor Stand** holding an item on its head for every single pipe segment.
If a player builds a massive factory with thousands of pipes, the server must spawn thousands of Armor Stand entities, which will severely lag both the server and the player's client FPS.

## The Solution: Modeled Render System
The **Modelled Render System** uses a custom Resource Pack to inject real 3D block models into the game. 

**Why is this better?**
1. **Fewer Entities:** It replaces the need to summon thousands of invisible Armor Stands. The game engine processes standard blocks much faster than ticking entities.
2. **Visual Fidelity:** The pipes physically look like real blocks with proper textures, connecting seamlessly.

## The Fallback: Vanilla Render System
If you or your players refuse to use a Resource Pack, the plugin automatically falls back to the **Vanilla Render System**. This uses the classic invisible Armor Stands to fake the pipe shape using Minecraft default blocks. 

*Always encourage your players to type `/tpipes settings` and enable the Modelled Resource Pack for a better, smoother experience!*
