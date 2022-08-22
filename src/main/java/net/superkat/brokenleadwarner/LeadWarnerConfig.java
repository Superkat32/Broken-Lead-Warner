package net.superkat.brokenleadwarner;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screen.Screen;

@Config(name = BrokenLeadWarner.MOD_ID)
public class LeadWarnerConfig implements ConfigData {
    //This class is used by Cloth Config. It helps implement the config menu via Mod Menu.
    @ConfigEntry.Gui.Excluded
    private static LeadWarnerConfig INSTANCE;

    public boolean enabled = true;
    public boolean actionBar = false;
    public boolean playSound = true;

    public static LeadWarnerConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = AutoConfig.register(LeadWarnerConfig.class, GsonConfigSerializer::new).get();
        }
        return INSTANCE;
    }

    public Screen getScreen(Screen parent) {
        return AutoConfig.getConfigScreen(LeadWarnerConfig.class, parent).get();
    }

}


