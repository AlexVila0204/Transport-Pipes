# Setup the Resource Pack (Modelled vs Vanilla)

Transport-Pipes offers two different ways to display pipes and items to the players:

1. **Vanilla Render System:** Uses Vanilla Minecraft textures (invisible armorstands). Good for servers that don't want a resource pack.
2. **Modelled Render System:** Uses a custom Resource Pack to render beautiful 3D pipe models. This provides a much better playing experience and significantly reduces the amount of entities in the world, increasing FPS!

## Changing your personal Render System

Every player on the server can individually choose which render system they want to see.

1. Type `/tpipes settings` in the chat.
2. This opens the player-specific settings interface.
3. Here you can toggle between **Vanilla** and **Modelled** Render System for yourself.

## Setting it up as an Admin

As a server admin, you dictate how the resource pack is distributed. Open `config.yml` and look for the `resourcepack_mode` property. There are three modes you can set:

- **`default`**: The plugin automatically handles sending the custom Transport-Pipes resource pack prompt to players when they join.
- **`server`**: Use this if you already have your own server/custom resource pack. You will need to download the TransportPipes resourcepack manually, merge it with your own, put the link to your merged pack into your `server.properties`, and let the plugin know by setting this mode to `server`.
- **`none`**: Completely disables the modelled rendersystem server-wide. No resourcepack prompt will ever be sent.
