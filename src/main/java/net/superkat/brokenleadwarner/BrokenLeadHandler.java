package net.superkat.brokenleadwarner;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.superkat.brokenleadwarner.config.LeadWarnerConfig;

public class BrokenLeadHandler {

    //To add a new warning method, please follow the following steps, future self...
    //First, begin by adding the enum to LeadWarnerConfig.java
    //Second, add an else/if statement here
    //Afterwards, add the text to the language json file(s)
    //Debug if needed... Debug, because it will be needed
    //And ta-da! You now have a new warning method!

    public static void onBrokenLead(Entity entity) {
        sendWarningMessage(entity);
        playWarningSound();
    }

    public static void sendWarningMessage(Entity entity) {
        if (!LeadWarnerConfig.showText) return;

        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        MutableText message = Text.translatable("chat.brokenleadwarner.broken_lead").formatted(Formatting.BOLD, Formatting.RED);
        if(LeadWarnerConfig.showMobName) {
            Text mobName = Text.translatable("chat.brokenleadwarner.mob_name", entity.getDisplayName());
            message.append(mobName);
        }

        if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.HOTBAR)) {
            //Sends a hotbar message. (A small piece of text above the player's hotbar)
            client.player.sendMessage(message,true);
        } else if (LeadWarnerConfig.warningMethodEnum.equals(LeadWarnerConfig.WarningMethodEnum.CHAT)) {
            //Sends a chat message to the player. Unfortunately, it will have a grey mark next to it
            client.player.sendMessage(message,false);
        }
    }

    public static void playWarningSound() {
        if (LeadWarnerConfig.playSound) {
            SoundEvent sound;
            if (LeadWarnerConfig.warningSoundType == LeadWarnerConfig.WarningSoundType.VANILLA_SNAP) {
                sound = SoundEvents.ITEM_LEAD_BREAK;
            } else {
                sound = BrokenLeadWarner.PING_WARNING_SOUND_EVENT;
            }
            float warningSoundVolume = LeadWarnerConfig.soundVolume / 100;
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, 1.0F, warningSoundVolume));
        }
    }

}
