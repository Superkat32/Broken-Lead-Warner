package net.superkat.tutorialmod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = TutorialMod.MOD_ID)
public class LeadWarnerConfigMenu implements LeadWarnerConfig, ConfigData {
    //This class is used by Cloth Config. It helps implement the config menu via Mod Menu.
    @ConfigEntry.Gui.Excluded
    private static final LeadWarnerConfigMenu INSTANCE;

//    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip(count = 4)
    public boolean actionBar = LeadWarnerConfig.super.actionBar();

//    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean rainbowText = LeadWarnerConfig.super.rainbowText();

    public static LeadWarnerConfig getInstance() {
        return INSTANCE;
    }

    private LeadWarnerConfigMenu() { }

    @Override
    public boolean actionBar() {
        return this.actionBar;
    }

    @Override
    public boolean rainbowText() {
        return this.rainbowText;
    }

    static {
        INSTANCE = AutoConfig.register(LeadWarnerConfigMenu.class, GsonConfigSerializer::new).getConfig();
    }
}


