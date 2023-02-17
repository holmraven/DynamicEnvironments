package com.holmraven.dynamicenvironments.mixins;

import com.holmraven.dynamicenvironments.handler.MorningFogHandler;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.MathHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Unique
    private static float viewDistance;

    @Inject(method = "applyFog", at = @At("HEAD"))
    private static void getViewDistance(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        BackgroundRendererMixin.viewDistance = viewDistance;
    }

    @Redirect(method = "applyFog", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BackgroundRenderer$FogData;fogStart:F", opcode = Opcodes.PUTFIELD), slice = @Slice(from = @At(value = "CONSTANT", args = "floatValue=64.0F")))
    private static void handleMorningFog(BackgroundRenderer.FogData fogData, float value) {
            float f = MathHelper.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
            if(MorningFogHandler.isMorning()) {
                fogData.fogStart = MorningFogHandler.fogValue();
            } else {
                fogData.fogStart = viewDistance - f;
            }
    }
}
