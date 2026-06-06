/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 */
package com.Cna4ik.maceswap.client;

import com.Cna4ik.maceswap.client.MaceSwapClient;
import com.Cna4ik.maceswap.config.MaceSwapConfig;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class TargetAssist {
    public static boolean isActive = false;
    public static class_1297 lockedTarget = null;

    public static void tick(class_310 client, MaceSwapConfig config) {
        if (!isActive || lockedTarget == null || client.field_1724 == null) {
            return;
        }
        if (config.playerOnlyAim && !(lockedTarget instanceof class_1657)) {
            isActive = false;
            lockedTarget = null;
            return;
        }
        if (!lockedTarget.method_5805() || lockedTarget.method_31481() || client.field_1724.method_5739(lockedTarget) > (float)config.aimDistance || !MaceSwapClient.hasLineOfSight(client, lockedTarget)) {
            return;
        }
        double dx = lockedTarget.method_23317() - client.field_1724.method_23317();
        double dy = lockedTarget.method_23320() - client.field_1724.method_23320();
        double dz = lockedTarget.method_23321() - client.field_1724.method_23321();
        double diffXZ = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0f;
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, diffXZ)));
        float currentYaw = client.field_1724.method_36454();
        float currentPitch = client.field_1724.method_36455();
        float yawDiff = class_3532.method_15393((float)(targetYaw - currentYaw));
        float pitchDiff = class_3532.method_15393((float)(targetPitch - currentPitch));
        float maxStep = (float)config.aimSpeed / 20.0f;
        float yawStep = class_3532.method_15363((float)(yawDiff * 0.1f), (float)(-maxStep), (float)maxStep);
        float pitchStep = class_3532.method_15363((float)(pitchDiff * 0.1f), (float)(-maxStep), (float)maxStep);
        client.field_1724.method_36456(currentYaw + yawStep);
        client.field_1724.method_36457(currentPitch + pitchStep);
    }
}

