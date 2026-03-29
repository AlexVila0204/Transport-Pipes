# Developer API

TransportPipes provides a lightweight API for duct creation/destruction, putting items inside them, registering custom containers, and more.

All API methods are accessed through `TransportPipesAPI.getInstance()`.

## Duct Management

### buildDuct

Builds a duct at the given location. Pass `"pipe"` as `baseDuctTypeName` and one of `"white"`, `"blue"`, `"red"`, `"yellow"`, `"green"`, `"black"`, `"golden"`, `"iron"`, `"ice"`, `"void"`, `"extraction"`, or `"crafting"` as `ductTypeName`.

```java
TransportPipesAPI.getInstance().buildDuct(
    String baseDuctTypeName,
    String ductTypeName,
    BlockLocation blockLocation,
    World world,
    Chunk chunk
) throws Exception
```

Throws an exception if a duct already exists at the location or there is a protected container adjacent to it.

### destroyDuct

Destroys the duct at the given location.

```java
TransportPipesAPI.getInstance().destroyDuct(
    BlockLocation blockLocation,
    World world
) throws Exception
```

### getDuct

Returns the `Duct` object at the given location, or `null` if there is no duct.

```java
TransportPipesAPI.getInstance().getDuct(BlockLocation blockLocation, World world)
```

### getDuctCount

Returns the number of ducts in the given world.

```java
TransportPipesAPI.getInstance().getDuctCount(World world)
```

### getDuctsInWorld

Returns a `ConcurrentSkipListMap<BlockLocation, Duct>` of all ducts in the given world.

```java
TransportPipesAPI.getInstance().getDuctsInWorld(World world)
```

## Item Management

### putItemInPipe

Puts an `ItemStack` into the given pipe with a moving direction of `itemDirection`.

```java
TransportPipesAPI.getInstance().putItemInPipe(Pipe pipe, ItemStack item, TPDirection itemDirection)
```

## Container Management

### registerTransportPipesContainer

Registers a custom container block at the given location. Every pipe around this block will try to extract/insert items from/into this container.

Implement the `TransportPipesContainer` interface to define the behavior of your custom container.

```java
TransportPipesAPI.getInstance().registerTransportPipesContainer(
    TransportPipesContainer container,
    BlockLocation blockLocation,
    World world
) throws Exception
```

### unregisterTransportPipesContainer

Unregisters a previously registered custom container block.

```java
TransportPipesAPI.getInstance().unregisterTransportPipesContainer(
    BlockLocation blockLocation,
    World world
) throws Exception
```

### getRegisteredContainers

Returns a `ConcurrentSkipListMap<BlockLocation, TransportPipesContainer>` of all registered custom containers in the given world.

```java
TransportPipesAPI.getInstance().getRegisteredContainers(World world)
```

### getContainerAtLocation

Returns the `TransportPipesContainer` at the given location, or `null` if there isn't one.

```java
TransportPipesAPI.getInstance().getContainerAtLocation(Location location)
```

### updateVanillaContainerBlock

When a vanilla container block (chest, furnace, etc.) is placed or destroyed programmatically and TransportPipes does not know about the block change, call this method to update the internal container set. `placed` indicates if the block was placed (`true`) or destroyed (`false`).

```java
TransportPipesAPI.getInstance().updateVanillaContainerBlock(Block block, boolean placed)
```

## Performance

### getTPS

Returns the current ticks per second of the TransportPipes thread.

```java
TransportPipesAPI.getInstance().getTPS()
```

### getPreferredTPS

Returns the preferred TPS of the TransportPipes thread. If this value equals the real TPS, the thread is running fine.

```java
TransportPipesAPI.getInstance().getPreferredTPS()
```

## TransportPipesContainer Interface

To create a custom container, implement the `TransportPipesContainer` interface:

```java
public interface TransportPipesContainer {
    ItemStack extractItem(TPDirection extractDirection, int amount, ItemFilter itemFilter);
    ItemStack insertItem(TPDirection insertDirection, ItemStack insertion);
    int spaceForItem(TPDirection insertDirection, ItemStack insertion);
    boolean isInLoadedChunk();
}
```

| Method | Description |
|--------|-------------|
| `extractItem` | Extracts items from the container. Returns what's available if not enough items exist. |
| `insertItem` | Inserts an item into the container. Returns the leftover that didn't fit, or `null` if everything fit. |
| `spaceForItem` | Returns the amount of space available for the given item. Called asynchronously. |
| `isInLoadedChunk` | Returns whether this container is in a loaded chunk. |
