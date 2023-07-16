package holmraven.dynamicenvironments.handler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

public class MorningFogHandler {
    public static final float fogBeforeMorningBegin = 3500;
    public static final float fogMorningBegin = 5000;
    public static final float fogMorningEnd = 7500;

    public static int getTime() {
        assert MinecraftClient.getInstance().player != null;
        return ((int) (MinecraftClient.getInstance().player.getWorld().getTimeOfDay() + 6000) % 24000);
    }

    public static boolean isUnderground() {
        assert MinecraftClient.getInstance().world != null;
        return MinecraftClient.getInstance().world.getLightLevel(LightType.SKY, MinecraftClient.getInstance().gameRenderer.getCamera().getBlockPos()) < 4.0F;
    }

    public static float fogValue(float viewDistance) {
        float f = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
        if (!isUnderground()) {
            if (getTime() >= fogMorningBegin && getTime() <= fogMorningEnd) {
                return (viewDistance - f) * (1 - ((fogMorningEnd - fogMorningBegin) - (getTime() - fogMorningBegin)) / (fogMorningEnd - fogMorningBegin));
            } else if (getTime() >= fogBeforeMorningBegin && getTime() < fogMorningBegin) {
                return (viewDistance - f) * ((fogMorningBegin - fogBeforeMorningBegin) - (getTime() - fogBeforeMorningBegin)) / (fogMorningBegin - fogBeforeMorningBegin);
            } else {
                return viewDistance - f;
            }
        }
        return viewDistance - f;
    }


}
