package cyano.steamadvantage;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/* TODO list
 * - Coal-Fired Steam Boiler
 * - Boiler Tank
 * - Steam Conduit
 * - Steam Powered Rock Crusher
 * - Steam Powered Blast Furnace (expanded furnace 2x2)
 * --- push version 0.1 ---
 * - Steam Powered Drill
 * --- push version 1.0 ---
 * - pressure cooker (fuel-less furnace that only cooks food)
 * - Musket (slow-loading ranged weapon)
 * - Bullets (smelt lead nuggets into grape-shot, loading a gun consumes 1 grape-shot and 1 gunpowder)
 * - Steam Powered Lift (pushes up special lift blocks, like an extendable piston)
 * - Steam Powered Machine Shop (automatable crafter)
 * - Steam Powered Defense Cannon (manual aiming and requires redstone trigger)
 * --- push version 1.1 ---
 * - Oil-Burning Steam Boiler
 * - Bioreactor (slowly makes liquid fuel from organic matter)
 */
@Mod(modid = SteamAdvantage.MODID, version = SteamAdvantage.VERSION, name=SteamAdvantage.NAME, 
		dependencies = "required-after:poweradvantage;required-after:basemetals")
public class SteamAdvantage
{/** The identifier for this mod */
    public static final String MODID = "steamadvantage";
    /** The display name for this mod */
    public static final String NAME = "Steam Advantage";
    /** The version of this mod, in the format major.minor.update */
    public static final String VERSION = "0.0.1";
    
    /**
     * Pre-initialization step. Used for initializing objects and reading the 
     * config file
     * @param event FML event object
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	// TODO: make init classes
    	// TODO: make utility class for machine GUIs (to draw needles)
    	// TODO: make block and tile entity classes
    	// TODO: flames on furnaces for random tick updates
    	// TODO: language translations
    	
    	
    	config.save();
		    	
    	if(event.getSide() == Side.CLIENT){
			clientPreInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverPreInit(event);
		}
	}

	@SideOnly(Side.CLIENT)
	private void clientPreInit(FMLPreInitializationEvent event){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPreInit(FMLPreInitializationEvent event){
		// client-only code
	}
	/**
     * Initialization step. Used for adding renderers and most content to the 
     * game
     * @param event FML event object
     */
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	

    	if(event.getSide() == Side.CLIENT){
			clientInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverInit(event);
		}
	}
	

	@SideOnly(Side.CLIENT)
	private void clientInit(FMLInitializationEvent event){
		// client-only code
		// TODO: create renders
	}
	@SideOnly(Side.SERVER)
	private void serverInit(FMLInitializationEvent event){
		// client-only code
	}
	
	/**
     * Post-initialization step. Used for cross-mod options
     * @param event FML event object
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
	

	@SideOnly(Side.CLIENT)
	private void clientPostInit(FMLPostInitializationEvent event){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPostInit(FMLPostInitializationEvent event){
		// client-only code
	}

}
