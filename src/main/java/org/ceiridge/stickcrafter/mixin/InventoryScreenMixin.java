package org.ceiridge.stickcrafter.mixin;

import org.ceiridge.stickcrafter.StickCrafter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
	private Recipe<?> stickRecipe;
	
	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(at = @At("RETURN"), method = "init")
	private void init(CallbackInfo info) {
		if(!this.client.interactionManager.hasCreativeInventory()) {
			this.addButton(new ButtonWidget(2, 50, 100, 20, new LiteralText("Craft All Sticks"), (widget) -> {
				for(RecipeResultCollection recipeCollection : this.client.player.getRecipeBook().getResultsForGroup(RecipeBookGroup.CRAFTING_MISC)) {
					for(Recipe<?> recipe : recipeCollection.getAllRecipes()) {
						if(recipe.getOutput().getItem().equals(Items.STICK)) {
							this.stickRecipe = recipe;
							break;
						}
					}
				} // This big recipe finder used to be outside of this callback function, but it works better if it's here
				
				this.client.interactionManager.clickRecipe(0, this.stickRecipe, true);
				StickCrafter.doQuickMove = true;
				StickCrafter.quickMoveStopper.reset();
			}));
		}
	}
}
