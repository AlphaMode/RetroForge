package net.fakecraft.entity;

public class Entity {
    public double posX, posY, posZ;
    public float rotationYaw;
    public float rotationPitch;
    public double motionX, motionY, motionZ;

    public int getEntityId() {
        return -1;
    }

    public DataWatcher getDataWatcher() {
        return null;
    }
}
