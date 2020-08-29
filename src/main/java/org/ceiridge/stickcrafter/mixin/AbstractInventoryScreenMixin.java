package org.ceiridge.stickcrafter.mixin;

import org.ceiridge.stickcrafter.StickItemForCalc;
import org.ceiridge.stickcrafter.util.TimeStopper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
	private static final StickItemForCalc[] calcItems = {new StickItemForCalc(Item.fromBlock(Blocks.OAK_LOG), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.ACACIA_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.BIRCH_LOG), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.DARK_OAK_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.JUNGLE_LOG), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.SPRUCE_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.CRIMSON_STEM), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.WARPED_STEM), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_ACACIA_LOG), 8),

			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_BIRCH_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_CRIMSON_STEM), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_DARK_OAK_LOG), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_JUNGLE_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_OAK_LOG), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_SPRUCE_LOG), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_WARPED_STEM), 8),

			new StickItemForCalc(Item.fromBlock(Blocks.ACACIA_WOOD), 8), new StickItemForCalc(Item.fromBlock(Blocks.BIRCH_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.DARK_OAK_WOOD), 8), new StickItemForCalc(Item.fromBlock(Blocks.JUNGLE_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.OAK_WOOD), 8), new StickItemForCalc(Item.fromBlock(Blocks.SPRUCE_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.CRIMSON_HYPHAE), 8), new StickItemForCalc(Item.fromBlock(Blocks.WARPED_HYPHAE), 8),

			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_ACACIA_WOOD), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_BIRCH_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_CRIMSON_HYPHAE), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_DARK_OAK_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_JUNGLE_WOOD), 8), new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_OAK_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_SPRUCE_WOOD), 8),
			new StickItemForCalc(Item.fromBlock(Blocks.STRIPPED_WARPED_HYPHAE), 8), new StickItemForCalc(Item.fromBlock(Blocks.ACACIA_PLANKS), 2),

			new StickItemForCalc(Item.fromBlock(Blocks.BIRCH_PLANKS), 2), new StickItemForCalc(Item.fromBlock(Blocks.CRIMSON_PLANKS), 2),
			new StickItemForCalc(Item.fromBlock(Blocks.DARK_OAK_PLANKS), 2), new StickItemForCalc(Item.fromBlock(Blocks.JUNGLE_PLANKS), 2),
			new StickItemForCalc(Item.fromBlock(Blocks.OAK_PLANKS), 2), new StickItemForCalc(Item.fromBlock(Blocks.SPRUCE_PLANKS), 2),
			new StickItemForCalc(Item.fromBlock(Blocks.WARPED_PLANKS), 2), new StickItemForCalc(Items.STICK, 1)};
	
	private int possibleSticks;
	private TimeStopper calcDelay = new TimeStopper(1000); // 1s delay

	public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(at = @At("RETURN"), method = "render") //"render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
	private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if (calcDelay.hasReached()) { // only calculate stick amount after delay
			calcDelay.reset();
			this.possibleSticks = 0;

			for (ItemStack item : this.client.player.inventory.main) {
				if (item == null) {
					continue;
				}
				Item type = item.getItem();

				for (StickItemForCalc sifc : calcItems) {
					if (sifc.type.equals(type)) {
						this.possibleSticks += item.getCount() * sifc.multiplier;
						break;
					}
				}
			}
		}

		this.textRenderer.drawWithShadow(matrices,
				"Total possible sticks: " + possibleSticks + " (" + (int) Math.ceil((double) possibleSticks / 64d) + " slots)", 2, 2, 0xFFFFFFFF);
		this.textRenderer.drawWithShadow(matrices, "Total possible stick emeralds: " + (int) Math.floor((double) possibleSticks / 32d), 2,
				this.textRenderer.getStringBoundedHeight("T", 100) + 2, 0xFFFFFFFF);
	}
}
