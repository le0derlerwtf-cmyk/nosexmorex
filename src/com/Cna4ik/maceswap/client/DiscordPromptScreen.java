/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.shedaniel.autoconfig.AutoConfig
 *  net.minecraft.class_156
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_364
 *  net.minecraft.class_4185
 *  net.minecraft.class_437
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package com.Cna4ik.maceswap.client;

import com.Cna4ik.maceswap.config.MaceSwapConfig;
import java.net.URI;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.class_156;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_364;
import net.minecraft.class_4185;
import net.minecraft.class_437;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

public class DiscordPromptScreen
extends class_437 {
    private final class_437 parent;

    public DiscordPromptScreen(class_437 parent) {
        super((class_2561)class_2561.method_43471((String)"maceswap.discord.title"));
        this.parent = parent;
    }

    protected void method_25426() {
        int buttonWidth = 260;
        int buttonHeight = 20;
        int x = (this.field_22789 - buttonWidth) / 2;
        int y = this.field_22790 - 60;
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"maceswap.discord.ok"), button -> {
            try {
                class_156.method_668().method_673(URI.create("https://modrinth.com/mod/auto-mace-swap"));
            }
            catch (Exception exception) {
                // empty catch block
            }
            MaceSwapConfig config = (MaceSwapConfig)AutoConfig.getConfigHolder(MaceSwapConfig.class).getConfig();
            config.hasShownDiscordPrompt = true;
            AutoConfig.getConfigHolder(MaceSwapConfig.class).save();
            this.field_22787.method_1507(this.parent);
        }).method_46434(x, y, buttonWidth, buttonHeight).method_46431());
        this.method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43471((String)"maceswap.discord.cancel"), button -> this.field_22787.method_1507(this.parent)).method_46434(x, y + 25, buttonWidth, buttonHeight).method_46431());
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        this.method_25420(context, mouseX, mouseY, delta);
        super.method_25394(context, mouseX, mouseY, delta);
        int titleWidth = this.field_22793.method_27525((class_5348)this.field_22785);
        int titleX = (this.field_22789 - titleWidth) / 2;
        int titleY = this.field_22790 - 105;
        context.method_27535(this.field_22793, this.field_22785, titleX, titleY, -1);
        class_5250 msgText = class_2561.method_43471((String)"maceswap.discord.message");
        int msgWidth = this.field_22793.method_27525((class_5348)msgText);
        int msgX = (this.field_22789 - msgWidth) / 2;
        int msgY = this.field_22790 - 85;
        context.method_27535(this.field_22793, (class_2561)msgText, msgX, msgY, -5592406);
    }
}

