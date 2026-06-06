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
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1661
 *  net.minecraft.class_1713
 *  net.minecraft.class_1743
 *  net.minecraft.class_1753
 *  net.minecraft.class_1764
 *  net.minecraft.class_1792
 *  net.minecraft.class_1794
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1810
 *  net.minecraft.class_1819
 *  net.minecraft.class_1821
 *  net.minecraft.class_1829
 *  net.minecraft.class_1835
 *  net.minecraft.class_238
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_2596
 *  net.minecraft.class_2828$class_2829
 *  net.minecraft.class_2828$class_2831
 *  net.minecraft.class_2848
 *  net.minecraft.class_2848$class_2849
 *  net.minecraft.class_2868
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_3675$class_307
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_437
 *  net.minecraft.class_442
 *  net.minecraft.class_9362
 */
package com.Cna4ik.maceswap.client;

import com.Cna4ik.maceswap.client.DiscordPromptScreen;
import com.Cna4ik.maceswap.client.TargetAssist;
import com.Cna4ik.maceswap.config.MaceSwapConfig;
import java.util.Optional;
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
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1713;
import net.minecraft.class_1743;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1794;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1810;
import net.minecraft.class_1819;
import net.minecraft.class_1821;
import net.minecraft.class_1829;
import net.minecraft.class_1835;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_2848;
import net.minecraft.class_2868;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3675;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_437;
import net.minecraft.class_442;
import net.minecraft.class_9362;

