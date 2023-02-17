package com.holmraven.dynamicenvironments.handler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class MorningFogHandler {
    public static float viewDistance;

    public static int getTime() {
        MinecraftClient minecraft = MinecraftClient.getInstance();

        assert minecraft.player != null;

        return ((int) (minecraft.player.world.getTimeOfDay() + 6000) % 24000);
    }

    public static float fogValue() {
        float f = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
        if(getTime() >= 5000 && getTime() <= 7500) {
            return (viewDistance - f) * (1 - (2500f - (getTime() - 5000f)) / 2500f);
        } else if (getTime() >= 3500 && getTime() < 5000) {
            return (viewDistance - f) * (1500f - (getTime() - 3500f)) / 1500f;
        } else {
            return viewDistance - f;
        }
    }
}
