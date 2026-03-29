package de.robotricker.transportpipes.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import de.robotricker.transportpipes.TransportPipes;
import de.robotricker.transportpipes.duct.Duct;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Optional;

public class Protocol_1_16_5 implements ProtocolProvider {

    @Override
    public PacketContainer setEntityMetadata(ProtocolManager protocolManager, ArmorStandData asd) {
        PacketContainer entityMetadataContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        entityMetadataContainer.getModifier().writeDefaults();
        entityMetadataContainer.getIntegers().write(0, asd.getEntityID());

        byte bitMask = (byte) ((asd.isSmall() ? 0x01 : 0x00) | 0x04 | 0x08 | 0x10);

        // Use NMS DataWatcher directly to avoid ProtocolLib 5.3.0 constructor bug on 1.16.5
        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(new DataWatcher(null));
        WrappedDataWatcher.WrappedDataWatcherObject entityMask = new WrappedDataWatcher.WrappedDataWatcherObject(0, BYTE_SERIALIZER);
        WrappedDataWatcher.WrappedDataWatcherObject nameVisible = new WrappedDataWatcher.WrappedDataWatcherObject(3, BOOLEAN_SERIALIZER);
        WrappedDataWatcher.WrappedDataWatcherObject asMask = new WrappedDataWatcher.WrappedDataWatcherObject(getMaskIndex(), BYTE_SERIALIZER);
        WrappedDataWatcher.WrappedDataWatcherObject headRot = new WrappedDataWatcher.WrappedDataWatcherObject(getHeadRotIndex(), VECTOR_SERIALIZER);
        WrappedDataWatcher.WrappedDataWatcherObject rArmRot = new WrappedDataWatcher.WrappedDataWatcherObject(getRightArmRotIndex(), VECTOR_SERIALIZER);

        dataWatcher.setObject(entityMask, (byte) (0x20 | 0x01));
        dataWatcher.setObject(nameVisible, false);
        dataWatcher.setObject(asMask, bitMask);
        dataWatcher.setObject(headRot, new Vector3F((float) asd.getHeadRotation().getX(), (float) asd.getHeadRotation().getY(), (float) asd.getHeadRotation().getZ()));
        dataWatcher.setObject(rArmRot, new Vector3F((float) asd.getArmRotation().getX(), (float) asd.getArmRotation().getY(), (float) asd.getArmRotation().getZ()));
        entityMetadataContainer.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadataContainer;
    }

    @Override
    public Recipe calculateRecipe(TransportPipes transportPipes, Inventory inventory, Duct duct) {
        ItemStack[] craftingMatrix = new ItemStack[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (inventory.getItem(10 + row * 9 + col) != null) {
                    craftingMatrix[row * 3 + col] = inventory.getItem(10 + row * 9 + col);
                } else {
                    craftingMatrix[row * 3 + col] = new ItemStack(Material.AIR);
                }
            }
        }

        Container container = new Container(null, -1) {
            @Override
            public InventoryView getBukkitView() {
                return null;
            }

            @Override
            public boolean canUse(EntityHuman entityHuman) {
                return false;
            }
        };

        InventoryCrafting inventoryCrafting = new InventoryCrafting(container, 3, 3);
        for (int i = 0; i < craftingMatrix.length; i++) {
            inventoryCrafting.setItem(i, CraftItemStack.asNMSCopy(craftingMatrix[i]));
        }

        World world = ((CraftWorld) duct.getWorld()).getHandle();
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return null;
        Optional<RecipeCrafting> recipeCrafting = server.getCraftingManager().craft(Recipes.CRAFTING, inventoryCrafting, world);

        return recipeCrafting.map(IRecipe::toBukkitRecipe).orElse(null);
    }
}
