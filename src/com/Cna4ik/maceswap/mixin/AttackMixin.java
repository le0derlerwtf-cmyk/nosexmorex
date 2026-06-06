/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_636
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.Cna4ik.maceswap.mixin;

import com.Cna4ik.maceswap.client.MaceSwapClient;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_636.class})
public class AttackMixin {
    @Inject(method={"method_2918"}, at={@At(value="HEAD")}, cancellable=true)
    public void onAttackEntity(class_1657 player, class_1297 target, CallbackInfo ci) {
        if (MaceSwapClient.handleGhostSwapAttack(player, target)) {
            ci.cancel();
        }
    }
}

