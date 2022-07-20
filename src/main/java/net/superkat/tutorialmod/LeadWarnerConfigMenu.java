package net.superkat.tutorialmod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screen.Screen;

@Config(name = TutorialMod.MOD_ID)
public class LeadWarnerConfigMenu implements ConfigData {
    //This class is used by Cloth Config. It helps implement the config menu via Mod Menu.
    @ConfigEntry.Gui.Excluded
    private static LeadWarnerConfigMenu INSTANCE;

    public boolean enabled = true;

    public static LeadWarnerConfigMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = AutoConfig.register(LeadWarnerConfigMenu.class, GsonConfigSerializer::new).get();
        }

        return INSTANCE;
    }

    public Screen getScreen(Screen parent) {
        return AutoConfig.getConfigScreen(LeadWarnerConfigMenu.class, parent).get();
    }

}


