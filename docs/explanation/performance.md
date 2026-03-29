# Performance & The Tick System

Transport-Pipes is designed to handle extremely complex networks, but moving hundreds of items per second through hundreds of pipes is mathematically expensive for a server CPU.

## The Dedicated Thread
Transport-Pipes runs its logic on a dedicated asynchronous thread, separated from the main Minecraft Server thread. This means that even if a massive pipe factory requires heavy calculations, it will not drop the main Server TPS (Ticks Per Second) drastically.

You can inspect the health of this dedicated thread in-game by running `/tpipes tps`. This will tell you exactly how many pipes and items are currently actively ticking.

## Pipe Explosions (Max Items Limit)
To prevent malicious players from intentionally crashing the server by infinitely looping thousands of items in a circle of pipes, there is a hard limit implemented.

Defined in `config.yml` as `max_items_per_pipe`, this threshold acts as a safety valve. If too many items stack up inside a single pipe due to a clog or an infinite loop, **the pipe will physically explode**, dropping the items onto the floor and breaking the loop. 
