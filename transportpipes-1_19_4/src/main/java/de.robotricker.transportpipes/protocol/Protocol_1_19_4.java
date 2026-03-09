package de.robotricker.transportpipes.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import de.robotricker.transportpipes.TransportPipes;
import de.robotricker.transportpipes.duct.Duct;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol_1_19_4 implements ProtocolProvider {

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
    public StructureModifier setASDYaw(PacketContainer spawnEntityLivingContainer, double yaw) {
        return spawnEntityLivingContainer.getBytes().write(1, (byte) (yaw * 256 / 360));
    }

    @Override
    public boolean isClickedFaceDirectional(BlockData blockData) {
        clickedFaceDirectionals.add(AmethystCluster.class);
        clickedFaceDirectionals.add(LightningRod.class);
        clickedFaceMaterials.add(Material.DEEPSLATE);
        clickedFaceMaterials.add(Material.INFESTED_DEEPSLATE);
        clickedFaceMaterials.add(Material.OCHRE_FROGLIGHT);
        clickedFaceMaterials.add(Material.PEARLESCENT_FROGLIGHT);
        clickedFaceMaterials.add(Material.VERDANT_FROGLIGHT);
        clickedFaceMaterials.add(Material.MUDDY_MANGROVE_ROOTS);
        return ProtocolProvider.super.isClickedFaceDirectional(blockData);
    }

    @Override
    public PacketContainer setEntityMetadata(ProtocolManager protocolManager, ArmorStandData asd) {
        try {
            PacketContainer entityMetadataContainer = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
            entityMetadataContainer.getIntegers().write(0, asd.getEntityID());
            List<WrappedDataValue> dataValues = new ArrayList<>();
            WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
            WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class);
            Class<?> vector3fClass = MinecraftReflection.getMinecraftClass("core.Vector3f");
            WrappedDataWatcher.Serializer vector3fSerializer = null;

            try {
                Field[] fields = WrappedDataWatcher.Registry.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType() == WrappedDataWatcher.Serializer.class) {
                        WrappedDataWatcher.Serializer serializer = (WrappedDataWatcher.Serializer) field.get(null);
                        if (serializer != null) {
                            try {
                                Method getTargetMethod = WrappedDataWatcher.Serializer.class.getDeclaredMethod("getTarget");
                                getTargetMethod.setAccessible(true);
                                Class<?> targetClass = (Class<?>) getTargetMethod.invoke(serializer);
                                if (targetClass != null && targetClass.equals(vector3fClass)) {
                                    vector3fSerializer = serializer;
                                    break;
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    }
                }

                if (vector3fSerializer == null) {
                    Object vector3f = createNMSVector3F(0, 0, 0);
                    vector3fSerializer = WrappedDataWatcher.Registry.get(vector3f.getClass());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (vector3fSerializer == null) {
                try {
                    Class<?> nmsSerializerRegistry = MinecraftReflection.getMinecraftClass("network.syncher.DataWatcherRegistry");
                    Field field = nmsSerializerRegistry.getDeclaredField("k");
                    field.setAccessible(true);
                    Object nmsSerializer = field.get(null);
                    Constructor<WrappedDataWatcher.Serializer> constructor = WrappedDataWatcher.Serializer.class.getDeclaredConstructor(Object.class);
                    constructor.setAccessible(true);
                    vector3fSerializer = constructor.newInstance(nmsSerializer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            dataValues.add(createWrappedDataValue(0, byteSerializer, (byte) (0x20 | 0x01)));

            dataValues.add(createWrappedDataValue(3, booleanSerializer, false));

            byte bitMask = (byte) ((asd.isSmall() ? 0x01 : 0x00) | 0x04 | 0x08 | 0x10);
            dataValues.add(createWrappedDataValue(getMaskIndex(), byteSerializer, bitMask));
            
            try {
                Object headRot = createNMSVector3F(
                        (float) asd.getHeadRotation().getX(), 
                        (float) asd.getHeadRotation().getY(), 
                        (float) asd.getHeadRotation().getZ()
                );
                dataValues.add(createWrappedDataValue(getHeadRotIndex(), vector3fSerializer, headRot));

                Object armRot = createNMSVector3F(
                        (float) asd.getArmRotation().getX(),
                        (float) asd.getArmRotation().getY(),
                        (float) asd.getArmRotation().getZ()
                );
                dataValues.add(createWrappedDataValue(getRightArmRotIndex(), vector3fSerializer, armRot));
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            entityMetadataContainer.getDataValueCollectionModifier().write(0, dataValues);
            
            return entityMetadataContainer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WrappedDataValue createWrappedDataValue(int index, WrappedDataWatcher.Serializer serializer, Object value) {
        return new WrappedDataValue(index, serializer, value);
    }
    
    private Object createNMSVector3F(float x, float y, float z) throws Exception {
        Class<?> vector3fClass = MinecraftReflection.getMinecraftClass("core.Vector3f");
        Constructor<?> constructor = vector3fClass.getDeclaredConstructor(float.class, float.class, float.class);
        constructor.setAccessible(true);
        return constructor.newInstance(x, y, z);
    }
}