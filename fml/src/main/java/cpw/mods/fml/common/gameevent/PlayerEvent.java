package cpw.mods.fml.common.gameevent;

import net.fakecraft.entity.item.EntityItem;
import net.fakecraft.entity.player.EntityPlayer;
import net.fakecraft.inventory.IInventory;
import net.fakecraft.world.item.ItemInstance;
import cpw.mods.fml.common.eventhandler.Event;

public class PlayerEvent extends Event {
    public final EntityPlayer player;
    private PlayerEvent(EntityPlayer player)
    {
        this.player = player;
    }

    public static class ItemPickupEvent extends PlayerEvent {
        public final EntityItem pickedUp;
        public ItemPickupEvent(EntityPlayer player, EntityItem pickedUp)
        {
            super(player);
            this.pickedUp = pickedUp;
        }
    }

    public static class ItemCraftedEvent extends PlayerEvent {
        public final ItemInstance crafting;
        public final IInventory craftMatrix;
        public ItemCraftedEvent(EntityPlayer player, ItemInstance crafting, IInventory craftMatrix)
        {
            super(player);
            this.crafting = crafting;
            this.craftMatrix = craftMatrix;
        }
    }
    public static class ItemSmeltedEvent extends PlayerEvent {
        public final ItemInstance smelting;
        public ItemSmeltedEvent(EntityPlayer player, ItemInstance crafting)
        {
            super(player);
            this.smelting = crafting;
        }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(EntityPlayer player)
        {
            super(player);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(EntityPlayer player)
        {
            super(player);
        }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        public PlayerRespawnEvent(EntityPlayer player)
        {
            super(player);
        }
    }

    public static class PlayerChangedDimensionEvent extends PlayerEvent {
        public final int fromDim;
        public final int toDim;
        public PlayerChangedDimensionEvent(EntityPlayer player, int fromDim, int toDim)
        {
            super(player);
            this.fromDim = fromDim;
            this.toDim = toDim;
        }
    }
}
