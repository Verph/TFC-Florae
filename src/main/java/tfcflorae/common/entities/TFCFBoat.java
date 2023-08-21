package tfcflorae.common.entities;

import java.util.function.Supplier;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import net.dries007.tfc.util.registry.RegistryWood;

import tfcflorae.common.blocks.wood.TFCFWood;

public class TFCFBoat extends Boat
{
    private final Supplier<? extends Item> drop;
    private final RegistryWood wood;

    public TFCFBoat(EntityType<? extends Boat> type, Level level, RegistryWood wood, Supplier<? extends Item> drop)
    {
        super(type, level);
        this.drop = drop;
        this.wood = wood;
    }

    @Override
    public Item getDropItem()
    {
        return drop.get();
    }

    @Override
    public Packet<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public double getPassengersRidingOffset()
    {
        return this.wood == TFCFWood.BAMBOO ? 0.25D : -0.1D;
    }
}
