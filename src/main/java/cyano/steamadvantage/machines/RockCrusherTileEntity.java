package cyano.steamadvantage.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import cyano.basemetals.registry.recipe.ICrusherRecipe;
import cyano.steamadvantage.init.Power;

public class RockCrusherTileEntity extends cyano.poweradvantage.api.simple.TileEntitySimplePowerConsumer{

	public static final float STEAM_PER_PROGRESS_TICK = 0.5f;
	public static final int TICKS_PER_ACTION = 400;

	private final ItemStack[] inventory = new ItemStack[6]; // slot 0 is input, other slots are output
	private final int[] dataSyncArray = new int[2];
	
	
	private int progress = 0;
	
	public RockCrusherTileEntity() {
		super(Power.steam_power, 200, RockCrusherTileEntity.class.getName());
	}

	private boolean redstone = true;
	

	private int timeSinceLastSteamBurst = 0;
	
	private ItemStack itemCheck = null;
	@Override
	public void tickUpdate(boolean isServerWorld) {
		if(isServerWorld){
			// reset progress when item in slot changes
			ItemStack input = inventory[0];
			if(itemCheck != null && ItemStack.areItemsEqual(itemCheck, input) == false){
				progress = 0;
			}
			itemCheck  = input;
			
			// disabled by redstone
			if(redstone){
				if (progress > 0){
					progress--;
				}
			} else {
				ICrusherRecipe recipe = getCrusherRecipe(inventory[0]);
				// reset progress if item is not crushable
				if(progress > 0 && recipe == null){
					progress = 0;
				}
				
				if(recipe != null){
					ItemStack output = recipe.getOutput(); // implementations use ItemStack.copy() to give us a fresh ItemStack
					// check if there's a place to put the output item
					int availableSlot = canAddToOutputInventory(output); // returns -1 if no slot is available
					if(availableSlot >= 0){
						// crusher progress
						if(this.getEnergy() >= STEAM_PER_PROGRESS_TICK){
							progress++;
							this.subtractEnergy(STEAM_PER_PROGRESS_TICK, getType());
							// play steam sounds occasionally
							timeSinceLastSteamBurst++;
							if(timeSinceLastSteamBurst > 50){
								getWorld().playSoundEffect(getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5, "random.fizz", 0.5f, 1f);
								timeSinceLastSteamBurst = 0;
							}
						} else if (progress > 0){
							// cannot crush, undo progress
							progress--;
						}
						if(progress >= TICKS_PER_ACTION){
							// add product to output
							if(inventory[availableSlot] == null){
								inventory[availableSlot] = output;
							} else {
								inventory[availableSlot].stackSize += output.stackSize;
							}
							progress = 0;
							getWorld().playSound(getPos().getX()+0.5, getPos().getY()+0.5, getPos().getZ()+0.5, "dig.gravel", 0.5f, 1f, true);
						}
					} else if (progress > 0){
						// cannot crush, undo progress
						progress--;
					}
				}
			}
			energyDecay();
		}
	}
	// TODO: item handler input (to input slot) and output (from output slots)


	private float oldSteam = 0;
	private int oldProgress = 0;
	
	@Override
	public void powerUpdate(){
		super.powerUpdate();
		
		redstone = hasRedstoneSignal();
		
		if(oldSteam != this.getEnergy() || progress != oldProgress){
			this.sync();
			oldSteam = this.getEnergy();
			oldProgress = progress;
		}
	}

	

	private void energyDecay() {
		if(getEnergy() > 0){
			subtractEnergy(Power.ENERGY_LOST_PER_TICK,Power.steam_power);
		}
	}
	
	private ICrusherRecipe getCrusherRecipe(ItemStack item) {
		if(item == null) return null;
		return CrusherRecipeRegistry.getInstance().getRecipeForInputItem(item);
	}

	private boolean hasRedstoneSignal() {
		for(int i = 0; i < EnumFacing.values().length; i++){
			if(getWorld().getRedstonePower(getPos(), EnumFacing.values()[i]) > 0) return true;
		}
		return false;
	}
	
	
	public float getProgressLevel(){
		return (float)progress / (float)TICKS_PER_ACTION;
	}
	
	public float getSteamLevel(){
		return this.getEnergy() / this.getEnergyCapacity();
	}
	
	@Override
	protected ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public int[] getDataFieldArray() {
		return dataSyncArray;
	}

	@Override
	public void prepareDataFieldsForSync() {
		dataSyncArray[0] = Float.floatToRawIntBits(this.getEnergy());
		dataSyncArray[1] = this.progress;
	}

	@Override
	public void onDataFieldUpdate() {
		this.setEnergy(Float.intBitsToFloat(dataSyncArray[0]), this.getType());
		this.progress = dataSyncArray[1];
	}

	

	private int canAddToOutputInventory(ItemStack output) {
		for(int i = 1; i < inventory.length; i++){
			if(inventory[i] == null) {return i;}
			if(ItemStack.areItemsEqual(output, inventory[i]) && ItemStack.areItemStackTagsEqual(output, inventory[i])
					&& (inventory[i].stackSize + output.stackSize) <= inventory[i].getMaxStackSize()){
				return i;
			}
		}
		return -1;
	}

}
