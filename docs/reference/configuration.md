# Configuration System

The TransportPipes data folder (`plugins/TransportPipes/`) contains the overall configuration options.

## Global Properties (`config.yml`)

This is the general plugin config file where you can adjust global properties:

- **`max_items_per_pipe`**: If the amount of items in one pipe exceeds this value, the pipe explodes to prevent server lag issues.
- **`crafting_enabled`**: Disable this to turn off all default pipe crafting recipes (Useful if you use a Custom Recipes plugin).
- **`anticheat_plugins`**: List all AntiCheat plugins running on your server here. If not listed, players may be blocked from placing/breaking pipes due to AntiCheat prevention.
- **`default_render_system`**: Can be either `vanilla` or `modelled`. 
- **`default_show_items`**: Specify the default value for the player-specific option "show items". This allows you to visually hide all items moving through the pipes entirely.
- **`default_render_distance`**: Specify the default render distance of pipe ducts (Players can individually modify this via `/tpipes settings`).
- **`resourcepack_mode`**: Can be `default`, `server`, or `none`. Refer to the [Resource Pack Setup Guide](../how-to/setup-resourcepack.md) for details.
- **`disabled_worlds`**: For every world contained in this list, the duct system will be fully disabled.
- **`wrench`**: Wrench configuration:
  - `item`: The Material of the wrench.
  - `glowing`: Whether the wrench has the enchantment glow effect.
  - `required`: If `true`, players can only open pipe GUIs by clicking with the wrench. If `false`, they can use an empty hand or any item.
- **`language`**: Specify the language.
- **`show_hidden_ducts_time`**: When a player shift-clicks with a wrench, hidden ducts are revealed. This is the time in seconds how long they stay revealed.
- **`merge_same_pipe_items`**: If `true`, items of the same type will attempt to merge together when they are in the same pipe section. Enabling this can improve performance and reduce pipe explosions.

## Language Files
The `lang_[LANG].yml` files hold all localized text. You can directly edit your language file or copy an existing one to create a new translation.

## Player Settings
The `playersettings/` directory contains individual player settings bound to UUIDs. You may delete one of those files to auto-generate a new default config for that player on the next server restart.
