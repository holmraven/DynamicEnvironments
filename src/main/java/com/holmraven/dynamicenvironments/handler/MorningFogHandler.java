package com.holmraven.dynamicenvironments.handler;

import net.minecraft.client.MinecraftClient;

public class MorningFogHandler {
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
        return 0;
    }
}
