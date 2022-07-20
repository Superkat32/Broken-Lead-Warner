package net.superkat.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.tutorialmod.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;

@Mixin(MobEntity.class)
public class ExampleMixin {
//	@Inject(at = @At("HEAD"), method = "detachLeash()")
	@Inject(at = @At("HEAD"), method = "detachLeash")
	public void init(boolean sendPacket, boolean dropItem, CallbackInfo info) {
		MobEntity self = (MobEntity) (Object) this;
		if (self.getHoldingEntity() != null) {
			if (!self.world.isClient && dropItem) {
				if (LeadWarnerConfig.getInstance().enabled)
					sendWarningMessage();
				else if (!LeadWarnerConfig.getInstance().enabled) {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
				} else {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Unknown reason.");
					//This method could be improved upon in the future if some serious bugs show up
				}
//				TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
//				playerEntity.sendSystemMessage(new LiteralMessage("Your lead has broken!"), Util.NIL_UUID);

			}
		}
//		TutorialMod.LOGGER.info("This line is printed by an example mod mixin!");
	}

	private void sendWarningMessage() {
		TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
		//TODO - Change LeadWarnerConfig to LeadWarnerConfig, and delete one of the two.
		//TODO - Add 2 more options: Rainbow text mode, and mod enabled.
		//FIXME - Mod sends message when lead breaks while on fence post.
		//Activates if the action bar setting is true
		playSoundEffect();
		if (LeadWarnerConfig.getInstance().actionBar) {
			//This piece of code will warn the player that their lead has broken right above their hotbar(aka. the action bar)
			getInstance().inGameHud.setOverlayMessage(Text.literal("Your lead has broken!").formatted(Formatting.BOLD, Formatting.RED), false);
			//Will play a pling sound when your lead breaks
			//TODO - Make the noteblock sound configurable, aka being able to choose which instrument gets used
			//TODO - Add a custom warning sound
//			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F));
		} else {
			//This piece of code will warn the player with a big title message at the center of their screen
			//There are 20 ticks a second
			//It will take 0.5 seconds for the title to fade in, stay on screen for 40 seconds, then fade out in 0.75 seconds
			getInstance().inGameHud.setTitleTicks(10, 40, 15);
			getInstance().inGameHud.setTitle(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED));
		}
	}

	private void playSoundEffect() {
		if (LeadWarnerConfig.getInstance().playSound) {
			//Plays note block "pling" sound
			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F));
			TutorialMod.LOGGER.info("Playing sound!");
		} else {
			TutorialMod.LOGGER.info("Playing Sound process abandoned. Option disabled.");
		}

	}

}