@Environment(value=EnvType.CLIENT)
public class MaceSwapClient
implements ClientModInitializer {
    private static boolean promptChecked = false;
    private static class_304 toggleKey;
    private static class_304 axeToggleKey;
    private static class_304 elytraLaunchKey;
    public static boolean isAutoMaceEnabled;
    public static boolean wasWhiteModeBeforeAim;
    public static boolean isPerformingInternalAttack;
    private static int swapStep;
    private static int queuedOriginalSlot;
    private boolean wasUsePressed = false;
    private boolean wasAttackPressed = false;
    private static int axeSwapState;
    private static int axeOriginalSlot;
    private static int axeMaceSlot;
    private static class_1297 queuedAxeTarget;
    private static boolean wasOnGround;
    private static float trackedFallDistance;
    public static int elytraState;
    private static int elytraOriginalSlot;
    private static int elytraDelayTimer;
    private static class_1297 elytraTarget;
    public static int tridentState;
    private static int tridentDelayTimer;

    public void onInitializeClient() {
        AutoConfig.register(MaceSwapConfig.class, GsonConfigSerializer::new);
        String category = "key.category.maceswap.keys";
        toggleKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.maceswap.swap", class_3675.class_307.field_1668, 82, category));
        axeToggleKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.maceswap.axe_swap", class_3675.class_307.field_1668, 71, category));
        elytraLaunchKey = KeyBindingHelper.registerKeyBinding((class_304)new class_304("key.maceswap.elytra", class_3675.class_307.field_1668, 86, category));
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MaceSwapConfig config = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
            class_310 client = class_310.method_1551();
            if (client.field_1724 != null && client.field_1687 != null) {
                if (elytraState == 4 || tridentState >= 1 && tridentState <= 2) {
                    client.field_1724.method_36457(-90.0f);
                } else if (elytraState >= 5) {
                    if (elytraTarget != null && elytraTarget.method_5805() && !elytraTarget.method_31481()) {
                        double dx = elytraTarget.method_23317() - client.field_1724.method_23317();
                        double dy = elytraTarget.method_23320() - client.field_1724.method_23320();
                        double dz = elytraTarget.method_23321() - client.field_1724.method_23321();
                        double diffXZ = Math.sqrt(dx * dx + dz * dz);
                        float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
                        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, diffXZ)));
                        float yawDiff = class_3532.method_15393((float)(targetYaw - client.field_1724.method_36454()));
                        float pitchDiff = class_3532.method_15393((float)(targetPitch - client.field_1724.method_36455()));
                        float maxStep = (float)config.elytraAimSpeed / 20.0f;
                        client.field_1724.method_36456(client.field_1724.method_36454() + class_3532.method_15363((float)(yawDiff * 0.1f), (float)(-maxStep), (float)maxStep));
                        client.field_1724.method_36457(client.field_1724.method_36455() + class_3532.method_15363((float)(pitchDiff * 0.1f), (float)(-maxStep), (float)maxStep));
                    }
                } else if (tridentState >= 3 && tridentState <= 6) {
                    if (TargetAssist.lockedTarget != null && TargetAssist.lockedTarget.method_5805() && !TargetAssist.lockedTarget.method_31481()) {
                        double dx = TargetAssist.lockedTarget.method_23317() - client.field_1724.method_23317();
                        double dy = TargetAssist.lockedTarget.method_23320() - client.field_1724.method_23320();
                        double dz = TargetAssist.lockedTarget.method_23321() - client.field_1724.method_23321();
                        double diffXZ = Math.sqrt(dx * dx + dz * dz);
                        float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
                        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, diffXZ)));
                        float yawDiff = class_3532.method_15393((float)(targetYaw - client.field_1724.method_36454()));
                        float pitchDiff = class_3532.method_15393((float)(targetPitch - client.field_1724.method_36455()));
                        float maxStep = (float)config.elytraAimSpeed / 20.0f;
                        client.field_1724.method_36456(client.field_1724.method_36454() + class_3532.method_15363((float)(yawDiff * 0.1f), (float)(-maxStep), (float)maxStep));
                        client.field_1724.method_36457(client.field_1724.method_36455() + class_3532.method_15363((float)(pitchDiff * 0.1f), (float)(-maxStep), (float)maxStep));
                    }
                } else if (elytraState == 0 && tridentState == 0) {
                    TargetAssist.tick(client, config);
                }
            }
            if (config.displayMode == MaceSwapConfig.DisplayMode.EXCLAMATION_MARK) {
                if (TargetAssist.isActive) {
                    drawContext.method_25303(client.field_1772, "!", 10, drawContext.method_51443() - 15, -16777216);
                } else if (isAutoMaceEnabled) {
                    drawContext.method_25303(client.field_1772, "!", 10, drawContext.method_51443() - 15, -1);
                }
            }
        });
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            double dist;
            class_243 down;
            class_243 pos;
            class_3965 hit;
            int wSlot;
            boolean currentUsePressed;
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
            if (config.autoRefillEnabled && client.field_1755 == null && client.field_1724.field_6012 % 10 == 0) {
                MaceSwapClient.refillItem(client, class_1802.field_49098);
                MaceSwapClient.refillItem(client, class_1802.field_8639);
            }
            if (TargetAssist.isActive && TargetAssist.lockedTarget != null && (!TargetAssist.lockedTarget.method_5805() || TargetAssist.lockedTarget.method_31481())) {
                TargetAssist.isActive = false;
                TargetAssist.lockedTarget = null;
                isAutoMaceEnabled = wasWhiteModeBeforeAim;
                if (config.displayMode == MaceSwapConfig.DisplayMode.ACTIONBAR) {
                    client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.maceswap.aim_lost"), true);
                }
            }
            boolean useJustPressed = (currentUsePressed = client.field_1690.field_1904.method_1434()) && !this.wasUsePressed;
            boolean currentAttackPressed = client.field_1690.field_1886.method_1434();
            boolean attackJustPressed = currentAttackPressed && !this.wasAttackPressed;
            boolean isOnGround = client.field_1724.method_24828();
            boolean isWindAllowed = false;
            if (isAutoMaceEnabled) {
                if (config.autoWindChargeMode == MaceSwapConfig.AutoWindChargeMode.ALWAYS) {
                    isWindAllowed = true;
                } else if (config.autoWindChargeMode == MaceSwapConfig.AutoWindChargeMode.ON_LOCK && TargetAssist.isActive) {
                    isWindAllowed = true;
                }
            }
            boolean fallTrigger = !wasOnGround && isOnGround && trackedFallDistance > (float)config.windFallDistance;
            boolean rmbTrigger = useJustPressed;
            if (config.autoWindChargeEnabled && isWindAllowed && (fallTrigger || rmbTrigger) && (wSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_49098)) != -1 && client.field_1761 != null && client.method_1562() != null) {
                boolean lookingAtBlock = client.field_1765 != null && client.field_1765.method_17783() == class_239.class_240.field_1332;
                boolean holdingValidItem = MaceSwapClient.isMeleeWeaponOrEmptyOrWindCharge(client.field_1724.method_6047());
                if (fallTrigger || rmbTrigger && holdingValidItem && !lookingAtBlock) {
                    int originalSlot = client.field_1724.method_31548().field_7545;
                    float originalPitch = client.field_1724.method_36455();
                    client.field_1724.method_31548().field_7545 = wSlot;
                    client.method_1562().method_52787((class_2596)new class_2868(wSlot));
                    client.field_1724.method_36457(90.0f);
                    client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), 90.0f, client.field_1724.method_24828()));
                    client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                    client.field_1724.method_6104(class_1268.field_5808);
                    client.field_1724.method_6043();
                    client.field_1724.method_36457(originalPitch);
                    client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), originalPitch, client.field_1724.method_24828()));
                    client.field_1724.method_31548().field_7545 = originalSlot;
                    client.method_1562().method_52787((class_2596)new class_2868(originalSlot));
                }
            }
            if (config.tridentMaceComboEnabled && TargetAssist.isActive && client.field_1724.method_6047().method_31574(class_1802.field_8547) && useJustPressed && tridentState == 0) {
                tridentState = 1;
            }
            if (tridentState > 0 && client.field_1761 != null && client.method_1562() != null) {
                if (TargetAssist.lockedTarget == null || !TargetAssist.lockedTarget.method_5805() || TargetAssist.lockedTarget.method_31481()) {
                    tridentState = 0;
                    client.field_1690.field_1904.method_23481(false);
                    client.field_1761.method_2897((class_1657)client.field_1724);
                } else if (tridentState == 1) {
                    client.field_1724.method_36457(-90.0f);
                    client.field_1690.field_1904.method_23481(true);
                    tridentDelayTimer = 15;
                    tridentState = 2;
                } else if (tridentState == 2) {
                    client.field_1724.method_36457(-90.0f);
                    if (tridentDelayTimer > 0) {
                        --tridentDelayTimer;
                    } else {
                        client.field_1690.field_1904.method_23481(false);
                        client.field_1761.method_2897((class_1657)client.field_1724);
                        tridentDelayTimer = 10;
                        tridentState = 3;
                    }
                } else if (tridentState == 3) {
                    MaceSwapClient.aimAtTarget(client, TargetAssist.lockedTarget);
                    if (tridentDelayTimer > 0) {
                        --tridentDelayTimer;
                    } else if (client.field_1724.method_18798().field_1351 <= 0.1 && !client.field_1724.method_24828()) {
                        tridentSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_8547);
                        if (tridentSlot != -1) {
                            client.field_1724.method_31548().field_7545 = tridentSlot;
                            client.method_1562().method_52787((class_2596)new class_2868(tridentSlot));
                        }
                        client.field_1690.field_1904.method_23481(true);
                        tridentDelayTimer = 15;
                        tridentState = 4;
                    } else if (client.field_1724.method_24828()) {
                        tridentState = 0;
                    }
                } else if (tridentState == 4) {
                    MaceSwapClient.aimAtTarget(client, TargetAssist.lockedTarget);
                    if (tridentDelayTimer > 0) {
                        --tridentDelayTimer;
                    } else {
                        client.field_1690.field_1904.method_23481(false);
                        client.field_1761.method_2897((class_1657)client.field_1724);
                        tridentDelayTimer = 2;
                        tridentState = 5;
                    }
                } else if (tridentState == 5) {
                    MaceSwapClient.aimAtTarget(client, TargetAssist.lockedTarget);
                    if (tridentDelayTimer > 0) {
                        --tridentDelayTimer;
                    } else {
                        int maceSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_49814);
                        if (maceSlot != -1) {
                            client.field_1724.method_31548().field_7545 = maceSlot;
                            client.method_1562().method_52787((class_2596)new class_2868(maceSlot));
                        }
                        tridentState = 6;
                    }
                } else if (tridentState == 6) {
                    MaceSwapClient.aimAtTarget(client, TargetAssist.lockedTarget);
                    if (client.field_1724.method_24828()) {
                        tridentState = 0;
                    } else if (config.tridentAutoHitEnabled && (double)client.field_1724.method_5739(TargetAssist.lockedTarget) <= 3.5) {
                        client.field_1761.method_2918((class_1657)client.field_1724, TargetAssist.lockedTarget);
                        client.field_1724.method_6104(class_1268.field_5808);
                        if (config.tridentWindBurstChain) {
                            tridentDelayTimer = 5;
                            tridentState = 7;
                        } else {
                            tridentState = 0;
                        }
                    }
                } else if (tridentState == 7) {
                    if (tridentDelayTimer > 0) {
                        --tridentDelayTimer;
                    } else if (client.field_1724.method_18798().field_1351 > 0.3) {
                        tridentSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_8547);
                        if (tridentSlot != -1) {
                            client.field_1724.method_31548().field_7545 = tridentSlot;
                            client.method_1562().method_52787((class_2596)new class_2868(tridentSlot));
                            tridentState = 1;
                        } else {
                            tridentState = 0;
                        }
                    } else if (client.field_1724.method_24828()) {
                        tridentState = 0;
                    }
                }
            }
            if (config.autoMlgEnabled && !isOnGround && client.field_1724.field_6017 > (float)config.mlgFallThreshold && (hit = client.field_1687.method_17742(new class_3959(pos = new class_243(client.field_1724.method_23317(), client.field_1724.method_23318(), client.field_1724.method_23321()), down = pos.method_1031(0.0, -4.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)client.field_1724))).method_17783() == class_239.class_240.field_1332 && (dist = pos.field_1351 - hit.method_17784().field_1351) < 3.5 && dist > 0.0) {
                int safeSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_49098);
                if (safeSlot == -1) {
                    safeSlot = MaceSwapClient.findInHotbar(client.field_1724.method_31548(), class_1802.field_8705);
                }
                if (safeSlot != -1 && client.method_1562() != null && client.field_1761 != null) {
                    int originalSlot = client.field_1724.method_31548().field_7545;
                    float originalPitch = client.field_1724.method_36455();
                    client.field_1724.method_31548().field_7545 = safeSlot;
                    client.field_1724.method_36457(90.0f);
                    client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), 90.0f, client.field_1724.method_24828()));
                    client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                    client.field_1724.method_6104(class_1268.field_5808);
                    client.field_1724.method_36457(originalPitch);
                    client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), originalPitch, client.field_1724.method_24828()));
                    client.field_1724.method_31548().field_7545 = originalSlot;
                }
            }
            while (elytraLaunchKey.method_1436()) {
                class_1297 target;
                if (!config.elytraLaunchEnabled || elytraState != 0 || (target = MaceSwapClient.getLookingAtEntity(client, config)) == null) continue;
                elytraTarget = target;
                elytraState = 1;
                elytraOriginalSlot = client.field_1724.method_31548().field_7545;
                TargetAssist.isActive = true;
                TargetAssist.lockedTarget = target;
                isAutoMaceEnabled = true;
                if (config.displayMode != MaceSwapConfig.DisplayMode.ACTIONBAR) continue;
                client.field_1724.method_7353((class_2561)class_2561.method_43469((String)"message.maceswap.aim_locked", (Object[])new Object[]{target.method_5476().getString()}), true);
            }
            if (elytraState > 0 && client.field_1761 != null) {
                class_1661 inv = client.field_1724.method_31548();
                if (isOnGround && elytraState > 3) {
                    if (elytraState != 7 || elytraDelayTimer <= 0) {
                        inv.field_7545 = elytraOriginalSlot;
                        elytraState = 0;
                        elytraTarget = null;
                    }
                } else if (elytraState == 1) {
                    if (!client.field_1724.method_6118(class_1304.field_6174).method_31574(class_1802.field_8833)) {
                        elytraSlot = MaceSwapClient.findInHotbar(inv, class_1802.field_8833);
                        if (elytraSlot != -1) {
                            inv.field_7545 = elytraSlot;
                            client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                        } else {
                            elytraState = 0;
                        }
                    }
                    if (elytraState != 0) {
                        if (client.field_1724.method_24828()) {
                            client.field_1724.method_6043();
                        }
                        elytraDelayTimer = 3;
                        elytraState = 2;
                    }
                } else if (elytraState == 2) {
                    if (elytraDelayTimer > 0) {
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2848((class_1297)client.field_1724, class_2848.class_2849.field_12982));
                        }
                        --elytraDelayTimer;
                    } else {
                        elytraState = 3;
                    }
                } else if (elytraState == 3) {
                    fwSlot = MaceSwapClient.findInHotbar(inv, class_1802.field_8639);
                    if (fwSlot != -1) {
                        inv.field_7545 = fwSlot;
                        client.field_1724.method_36457(-90.0f);
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), -90.0f, client.field_1724.method_24828()));
                        }
                        client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                        client.field_1724.method_6104(class_1268.field_5808);
                        elytraDelayTimer = 15;
                        elytraState = 4;
                    } else {
                        inv.field_7545 = elytraOriginalSlot;
                        elytraState = 0;
                        elytraTarget = null;
                    }
                } else if (elytraState == 4) {
                    if (elytraDelayTimer > 0) {
                        client.field_1724.method_36457(-90.0f);
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2828.class_2831(client.field_1724.method_36454(), -90.0f, client.field_1724.method_24828()));
                        }
                        --elytraDelayTimer;
                    } else {
                        elytraState = 5;
                    }
                } else if (elytraState == 5) {
                    class_243 down2;
                    class_243 pos2;
                    class_3965 hit2;
                    boolean shouldEquip = false;
                    if (elytraTarget != null && (!elytraTarget.method_5805() || elytraTarget.method_31481())) {
                        elytraTarget = null;
                    }
                    if (elytraTarget != null && (double)client.field_1724.method_5739(elytraTarget) <= 5.0) {
                        shouldEquip = true;
                    }
                    if ((hit2 = client.field_1687.method_17742(new class_3959(pos2 = new class_243(client.field_1724.method_23317(), client.field_1724.method_23318(), client.field_1724.method_23321()), down2 = pos2.method_1031(0.0, -5.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)client.field_1724))).method_17783() == class_239.class_240.field_1332 && pos2.field_1351 - hit2.method_17784().field_1351 <= 5.0) {
                        shouldEquip = true;
                    }
                    if (shouldEquip) {
                        int chestSlot = MaceSwapClient.findChestplateInHotbar(inv);
                        if (chestSlot != -1) {
                            inv.field_7545 = chestSlot;
                            client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                        }
                        inv.field_7545 = elytraOriginalSlot;
                        if (config.elytraAutoHitEnabled && elytraTarget != null) {
                            elytraState = 6;
                        } else {
                            elytraState = 0;
                            elytraTarget = null;
                        }
                    }
                } else if (elytraState == 6) {
                    if (elytraTarget != null && elytraTarget.method_5805() && !elytraTarget.method_31481()) {
                        if ((double)client.field_1724.method_5739(elytraTarget) <= 3.5) {
                            client.field_1761.method_2918((class_1657)client.field_1724, elytraTarget);
                            client.field_1724.method_6104(class_1268.field_5808);
                            if (config.elytraWindBurstChain) {
                                elytraDelayTimer = 5;
                                elytraState = 7;
                            } else {
                                elytraState = 0;
                                elytraTarget = null;
                            }
                        }
                    } else {
                        elytraState = 0;
                        elytraTarget = null;
                    }
                } else if (elytraState == 7) {
                    if (elytraTarget == null || !elytraTarget.method_5805() || elytraTarget.method_31481()) {
                        elytraState = 0;
                        elytraTarget = null;
                    } else if (elytraDelayTimer > 0) {
                        --elytraDelayTimer;
                    } else if (client.field_1724.method_18798().field_1351 <= 0.05) {
                        elytraState = 8;
                    }
                } else if (elytraState == 8) {
                    if (!client.field_1724.method_6118(class_1304.field_6174).method_31574(class_1802.field_8833)) {
                        elytraSlot = MaceSwapClient.findInHotbar(inv, class_1802.field_8833);
                        if (elytraSlot != -1) {
                            inv.field_7545 = elytraSlot;
                            client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                        } else {
                            elytraState = 0;
                        }
                    }
                    if (elytraState != 0) {
                        elytraDelayTimer = 3;
                        elytraState = 9;
                    }
                } else if (elytraState == 9) {
                    if (elytraDelayTimer > 0) {
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2848((class_1297)client.field_1724, class_2848.class_2849.field_12982));
                        }
                        --elytraDelayTimer;
                    } else {
                        elytraState = 10;
                    }
                } else if (elytraState == 10) {
                    fwSlot = MaceSwapClient.findInHotbar(inv, class_1802.field_8639);
                    if (fwSlot != -1) {
                        inv.field_7545 = fwSlot;
                        double dx = elytraTarget.method_23317() - client.field_1724.method_23317();
                        double dy = elytraTarget.method_23320() - client.field_1724.method_23320();
                        double dz = elytraTarget.method_23321() - client.field_1724.method_23321();
                        double diffXZ = Math.sqrt(dx * dx + dz * dz);
                        float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
                        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, diffXZ)));
                        float originalPitch = client.field_1724.method_36455();
                        float originalYaw = client.field_1724.method_36454();
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2828.class_2831(targetYaw, targetPitch, client.field_1724.method_24828()));
                        }
                        client.field_1761.method_2919((class_1657)client.field_1724, class_1268.field_5808);
                        if (client.method_1562() != null) {
                            client.method_1562().method_52787((class_2596)new class_2828.class_2831(originalYaw, originalPitch, client.field_1724.method_24828()));
                        }
                        inv.field_7545 = elytraOriginalSlot;
                        elytraState = 5;
                    } else {
                        inv.field_7545 = elytraOriginalSlot;
                        elytraState = 0;
                        elytraTarget = null;
                    }
                }
            }
            wasOnGround = isOnGround;
            trackedFallDistance = !isOnGround ? client.field_1724.field_6017 : 0.0f;
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
            if (swapStep == 1) {
                if (queuedOriginalSlot != -1) {
                    client.field_1724.method_31548().field_7545 = queuedOriginalSlot;
                }
                swapStep = 0;
                queuedOriginalSlot = -1;
            }
            while (axeToggleKey.method_1436()) {
                if (!config.allowAxeSwapHotkey) continue;
                config.axeSwapEnabled = !config.axeSwapEnabled;
                AutoConfig.getConfigHolder(MaceSwapConfig.class).save();
                if (config.displayMode != MaceSwapConfig.DisplayMode.ACTIONBAR) continue;
                client.field_1724.method_7353((class_2561)class_2561.method_43471((String)(config.axeSwapEnabled ? "message.maceswap.axe_enabled" : "message.maceswap.axe_disabled")), true);
            }
            while (toggleKey.method_1436()) {
                if (TargetAssist.isActive) {
                    TargetAssist.isActive = false;
                    TargetAssist.lockedTarget = null;
                    isAutoMaceEnabled = wasWhiteModeBeforeAim;
                    if (config.displayMode != MaceSwapConfig.DisplayMode.ACTIONBAR) continue;
                    client.field_1724.method_7353((class_2561)class_2561.method_43471((String)"message.maceswap.aim_lost"), true);
                    continue;
                }
                boolean aimActivated = false;
                class_1297 target = MaceSwapClient.getLookingAtEntity(client, config);
                if (target != null) {
                    wasWhiteModeBeforeAim = isAutoMaceEnabled;
                    isAutoMaceEnabled = true;
                    TargetAssist.isActive = true;
                    TargetAssist.lockedTarget = target;
                    aimActivated = true;
                    if (config.displayMode == MaceSwapConfig.DisplayMode.ACTIONBAR) {
                        client.field_1724.method_7353((class_2561)class_2561.method_43469((String)"message.maceswap.aim_locked", (Object[])new Object[]{target.method_5476().getString()}), true);
                    }
                }
                if (aimActivated) continue;
                boolean bl = isAutoMaceEnabled = !isAutoMaceEnabled;
                if (config.displayMode != MaceSwapConfig.DisplayMode.ACTIONBAR) continue;
                client.field_1724.method_7353((class_2561)class_2561.method_43471((String)(isAutoMaceEnabled ? "message.maceswap.enabled" : "message.maceswap.disabled")), true);
            }
            this.wasUsePressed = currentUsePressed;
            this.wasAttackPressed = currentAttackPressed;
        });
    }

    private static void aimAtTarget(class_310 client, class_1297 target) {
        if (target == null || !target.method_5805() || target.method_31481()) {
            return;
        }
        double dx = target.method_23317() - client.field_1724.method_23317();
        double dy = target.method_23320() - client.field_1724.method_23320();
        double dz = target.method_23321() - client.field_1724.method_23321();
        double diffXZ = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, diffXZ)));
        client.field_1724.method_36456(targetYaw);
        client.field_1724.method_36457(targetPitch);
        if (client.method_1562() != null) {
            client.method_1562().method_52787((class_2596)new class_2828.class_2831(targetYaw, targetPitch, client.field_1724.method_24828()));
        }
    }

    public static boolean isWeaponOrTool(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return false;
        }
        class_1792 item = stack.method_7909();
        return item instanceof class_1829 || item instanceof class_1743 || item instanceof class_1810 || item instanceof class_1821 || item instanceof class_1794 || item instanceof class_1835 || item instanceof class_9362 || item instanceof class_1753 || item instanceof class_1764 || item instanceof class_1819;
    }

    public static boolean isMeleeWeaponOrEmptyOrWindCharge(class_1799 stack) {
        if (stack == null || stack.method_7960()) {
            return true;
        }
        class_1792 item = stack.method_7909();
        return item instanceof class_1829 || item instanceof class_1743 || item instanceof class_9362 || item == class_1802.field_49098;
    }

    private static void refillItem(class_310 client, class_1792 item) {
        class_1661 inv = client.field_1724.method_31548();
        if (MaceSwapClient.findInHotbar(inv, item) == -1) {
            for (int i = 9; i < 36; ++i) {
                if (!inv.method_5438(i).method_31574(item)) continue;
                int hotbarSlot = -1;
                for (int j = 0; j < 9; ++j) {
                    if (!inv.method_5438(j).method_7960()) continue;
                    hotbarSlot = j;
                    break;
                }
                if (hotbarSlot == -1) {
                    hotbarSlot = 8;
                }
                client.field_1761.method_2906(0, i, hotbarSlot, class_1713.field_7791, (class_1657)client.field_1724);
                break;
            }
        }
    }

    private static int findInHotbar(class_1661 inv, class_1792 item) {
        for (int i = 0; i < 9; ++i) {
            if (!inv.method_5438(i).method_31574(item)) continue;
            return i;
        }
        return -1;
    }

    private static int findChestplateInHotbar(class_1661 inv) {
        for (int i = 0; i < 9; ++i) {
            class_1792 item = inv.method_5438(i).method_7909();
            if (item != class_1802.field_22028 && item != class_1802.field_8058 && item != class_1802.field_8523 && item != class_1802.field_8678 && item != class_1802.field_8873 && item != class_1802.field_8577) continue;
            return i;
        }
        return -1;
    }

    public static boolean hasLineOfSight(class_310 client, class_1297 target) {
        class_243 end;
        if (client.field_1724 == null || client.field_1687 == null) {
            return false;
        }
        class_243 start = client.field_1724.method_5836(1.0f);
        class_3959 context = new class_3959(start, end = new class_243(target.method_23317(), target.method_23320(), target.method_23321()), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)client.field_1724);
        return client.field_1687.method_17742(context).method_17783() == class_239.class_240.field_1333;
    }

    private static class_1297 getLookingAtEntity(class_310 client, MaceSwapConfig config) {
        if (client.field_1724 == null || client.field_1687 == null) {
            return null;
        }
        double maxDistance = config.aimDistance;
        boolean playerOnly = config.playerOnlyAim;
        boolean smartLock = config.smartLockEnabled;
        class_243 cameraPos = client.field_1724.method_5836(1.0f);
        class_243 rotation = client.field_1724.method_5828(1.0f);
        class_243 endPos = cameraPos.method_1031(rotation.field_1352 * maxDistance, rotation.field_1351 * maxDistance, rotation.field_1350 * maxDistance);
        class_238 box = client.field_1724.method_5829().method_18804(rotation.method_1021(maxDistance)).method_1009(1.0, 1.0, 1.0);
        class_1297 closestEntity = null;
        double bestScore = Double.MAX_VALUE;
        for (class_1297 entity : client.field_1687.method_8333((class_1297)client.field_1724, box, e -> e instanceof class_1309 && e.method_5805() && (!playerOnly || e instanceof class_1657))) {
            double dist;
            class_238 entityBox = entity.method_5829().method_1014(0.1);
            Optional hit = entityBox.method_992(cameraPos, endPos);
            if (smartLock) {
                class_1309 le;
                double score;
                class_243 dir = new class_243(entity.method_23317(), entity.method_23318(), entity.method_23321()).method_1020(cameraPos).method_1029();
                if (!(rotation.method_1026(dir) > 0.8) || !MaceSwapClient.hasLineOfSight(client, entity) || !((score = (double)((le = (class_1309)entity).method_6032() + (float)le.method_6096())) < bestScore)) continue;
                bestScore = score;
                closestEntity = entity;
                continue;
            }
            if (!hit.isPresent() || !MaceSwapClient.hasLineOfSight(client, entity) || !((dist = cameraPos.method_1025((class_243)hit.get())) < bestScore)) continue;
            bestScore = dist;
            closestEntity = entity;
        }
        return closestEntity;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
        if (config.axeSwapEnabled && player.method_6047().method_7909() instanceof class_1743) {
            boolean isBlocking = false;
            if (target instanceof class_1309) {
                class_1309 livingTarget = (class_1309)target;
                isBlocking = livingTarget.method_6039();
            }
            if (isBlocking) {
                if (axeSwapState == 0) {
                    queuedAxeTarget = target;
                    axeSwapState = 1;
                }
                return false;
            }
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
                class_1309 livingTarget;
                queuedOriginalSlot = originalSlot;
                swapStep = 1;
                inv.field_7545 = maceSlot;
                boolean bypassed = false;
                class_243 originalPos = new class_243(player.method_23317(), player.method_23318(), player.method_23321());
                if (config.bypassShieldEnabled && target instanceof class_1309 && (livingTarget = (class_1309)target).method_6039()) {
                    class_243 targetLook = target.method_5828(1.0f);
                    class_243 behindPos = new class_243(target.method_23317(), target.method_23318(), target.method_23321()).method_1020(targetLook.method_1021(1.5));
                    client.method_1562().method_52787((class_2596)new class_2828.class_2829(behindPos.field_1352, behindPos.field_1351, behindPos.field_1350, player.method_24828()));
                    bypassed = true;
                }
                isPerformingInternalAttack = true;
                try {
                    client.field_1761.method_2918(player, target);
                }
                finally {
                    isPerformingInternalAttack = false;
                }
                player.method_6104(class_1268.field_5808);
                if (bypassed) {
                    client.method_1562().method_52787((class_2596)new class_2828.class_2829(originalPos.field_1352, originalPos.field_1351, originalPos.field_1350, player.method_24828()));
                }
                return true;
            }
        }
        return false;
    }

    static {
        isAutoMaceEnabled = false;
        wasWhiteModeBeforeAim = false;
        isPerformingInternalAttack = false;
        swapStep = 0;
        queuedOriginalSlot = -1;
        axeSwapState = 0;
        axeOriginalSlot = -1;
        axeMaceSlot = -1;
        queuedAxeTarget = null;
        wasOnGround = true;
        trackedFallDistance = 0.0f;
        elytraState = 0;
        elytraOriginalSlot = -1;
        elytraDelayTimer = 0;
        elytraTarget = null;
        tridentState = 0;
        tridentDelayTimer = 0;
    }
}

