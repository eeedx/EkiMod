package chaos.mod.objects.block;

import java.util.List;
import java.util.Random;

import chaos.mod.init.BlockInit;
import chaos.mod.objects.block.base.BlockHasFace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlatformEdge extends BlockHasFace {
	public static final AxisAlignedBB PLATFORM_EDGE_TOP_AABB = new AxisAlignedBB(0, 0.84375D, 0, 1, 1, 1);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private final boolean isOn;
	private final boolean lightable;
	
	public BlockPlatformEdge(String name, Material material, CreativeTabs tab, boolean isOn, boolean lightable) {
		super(name, material, tab, false);
		this.isOn = isOn;
		this.lightable = lightable;
		
		if(this.isOn) {
			setLightLevel(8/15F);
			setCreativeTab(null);
		}
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.SOLID;
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		switch (state.getValue(FACING)) {
		case NORTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0.5D, 1, 0.84375D, 1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, PLATFORM_EDGE_TOP_AABB);
			break;
		case SOUTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 1, 0.84375D, 0.5D));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, PLATFORM_EDGE_TOP_AABB);
			break;
		case EAST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0, 0.5D, 0.84375D, 1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, PLATFORM_EDGE_TOP_AABB);
			break;
		case WEST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.5D, 0, 0, 1, 0.84375D, 1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, PLATFORM_EDGE_TOP_AABB);
		default:
			break;
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote && lightable) {
			if (this.isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.scheduleBlockUpdate(pos, this, 0, 1);
			} else if (!this.isOn && worldIn.isBlockPowered(pos)) {
				IBlockState ON = BlockInit.PLATFORM_EDGE_WITH_LINE_AND_LIGHT_ON.getDefaultState();
				EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
				switch (state.getValue(FACING)) {
				case NORTH:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case EAST:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case SOUTH:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case WEST:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote && lightable) {
			if (this.isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.scheduleBlockUpdate(pos, this, 0, 1);
			} else if (!this.isOn && worldIn.isBlockPowered(pos)) {
				IBlockState ON = BlockInit.PLATFORM_EDGE_WITH_LINE_AND_LIGHT_ON.getDefaultState();
				EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
				switch (state.getValue(FACING)) {
				case NORTH:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case EAST:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case SOUTH:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				case WEST:
					worldIn.setBlockState(pos, ON.withProperty(FACING, enumfacing));
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && lightable) {
			if (this.isOn && !worldIn.isBlockPowered(pos)) {
				IBlockState OFF = BlockInit.PLATFORM_EDGE_WITH_LINE_AND_LIGHT_OFF.getDefaultState();
				EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
				switch (state.getValue(FACING)) {
				case NORTH:
					worldIn.setBlockState(pos, OFF.withProperty(FACING, enumfacing));
					break;
				case EAST:
					worldIn.setBlockState(pos, OFF.withProperty(FACING, enumfacing));
					break;
				case SOUTH:
					worldIn.setBlockState(pos, OFF.withProperty(FACING, enumfacing));
					break;
				case WEST:
					worldIn.setBlockState(pos, OFF.withProperty(FACING, enumfacing));
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return isOn ? new ItemStack(BlockInit.PLATFORM_EDGE_WITH_LINE_AND_LIGHT_OFF) : new ItemStack(state.getBlock());
	}
}
