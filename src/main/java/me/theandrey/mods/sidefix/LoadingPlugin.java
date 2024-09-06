package me.theandrey.mods.sidefix;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(900) // Obf
public class LoadingPlugin implements IFMLLoadingPlugin {

	static final Logger LOGGER = LogManager.getLogger(LoadingPlugin.class);
	public static final Side[] VALID_SIDES = {Side.CLIENT, Side.SERVER};

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"me.theandrey.mods.sidefix.ClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> map) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
