package holmraven.dynamicenvironments.utils;

import net.minecraft.client.MinecraftClient;

public class ConditionUtil {
    public static boolean isUnderground() {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().player != null) {
            return !MinecraftClient.getInstance().player.clientWorld.isSkyVisible(MinecraftClient.getInstance().player.getBlockPos()) && MinecraftClient.getInstance().player.getBlockPos().getY() < MinecraftClient.getInstance().world.getSeaLevel();
        }
        return false;
    }
}
