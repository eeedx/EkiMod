package chaos.mod.creativetabs;

import chaos.mod.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Eki_misc extends CreativeTabs{
	public Eki_misc(String label) {
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemInit.WRENCH);
	}
}
