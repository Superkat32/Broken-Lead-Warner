package net.superkat.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.tutorialmod.TutorialMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;

@Mixin(MobEntity.class)
public class ExampleMixin {
	public boolean actionBar = true;
	@Inject(at = @At("HEAD"), method = "detachLeash()V")
	private void init(boolean sendPacket, boolean dropItem, CallbackInfo info) {
		MobEntity self = (MobEntity) (Object) this;
		if (self.getHoldingEntity() != null) {
			if (!self.world.isClient && dropItem) {
//				TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
				sendWarningMessage();
//				playerEntity.sendSystemMessage(new LiteralMessage("Your lead has broken!"), Util.NIL_UUID);
				
			}
		}
//		TutorialMod.LOGGER.info("This line is printed by an example mod mixin!");
	}
	
	private void sendWarningMessage() {
		TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
		if (actionBar) {
			//Activates if the action bar setting is true.
			//This piece of code will warn the player that their lead has broken right above their hotbar(aka. the action bar)
			getInstance().inGameHud.setOverlayMessage(Text.literal("Your lead has broken!").formatted(Formatting.BOLD, Formatting.RED), false);
			actionBar = false; //temp
		} else {
			//This piece of code will warn the player with a big title message
			//There are 20 ticks a second
			//It will take 0.5 seconds for the title to fade in, stay on screen for 40 seconds, then fade out in 0.75 seconds.
			getInstance().inGameHud.setTitleTicks(10, 40, 15);
			getInstance().inGameHud.setTitle(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED));
			actionBar = true; //temp
		}
	}
	
}
