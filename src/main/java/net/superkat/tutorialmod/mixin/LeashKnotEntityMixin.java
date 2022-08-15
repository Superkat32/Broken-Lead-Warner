package net.superkat.tutorialmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.superkat.tutorialmod.TutorialMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeashKnotEntity.class)
public abstract class LeashKnotEntityMixin {

//    boolean leashOnFenceBroken;

//    @Inject(at = @At("HEAD"), method = "onPlace")
//    public void init(CallbackInfo ci) {
//        TutorialMod.LOGGER.info("A leash has been placed on a fence");
//        TutorialMod.setLeashOnFence(true);
//    }
//
//    @Inject(at = @At("HEAD"), method = "onBreak")
//    public void init(Entity entity, CallbackInfo ci) {
//        TutorialMod.LOGGER.info("A leash on a fence has been broken");
//    }

//    public boolean isLeashOnFenceBroken() {
//        return leashOnFenceBroken;
//    }
//
//    public void setLeashOnFenceBroken(boolean leashOnFenceBroken) {
//        this.leashOnFenceBroken = leashOnFenceBroken;
//    }
}
