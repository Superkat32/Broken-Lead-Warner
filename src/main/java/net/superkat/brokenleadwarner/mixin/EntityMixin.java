package net.superkat.brokenleadwarner.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.superkat.brokenleadwarner.duck.BreakableLeashableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin implements BreakableLeashableEntity {

    private boolean isInteract;
    private int lastHoldingEntityId;

    //Method code provided by Enriath (Updated slightly by Superkat32 for 1.21+ update, idea remains the same)
    // Keep track of if the lead was manually removed by hooking specifically when the game removes the lead.
    @Inject(method = "interact", at = @At("HEAD"))
    private void onEntityInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        this.isInteract = true;
    }

    @Override
    public boolean isInteract() {
        return this.isInteract;
    }

    @Override
    public void setIsInteract(boolean isInteract) {
        this.isInteract = isInteract;
    }

    @Override
    public int lastHoldingEntityId() {
        return this.lastHoldingEntityId;
    }

    @Override
    public void setLastHoldingEntityId(int id) {
        this.lastHoldingEntityId = id;
    }

}
