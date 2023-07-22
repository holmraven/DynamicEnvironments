package holmraven.dynamicenvironments;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicEnvironments implements ModInitializer {
	public static final String MODID = "dynamic-environments";
    public static final Logger LOGGER = LoggerFactory.getLogger(DynamicEnvironments.MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}