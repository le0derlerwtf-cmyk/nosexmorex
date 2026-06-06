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
    public DisplayMode displayMode = DisplayMode.ACTIONBAR;
    @ConfigEntry.Category(value="general")
    @ConfigEntry.Gui.EnumHandler(option=ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public SwapItemsMode swapItemsMode = SwapItemsMode.EVERYTHING;
    @ConfigEntry.Category(value="general")
    @ConfigEntry.Gui.Tooltip
    public boolean autoRefillEnabled = false;
    @ConfigEntry.Category(value="aim")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=50L)
    public int aimSpeed = 15;
    @ConfigEntry.Category(value="aim")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=100L)
    public int elytraAimSpeed = 40;
    @ConfigEntry.Category(value="aim")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=50L)
    public int aimDistance = 5;
    @ConfigEntry.Category(value="aim")
    @ConfigEntry.Gui.Tooltip
    public boolean playerOnlyAim = true;
    @ConfigEntry.Category(value="aim")
    @ConfigEntry.Gui.Tooltip
    public boolean smartLockEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean axeSwapEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean allowAxeSwapHotkey = true;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean bypassShieldEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean elytraLaunchEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean elytraAutoHitEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean elytraWindBurstChain = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean tridentMaceComboEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean tridentAutoHitEnabled = false;
    @ConfigEntry.Category(value="combos")
    @ConfigEntry.Gui.Tooltip
    public boolean tridentWindBurstChain = false;
    @ConfigEntry.Category(value="misc")
    @ConfigEntry.Gui.Tooltip
    public boolean autoWindChargeEnabled = false;
    @ConfigEntry.Category(value="misc")
    @ConfigEntry.Gui.EnumHandler(option=ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public AutoWindChargeMode autoWindChargeMode = AutoWindChargeMode.ON_LOCK;
    @ConfigEntry.Category(value="misc")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=1L, max=10L)
    public int windFallDistance = 2;
    @ConfigEntry.Category(value="misc")
    @ConfigEntry.Gui.Tooltip
    public boolean autoMlgEnabled = false;
    @ConfigEntry.Category(value="misc")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min=5L, max=50L)
    public int mlgFallThreshold = 10;

    public static enum DisplayMode {
        ACTIONBAR("text.autoconfig.maceswap.option.displayMode.ACTIONBAR"),
        EXCLAMATION_MARK("text.autoconfig.maceswap.option.displayMode.EXCLAMATION_MARK");

        private final String key;

        private DisplayMode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum SwapItemsMode {
        ONLY_WEAPONS("text.autoconfig.maceswap.option.swapItemsMode.ONLY_WEAPONS"),
        EVERYTHING("text.autoconfig.maceswap.option.swapItemsMode.EVERYTHING");

        private final String key;

        private SwapItemsMode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public static enum AutoWindChargeMode {
        ON_LOCK("text.autoconfig.maceswap.option.autoWindChargeMode.ON_LOCK"),
        ALWAYS("text.autoconfig.maceswap.option.autoWindChargeMode.ALWAYS");

        private final String key;

        private AutoWindChargeMode(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }
}

