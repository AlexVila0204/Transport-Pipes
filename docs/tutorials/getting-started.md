# Getting Started

Now that the plugin is installed, it's time to learn how to place and connect pipes in the game.

## The Wrench

To configure any pipe, you will need a **Wrench**. The wrench is the core tool of the plugin. 

By default, the wrench can be crafted, or you can get it using `/tpipes creative` if you are in Creative Mode. The wrench can be used simply by **right-clicking** the desired pipe.

## Your First Pipe System

### 1. The Extraction Pipe
Pipes transport items, but they need to pull items from somewhere. The **Extraction Pipe** is the ONLY pipe which can extract items from container blocks (Chests, furnaces, hoppers, shulker boxes, and so on).

1. Place a Chest and put some items inside.
2. Place an **Extraction Pipe** connected to the chest.
3. The pipe will automatically display a different connection texture pointing towards the chest. This is its *extract direction*.

You can change the extract direction as well as the *extract condition* by right-clicking the Extraction Pipe with your Wrench. 

**Extract Conditions:**
- `needs redstone`: Extracts only if powered by a redstone signal.
- `always extract`: Always extracts items regardless of redstone.
- `never extract`: Never extracts items.

### 2. Transporting Items
Extraction pipes don't connect to each other, but they do connect with every other pipe. The simplest transport pipe is the **Colored Pipe**.

1. Craft a White Colored Pipe (which acts as the default uncolored pipe).
2. Connect it to your Extraction Pipe.
3. Keep placing Colored Pipes until you reach your destination container. The White pipe automatically connects to any neighboring pipe.
*(Note: You can dye colored pipes. Different colored pipes behave the same but don't connect to each other, allowing you to create complex systems in tight spaces without wires crossing).*

### 3. Inserting Items
Finally, connect your last Colored Pipe directly into your destination container (e.g., another chest). All pipes can put items into container blocks if they are connected to them. 

Turn on your Extraction Pipe (e.g., set to `always extract`) and watch the items flow through the network into the final chest!
