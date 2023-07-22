package holmraven.dynamicenvironments;

import holmraven.dynamicenvironments.particle.FootprintParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DynamicEnvironmentsClient implements ClientModInitializer {
	public static DefaultParticleType FOOTPRINT = FabricParticleTypes.simple();
	@Override
	public void onInitializeClient() {
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(DynamicEnvironments.MODID, "footprint"), FOOTPRINT);
		ParticleFactoryRegistry.getInstance().register(DynamicEnvironmentsClient.FOOTPRINT, FootprintParticle.Factory::new);
	}
}
