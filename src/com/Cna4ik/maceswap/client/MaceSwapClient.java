/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.AutoConfig
 *  me.shedaniel.autoconfig.serializer.GsonConfigSerializer
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1743
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1829
 *  net.minecraft.class_1835
 *  net.minecraft.class_2561
 *  net.minecraft.class_2596
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_3675$class_307
 *  net.minecraft.class_437
 *  net.minecraft.class_442
 */
package com.Cna4ik.maceswap.client;

import com.Cna4ik.maceswap.client.DiscordPromptScreen;
import com.Cna4ik.maceswap.config.MaceSwapConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1829;
import net.minecraft.class_1835;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import net.minecraft.class_437;
import net.minecraft.class_442;

@Environment(value=EnvType.CLIENT)
public class MaceSwapClient
implements ClientModInitializer {
    private static boolean promptChecked = false;
    private static class_304 toggleKey;
    public static boolean isAutoMaceEnabled;
    public static boolean isPerformingInternalAttack;
    private static int swapStep;
    private static int queuedOriginalSlot;
    private boolean wasUsePressed = false;
    private boolean wasAttackPressed = false;
    private static int axeSwapState;
    private static int axeOriginalSlot;
    private static int axeMaceSlot;
    private static class_1297 queuedAxeTarget;

    public void onInitializeClient() {
        AutoConfig.register(MaceSwapConfig.class, GsonConfigSerializer::new);
        String category = "key.category.maceswap.keys";
        toggleKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.maceswap.swap", class_3675.class_307.field_1668, 82, category));
        
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MaceSwapConfig config = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
            class_310 client = class_310.method_1551();
            if (client.field_1724 != null) {
                if (config.displayMode == MaceSwapConfig.DisplayMode.LYRICA_CLIENT) {
                    String indicator = isAutoMaceEnabled ? "LyricaClient." : "LyricaClient";
                    drawContext.method_25303(client.field_1772, indicator, 10, 10, 11184810); // Purple color (0xAA77FF)
                }
            }
        });
        
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!promptChecked && client.field_1755 instanceof class_442) {
                MaceSwapConfig conf = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
                if (!conf.hasShownDiscordPrompt) {
                    client.method_1507((class_437)new DiscordPromptScreen(client.field_1755));
                }
                promptChecked = true;
            }
            
            if (client.field_1724 == null || client.field_1687 == null) {
                return;
            }
            
            MaceSwapConfig config = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
            
            boolean currentUsePressed = client.field_1690.field_1904.method_1434();
            boolean currentAttackPressed = client.field_1690.field_1886.method_1434();
            boolean attackJustPressed = currentAttackPressed && !this.wasAttackPressed;
            
            if (config.stunSlamEnabled && attackJustPressed && isAutoMaceEnabled) {
                // Stun slam happens naturally when attacking
            }
            
            while (toggleKey.method_1436()) {
                isAutoMaceEnabled = !isAutoMaceEnabled;
                String message = isAutoMaceEnabled ? "Mace Swap Enabled" : "Mace Swap Disabled";
                client.field_1724.method_7353((class_2561)class_2561.method_43471((String)message), true);
            }
            
            if (swapStep == 1) {
                if (queuedOriginalSlot != -1) {
                    client.field_1724.method_31548().field_7545 = queuedOriginalSlot;
                }
                swapStep = 0;
                queuedOriginalSlot = -1;
            }
            
            if (axeSwapState == 2) {
                if (axeOriginalSlot != -1 && client.field_1761 != null) {
                    client.field_1724.method_31548().field_7545 = axeOriginalSlot;
                }
                axeSwapState = 0;
                axeOriginalSlot = -1;
                axeMaceSlot = -1;
                queuedAxeTarget = null;
            } else if (axeSwapState == 1) {
                if (queuedAxeTarget != null && queuedAxeTarget.method_5805() && client.field_1761 != null) {
                    int maceSlot = -1;
                    for (int i = 0; i < 9; ++i) {
                        if (!client.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_49814)) continue;
                        maceSlot = i;
                        break;
                    }
                    if (maceSlot != -1) {
                        axeOriginalSlot = client.field_1724.method_31548().field_7545;
                        axeMaceSlot = maceSlot;
                        axeSwapState = 2;
                        client.field_1724.method_31548().field_7545 = axeMaceSlot;
                        isPerformingInternalAttack = true;
                        try {
                            client.field_1761.method_2918((class_1657)client.field_1724, queuedAxeTarget);
                        }
                        finally {
                            isPerformingInternalAttack = false;
                        }
                        client.field_1724.method_6104(class_1268.field_5808);
                    } else {
                        axeSwapState = 0;
                        queuedAxeTarget = null;
                    }
                } else {
                    axeSwapState = 0;
                    queuedAxeTarget = null;
                }
            }
            
            this.wasUsePressed = currentUsePressed;
            this.wasAttackPressed = currentAttackPressed;
        });
    }

    public static boolean isWeaponOrTool(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        class_1792 item = stack.method_7909();
        return item instanceof class_1829 || item instanceof class_1743 || item instanceof class_1835;
    }

    private static int findInHotbar(class_1661 inv, class_1792 item) {
        for (int i = 0; i < 9; ++i) {
            if (!inv.method_5438(i).method_31574(item)) continue;
            return i;
        }
        return -1;
    }

    public static boolean hasLineOfSight(class_310 client, class_1297 target) {
        if (client.field_1724 == null || client.field_1687 == null) {
            return false;
        }
        return true;
    }

    public static boolean handleGhostSwapAttack(class_1657 player, class_1297 target) {
        if (isPerformingInternalAttack) {
            return false;
        }
        if (player.method_6047().method_31574(class_1802.field_49814)) {
            return false;
        }
        if (!isAutoMaceEnabled) {
            return false;
        }
        
        MaceSwapConfig config = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
        if (config.swapItemsMode == MaceSwapConfig.SwapItemsMode.ONLY_WEAPONS && !MaceSwapClient.isWeaponOrTool(player.method_6047())) {
            return false;
        }
        
        class_1661 inv = player.method_31548();
        int maceSlot = -1;
        for (int i = 0; i < 9; ++i) {
            if (!inv.method_5438(i).method_31574(class_1802.field_49814)) continue;
            maceSlot = i;
            break;
        }
        
        if (maceSlot != -1) {
            class_310 client = class_310.method_1551();
            int originalSlot = inv.field_7545;
            if (swapStep == 0 && client.field_1761 != null) {
                queuedOriginalSlot = originalSlot;
                swapStep = 1;
                inv.field_7545 = maceSlot;
                isPerformingInternalAttack = true;
                try {
                    client.field_1761.method_2918(player, target);
                }
                finally {
                    isPerformingInternalAttack = false;
                }
                player.method_6104(class_1268.field_5808);
                
                MaceSwapConfig cfg = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
                if (!cfg.autoReturnToAxeEnabled && player.method_6047().method_7909() instanceof class_1743) {
                    // Don't auto return - player controls when to return
                }
                
                return true;
            }
        }
        return false;
    }

    static {
        isAutoMaceEnabled = false;
        isPerformingInternalAttack = false;
        swapStep = 0;
        queuedOriginalSlot = -1;
        axeSwapState = 0;
        axeOriginalSlot = -1;
        axeMaceSlot = -1;
        queuedAxeTarget = null;
    }
}
