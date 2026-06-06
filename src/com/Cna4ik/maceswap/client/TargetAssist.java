/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_310
 */
package com.Cna4ik.maceswap.client;

import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_310;

public class TargetAssist {
    public static boolean isActive = false;
    public static class_1297 lockedTarget = null;

    public static void tick(class_310 client, Object config) {
        // Aim assist completely removed - mod no longer tracks targets
        isActive = false;
        lockedTarget = null;
    }
}
