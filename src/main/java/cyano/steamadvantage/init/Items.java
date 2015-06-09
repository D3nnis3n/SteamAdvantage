package cyano.steamadvantage.init;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import cyano.steamadvantage.SteamAdvantage;
import cyano.steamadvantage.items.MusketItem;

public abstract class Items {

	
	public static final Map<String,Item> allItems = new HashMap<>();

	public static Item steam_governor;
	public static Item steam_drill_bit;
	public static Item blackpowder_cartridge;
	public static Item blackpowder_musket;
	
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		Blocks.init();

		steam_governor = addItem("steam_governor",new Item(),"governor","governorBrass");
		steam_drill_bit = addItem("steam_drill_bit",new Item());
		blackpowder_cartridge = addItem("blackpowder_cartridge",new Item(),"ammoBlackpowder");
		blackpowder_musket = addItem("musket",new MusketItem(),"gun");
		
		initDone = true;
	}

	private static Item addItem(String unlocalizedName, Item i,String... oreDictNames){
		Item n = addItem(unlocalizedName,i);
		for(String oreDictName : oreDictNames){
			OreDictionary.registerOre(oreDictName, n);
		}
		return n;
	}
	private static Item addItem(String unlocalizedName, Item i){
		i.setUnlocalizedName(SteamAdvantage.MODID+"."+unlocalizedName);
		GameRegistry.registerItem(i, unlocalizedName);
		i.setCreativeTab(cyano.poweradvantage.init.ItemGroups.tab_powerAdvantage);
		allItems.put(unlocalizedName, i);
		return i;
	}
	
	public static Item getItemByName(String unlocalizedName){
		return allItems.get(unlocalizedName);
	}
	
	public static Set<Map.Entry<String, Item>> getAllRegisteredItems(){
		return allItems.entrySet();
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event) {
		for(Map.Entry<String, Item> e :  getAllRegisteredItems()){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(e.getValue(), 0, 
					new ModelResourceLocation(SteamAdvantage.MODID+":"+e.getKey(), "inventory"));
		}
	}
	
}
