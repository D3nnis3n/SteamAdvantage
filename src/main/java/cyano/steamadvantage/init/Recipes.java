package cyano.steamadvantage.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import cyano.poweradvantage.PowerAdvantage;
import cyano.poweradvantage.RecipeMode;

public class Recipes {

	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		Blocks.init();
		Items.init();
		
		
		
		RecipeMode recipeMode = PowerAdvantage.recipeMode;
		OreDictionary.registerOre("stick", net.minecraft.init.Items.stick);
		
		if(recipeMode == RecipeMode.TECH_PROGRESSION){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.steam_governor,1)," t ","srs","btb",'t',"sprocket",'s',"barsSteel",'r',"barsBrass",'b',"ingotBrass"));
		} else if(recipeMode == RecipeMode.APOCALYPTIC){
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.steam_governor,1)," t ","srs","btb",'t',"sprocket",'s',"stick",'r',"barsBrass",'b',"ingotBrass"));
			CrusherRecipeRegistry.addNewCrusherRecipe(Items.steam_governor, new ItemStack(cyano.poweradvantage.init.Items.sprocket,2));

			CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.steam_crusher, new ItemStack(Items.steam_governor,2));
			CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.steam_furnace, new ItemStack(Items.steam_governor,2));
			CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.steam_boiler_coal, new ItemStack(Items.steam_governor,2));
			CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.steam_drill, new ItemStack(Items.steam_governor,2));
			CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.steam_tank, new ItemStack(Items.steam_governor,1));
		} else {
			// normal
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.steam_governor,1)," t ","srs","btb",'t',"nuggetIron",'s',"stick",'r',"stick",'b',"ingotBrass"));
			GameRegistry.addRecipe(steamMachineRecipe(Blocks.steam_drill,net.minecraft.init.Items.diamond_pickaxe));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(Items.steam_drill_bit," g "," i ","did",'g',"sprocket",'i',"ingotSteel",'d',"gemDiamond"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.steam_pipe,6),"xxx","   ","xxx",'x',"ingotBrass"));
		GameRegistry.addRecipe(steamMachineRecipe(Blocks.steam_crusher,net.minecraft.init.Blocks.piston,"blockSteel"));
		GameRegistry.addRecipe(steamMachineRecipe(Blocks.steam_furnace,net.minecraft.init.Blocks.furnace));
		GameRegistry.addRecipe(steamMachineRecipe(Blocks.steam_boiler_coal,"conduitSteam"));
		GameRegistry.addRecipe(steamMachineRecipe(Blocks.steam_drill,Items.steam_drill_bit));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.steam_tank,"xgx","xpx","xxx",'x',"plateCopper",'p',"conduitSteam",'g',"governor"));
		
		
		initDone = true;
	}

	private static ShapedOreRecipe steamMachineRecipe(Block output, Object item){
		return new ShapedOreRecipe(output, "gXg","pmp",'X',item,'g',"governor",'p',"plateIron",'m',"frameSteel");
	}

	private static ShapedOreRecipe steamMachineRecipe(Block output, Object item1, Object item2){
		return new ShapedOreRecipe(output, " Y ","gXg","pmp",'X',item1,'Y',item2,'g',"governor",'p',"plateIron",'m',"frameSteel");
	}
}
