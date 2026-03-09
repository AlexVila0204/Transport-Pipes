package de.robotricker.transportpipes.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import de.robotricker.transportpipes.TransportPipes;
import de.robotricker.transportpipes.duct.Duct;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol_1_18_2 implements ProtocolProvider {

    private final List<Class<? extends Directional>> clickedFaceDirectionals = new ArrayList<>(Arrays.asList(Bell.class, Cocoa.class,
            CoralWallFan.class, Grindstone.class, Hopper.class, Ladder.class, RedstoneWallTorch.class, Switch.class,
            TrapDoor.class, TripwireHook.class, WallSign.class));

    private final List<Material> clickedFaceMaterials = new ArrayList<>(Arrays.asList(Material.BONE_BLOCK, Material.BASALT,
            Material.POLISHED_BASALT, Material.CHAIN, Material.HAY_BLOCK, Material.PURPUR_PILLAR, Material.QUARTZ_PILLAR));

    @Override
    public int getMaskIndex() {
        return 15;
    }

    @Override
    public int getHeadRotIndex() {
        return 16;
    }

    @Override
    public int getRightArmRotIndex() {
        return 19;
    }

    @Override
    public void removeASD(Player p, List<ArmorStandData> armorStandData, ProtocolManager protocolManager) {
        PacketContainer entityDestroyContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        List<Integer> ids = armorStandData.stream().mapToInt(ArmorStandData::getEntityID).boxed().collect(Collectors.toList());
        entityDestroyContainer.getIntLists().write(0, ids);
        protocolManager.sendServerPacket(p, entityDestroyContainer);
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

        return Bukkit.getCraftingRecipe(craftingMatrix, duct.getWorld());
    }

    @Override
    public boolean isClickedFaceDirectional(BlockData blockData) {
        clickedFaceDirectionals.add(AmethystCluster.class);
        clickedFaceDirectionals.add(LightningRod.class);
        clickedFaceMaterials.add(Material.DEEPSLATE);
        clickedFaceMaterials.add(Material.INFESTED_DEEPSLATE);
        return ProtocolProvider.super.isClickedFaceDirectional(blockData);
    }

    @Override
    public PacketContainer setEntityMetadata(ProtocolManager protocolManager, ArmorStandData asd) {

        try {
            PacketContainer entityMetadataContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
            entityMetadataContainer.getIntegers().write(0, asd.getEntityID());

            List<Object> nmsDataWatcherItems = new ArrayList<>();

            Class<?> nmsSerializerRegistry = MinecraftReflection.getMinecraftClass("network.syncher.DataWatcherRegistry");

            Object byteSerializer = getSerializerField(nmsSerializerRegistry, "a");
            Object booleanSerializer = getSerializerField(nmsSerializerRegistry, "i");
            Object vector3fSerializer = getSerializerField(nmsSerializerRegistry, "k");


            nmsDataWatcherItems.add(createDataItem(byteSerializer, 0, (byte) (0x20 | 0x01)));
            nmsDataWatcherItems.add(createDataItem(booleanSerializer, 3, false));


            byte bitMask = (byte) ((asd.isSmall() ? 0x01 : 0x00) | 0x04 | 0x08 | 0x10);
            nmsDataWatcherItems.add(createDataItem(byteSerializer, getMaskIndex(), bitMask));


            Object headRot = createNMSVector3F(
                    (float) asd.getHeadRotation().getX(),
                    (float) asd.getHeadRotation().getY(),
                    (float) asd.getHeadRotation().getZ()
            );
            nmsDataWatcherItems.add(createDataItem(vector3fSerializer, getHeadRotIndex(), headRot));


            Object armRot = createNMSVector3F(
                    (float) asd.getArmRotation().getX(),
                    (float) asd.getArmRotation().getY(),
                    (float) asd.getArmRotation().getZ()
            );
            nmsDataWatcherItems.add(createDataItem(vector3fSerializer, getRightArmRotIndex(), armRot));


            List<WrappedWatchableObject> watchableList = new ArrayList<>();
            for (Object item : nmsDataWatcherItems) {
                watchableList.add(new WrappedWatchableObject(item));
            }

            entityMetadataContainer.getWatchableCollectionModifier().write(0, watchableList);


            return entityMetadataContainer;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Object getSerializerField(Class<?> registryClass, String fieldName) throws Exception {
        Field field = registryClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(null);
    }


    private Object createDataItem(Object serializer, int index, Object value) throws Exception {
        Class<?> dataWatcherObjectClass = MinecraftReflection.getMinecraftClass("network.syncher.DataWatcherObject");
        Class<?> dataWatcherSerializerClass = MinecraftReflection.getMinecraftClass("network.syncher.DataWatcherSerializer");


        Constructor<?> dwocConstructor = dataWatcherObjectClass.getDeclaredConstructor(int.class, dataWatcherSerializerClass);
        dwocConstructor.setAccessible(true);
        Object dataWatcherObject = dwocConstructor.newInstance(index, serializer);


        Class<?> dataWatcherItemClass = MinecraftReflection.getDataWatcherItemClass();
        Constructor<?> dwiConstructor = dataWatcherItemClass.getDeclaredConstructor(dataWatcherObjectClass, Object.class);
        dwiConstructor.setAccessible(true);
        return dwiConstructor.newInstance(dataWatcherObject, value);
    }


    private Object createNMSVector3F(float x, float y, float z) throws Exception {
        Class<?> vector3fClass = MinecraftReflection.getMinecraftClass("core.Vector3f");
        Constructor<?> constructor = vector3fClass.getDeclaredConstructor(float.class, float.class, float.class);
        constructor.setAccessible(true);
        return constructor.newInstance(x, y, z);
    }
}