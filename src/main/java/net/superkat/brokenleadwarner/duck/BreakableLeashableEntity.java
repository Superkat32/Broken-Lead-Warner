package net.superkat.brokenleadwarner.duck;

//wow so good with names
public interface BreakableLeashableEntity {
    boolean isInteract();
    void setIsInteract(boolean isInteract);

    int lastHoldingEntityId();
    void setLastHoldingEntityId(int id);
}
