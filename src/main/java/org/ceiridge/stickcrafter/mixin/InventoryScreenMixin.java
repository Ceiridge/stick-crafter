package org.ceiridge.stickcrafter.mixin;

import org.ceiridge.stickcrafter.StickCrafter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(at = @At("RETURN"), method = "init")
	private void init(CallbackInfo info) {
		if (!this.client.interactionManager.hasCreativeInventory()) {
			this.addButton(new ButtonWidget(2, 50, 100, 20, new LiteralText("Craft All Sticks"), (widget) -> {
				/*RecipeManager manager = this.client.world.getRecipeManager();

				for (Identifier id : manager.keys().collect(Collectors.toList())) {
					System.out.println(id.toString() + " " + id.getPath());
					Optional<? extends Recipe<?>> recipeOp = manager.get(id);
					if (recipeOp.isPresent()) {
						Recipe<?> stickRecipe = recipeOp.get();
						
						if (stickRecipe.getOutput().getItem().equals(Items.STICK)) {
							
						}
					}
				}*/
				
				this.client.interactionManager.clickRecipe(this.handler.syncId, this.client.world.getRecipeManager().get(new Identifier("minecraft:stick")).get(), true);
				StickCrafter.doQuickMove = true;
				StickCrafter.quickMoveStopper.reset();
			}));
		}
	}
}
