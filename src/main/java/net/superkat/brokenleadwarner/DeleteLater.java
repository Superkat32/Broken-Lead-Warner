package net.superkat.brokenleadwarner;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screen.Screen;

@Config(name = BrokenLeadWarner.MOD_ID)
public class DeleteLater implements ConfigData {
    //This class is used by Cloth Config. It helps implement the config menu via Mod Menu.
    @ConfigEntry.Gui.Excluded
    private static DeleteLater INSTANCE;

    public boolean enabled = true;
    public boolean altWarningMethod = false;
    public boolean playSound = true;
    public boolean showText = true;

    public static DeleteLater getInstance() {
        if (INSTANCE == null) {
            INSTANCE = AutoConfig.register(DeleteLater.class, GsonConfigSerializer::new).get();
        }
        return INSTANCE;
    }

    public Screen getScreen(Screen parent) {
        return AutoConfig.getConfigScreen(DeleteLater.class, parent).get();
    }

}


