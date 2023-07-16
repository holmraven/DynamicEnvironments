package holmraven.dynamicenvironments.handler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class MorningFogHandler {
    public static final float fogBeforeMorningBegin = 3500;
    public static final float fogMorningBegin = 5000;
    public static final float fogMorningEnd = 7500;

    public static int getTime() {
        MinecraftClient minecraft = MinecraftClient.getInstance();

        assert minecraft.player != null;

        return ((int) (minecraft.player.getWorld().getTimeOfDay() + 6000) % 24000);
    }

    public static float fogValue(float viewDistance) {
        float f = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
        if(getTime() >= fogMorningBegin && getTime() <= fogMorningEnd) {
            return (viewDistance - f) * (1 - ((fogMorningEnd - fogMorningBegin) - (getTime() - fogMorningBegin)) / (fogMorningEnd - fogMorningBegin));
        } else if (getTime() >= fogBeforeMorningBegin && getTime() < fogMorningBegin) {
            return (viewDistance - f) * ((fogMorningBegin - fogBeforeMorningBegin) - (getTime() - fogBeforeMorningBegin)) / (fogMorningBegin - fogBeforeMorningBegin);
        } else {
            return viewDistance - f;
        }
    }
}
