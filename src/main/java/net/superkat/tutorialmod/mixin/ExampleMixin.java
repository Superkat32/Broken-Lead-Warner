package net.superkat.tutorialmod.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Items;
import net.superkat.tutorialmod.TutorialMod;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "detachLeash()V")
	private void init(boolean sendPacket, boolean dropItem, CallbackInfo info) {
		MobEntity self = (MobEntity) (Object) this;
		if (self.getHoldingEntity() != null) {
			if (!self.world.isClient && dropItem) {
				TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
			}
		}
//		TutorialMod.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
