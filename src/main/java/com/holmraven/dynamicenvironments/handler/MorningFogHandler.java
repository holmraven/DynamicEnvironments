package com.holmraven.dynamicenvironments.handler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class MorningFogHandler {
    public static float viewDistance;

    public static int getHour() {
        MinecraftClient minecraft = MinecraftClient.getInstance();

        assert minecraft.player != null;
        int time = ((int) (minecraft.player.world.getTimeOfDay() + 6000) % 24000);

        return time / 1000;
    }

    public static boolean isMorning() {
        return getHour() >= 4 && getHour() <= 6;
    }

    public static float fogValue() {
        float f = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
        if(MorningFogHandler.isMorning()) {
            return 0;
        } else {
            return viewDistance - f;
        }
    }
}
