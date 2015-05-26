package cyano.steamadvantage.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import cyano.steamadvantage.init.Blocks;

public class DrillBitTileEntity extends TileEntity implements IUpdatePlayerListBox{

	public final static float ROTATION_PER_TICK = 360f / 20f; // in degrees, 20 ticks per revolution
	public float rotation = 0;
	private EnumFacing direction = EnumFacing.DOWN;
	
	public DrillBitTileEntity(){
		super();
	}
	
	@Override
	public void update(){
		if(getWorld().isRemote){
			rotation = ROTATION_PER_TICK * (getWorld().getTotalWorldTime() % 20);
		}
	}
	public void setDirection(EnumFacing dir){
		this.direction = dir;
	}
	
	public EnumFacing.Axis getDirection(){
		return direction.getAxis();
	}
	
	public static void createDrillBitBlock(World w, BlockPos coord, EnumFacing dir){
		w.setBlockState(coord, cyano.steamadvantage.init.Blocks.drillbit.getDefaultState());
		DrillBitTileEntity te = new DrillBitTileEntity();
		te.direction = dir;
		w.setTileEntity(coord, te);
		FMLLog.info("Created drill bit at "+coord+" facing "+dir);// TODO: remove debug code
	}
	
	/**
	 * Destroys all drillbits connected to this one
	 */
	public void destroyLine(){
		this.destroy(this.direction);
		this.destroy(this.direction.getOpposite());
		FMLLog.info("destroy line");// TODO: remove debug code
		getWorld().setBlockToAir(getPos()); // redundant because this is being called on block destruction
	}
	/**
	 * destroys upstream drillbit
	 */
	private void destroy(EnumFacing f){
		FMLLog.info("destroy "+f);// TODO: remove debug code
		BlockPos coord = this.getPos().offset(f);
		while(getWorld().getBlockState(coord).getBlock() == Blocks.drillbit){
			getWorld().setBlockToAir(coord);
			coord = coord.offset(f);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound root){
		super.writeToNBT(root);
		root.setByte("dir", (byte)direction.getIndex());
	}
	

	@Override
	public void readFromNBT(NBTTagCompound root){
		super.readFromNBT(root);
		if(root.hasKey("dir")){
			this.direction = EnumFacing.getFront(root.getByte("dir"));
		}
	}
}
