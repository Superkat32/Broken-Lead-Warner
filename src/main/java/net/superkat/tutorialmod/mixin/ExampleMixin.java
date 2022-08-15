package net.superkat.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.superkat.tutorialmod.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.MinecraftClient.getInstance;

@Mixin(PathAwareEntity.class)
public abstract class ExampleMixin {
//	@Shadow public abstract BlockPos getPositionTarget();

//	@Shadow
//	private BlockPos positionTarget;
//
//	@Shadow
//	protected abstract void playHurtSound(DamageSource source);
//
//	@Shadow
//	@Nullable
//	private Entity holdingEntity;
//
//	@Shadow
//	private int holdingEntityId;

	//	@Inject(at = @At("HEAD"), method = "detachLeash()")
	@Inject(at = @At("HEAD"), method = "updateLeash")
	public void init(CallbackInfo ci) {
		PathAwareEntity self = (PathAwareEntity) (Object) this;
		Entity entity = self.getHoldingEntity();
		if (entity != null && entity.world == self.world) {
			float f = self.distanceTo(entity);
//			if (self instanceof TameableEntity && ((TameableEntity)self).isInSittingPose()) {
			if (f > 10.0F) {
				if (LeadWarnerConfig.getInstance().enabled) {
					sendWarningMessage();
				} else if (!LeadWarnerConfig.getInstance().enabled) {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Mod has been disabled.");
				} else {
					TutorialMod.LOGGER.info("Warning Message process abandoned. Unknown reason.");
					//This method could be improved upon in the future if some serious bugs show up
				}
			}
//			}
		}
//		TutorialMod.LOGGER.info("This line is printed by an example mod mixin!");
	}


	private void sendWarningMessage() {
		TutorialMod.LOGGER.info("YOUR LEAD HAS HEREBY BEEN DECLARED; BROKEN!!!");
		/*
		FIXME - Mod sends message when lead breaks while on fence post.
		FIXME - Sound will play even if the leash was on a fence
		FIXME - Mod will completely break if you put a leash on a fence, then have another lead break, then break the tied lead
		FIXME - Mod will amplify sound if multiple leads break
		FIXME - Change the whole method on how the mod realizes that the lead is tied to a fence.
		*/
		playSoundEffect();
		if (LeadWarnerConfig.getInstance().actionBar) {
			//Activates if the action bar setting is true
			//This piece of code will warn the player that their lead has broken right above their hotbar(aka. the action bar)
			getInstance().inGameHud.setOverlayMessage(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED), false);
//			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F));
		} else if (!LeadWarnerConfig.getInstance().actionBar) {
			//This piece of code will warn the player with a big title message at the center of their screen
			//There are 20 ticks a second
			//It will take 0.5 seconds for the title to fade in, stay on screen for 40 seconds, then fade out in 0.75 seconds
			getInstance().inGameHud.setTitleTicks(10, 40, 15);
			getInstance().inGameHud.setTitle(Text.literal("Your lead broke!").formatted(Formatting.BOLD, Formatting.RED));
		} else {
			TutorialMod.LOGGER.info("No warning message sent: none of the boolean conditions were met.");
		}
	}

	private void playSoundEffect() {
		if (LeadWarnerConfig.getInstance().playSound) {
			//Plays warning sound
//			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(TutorialMod.MY_SOUND_EVENT, 1.0F, 1.0F));
//			MinecraftClient.getInstance().getSoundManager().play((SoundInstance) TutorialMod.MY_SOUND_EVENT);
			MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(TutorialMod.MY_SOUND_EVENT, 1.0F, 1.0F));
			TutorialMod.LOGGER.info("Playing sound!");
		} else {
			TutorialMod.LOGGER.info("Playing Sound process abandoned. Option disabled.");
		}

	}

}
