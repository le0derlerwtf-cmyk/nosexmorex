/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.ConfigData
 *  me.shedaniel.autoconfig.annotation.Config
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$BoundedDiscrete
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Category
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$EnumHandler
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$EnumHandler$EnumDisplayOption
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$Excluded
 *  me.shedaniel.autoconfig.annotation.ConfigEntry$Gui$Tooltip
 */
package com.Cna4ik.maceswap.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="maceswap")
public class MaceSwapConfig
implements ConfigData {
    @ConfigEntry.Category(value="general")
    @ConfigEntry.Gui.Excluded
    public boolean hasShownDiscordPrompt = false;
    @ConfigEntry.Category(value="general")
    @ConfigEntry.Gui.EnumHandler(option=ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public DisplayMode displayMode = DisplayMode.LYRICA_CLIENT;
    @ConfigEntry.Category(value="general")
    @ConfigEntry.Gui.EnumHandler(option=ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public SwapItemsMode swapItemsMode = SwapItemsMode.EVERYTHING;
    @ConfigEntry.Category(value="swap")
    @ConfigEntry.Gui.Tooltip
    public boolean stunSlamEnabled = true;
    @ConfigEntry.Category(value="swap")
    @ConfigEntry.Gui.Tooltip
    public boolean autoReturnToAxeEnabled = false;
    @ConfigEntry.Category(value="hud")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=100L)
    public int saturationKeyBinding = 1;
    @ConfigEntry.Category(value="hud")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=100L)
    public int barKeyBinding = 2;

    public static enum DisplayMode {
        LYRICA_CLIENT("LyricaClient"),
        EXCLAMATION_MARK("Exclamation Mark");

        private final String key;

        private DisplayMode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum SwapItemsMode {
        ONLY_WEAPONS("Only Weapons"),
        EVERYTHING("Everything");

        private final String key;

        private SwapItemsMode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }
}
