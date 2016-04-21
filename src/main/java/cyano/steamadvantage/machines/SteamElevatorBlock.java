package cyano.steamadvantage.machines;

import cyano.poweradvantage.api.ConduitType;
import cyano.poweradvantage.api.PoweredEntity;
import cyano.steamadvantage.init.Power;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * @author DrCyano
 *
 */
public class SteamElevatorBlock extends cyano.poweradvantage.api.simple.BlockSimplePowerMachine {

	
	public SteamElevatorBlock() {
		super(Material.PISTON, 0.75f, Power.steam_power);
	}

	@Override
	public PoweredEntity createNewTileEntity(World world, int metaDataValue) {
		return new SteamElevatorTileEntity();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState bs) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState bs, World world, BlockPos coord) {
		if(world.getTileEntity(coord) instanceof SteamElevatorTileEntity){
			return ((SteamElevatorTileEntity)world.getTileEntity(coord)).getComparatorOutput();
		}
		return 0;
	}

	@Override
	public boolean isPowerSink(ConduitType conduitType) {
		return true;
	}

	@Override
	public boolean isPowerSource(ConduitType conduitType) {
		return false;
	}

}
