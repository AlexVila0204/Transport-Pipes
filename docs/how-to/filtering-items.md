# How to Filter Items using Golden Pipes

A **Golden Pipe** allows you to sort items into different directions. 
Every output direction on the Golden Pipe has a specific color physically visible on the pipe.

## Setting Up the Filter

1. Place a **Golden Pipe** at an intersection in your pipe system.
2. Hold a **Wrench** and **Right-Click** the Golden Pipe.
3. A sorting inventory GUI opens. 

Every row in this inventory refers to an output direction of the Golden Pipe. The block color in the row indicates which direction it corresponds to.

- **To set a filter:** Place the items you want to filter directly into the slots of that row.

## Filter Modes and Strictness

By clicking the color indicator (the wool block) inside the inventory row, you can change the **Filter Mode** and **Filter Strictness** for that specific output direction.

### Filter Modes
This determines *how* the filter should be applied to the items moving through.
- **Normal:** The default mode. It only accepts an item if it matches at least one item inside the filter row.
- **Inverted:** It blocks all items that are inside the filter row, and accepts everything else.
- **Block All:** Simply closes that output direction and blocks everything.

### Filter Strictness
This determines *how strictly* the plugin compares the items moving through against the items in the filter row.
- **Item Material:** Only checks if the block/item material matches (e.g., ignores durability, enchantments, or metadata).
- **Item Material and Metadata:** Checks if the item inside the pipe is exactly the identical same as the filter item.
