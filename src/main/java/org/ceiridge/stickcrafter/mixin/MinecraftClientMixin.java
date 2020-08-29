package org.ceiridge.stickcrafter.mixin;

import org.ceiridge.stickcrafter.StickCrafter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.screen.slot.SlotActionType;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Shadow
	private ClientPlayerInteractionManager interactionManager;
	@Shadow
	private ClientPlayerEntity player;
	@Shadow
	private Screen currentScreen;

	@Inject(at = @At("RETURN"), method = "tick")
	private void tick(CallbackInfo info) {
		if (StickCrafter.doQuickMove && StickCrafter.quickMoveStopper.hasReached()) {
			StickCrafter.doQuickMove = false;

			if (this.player != null && this.currentScreen instanceof InventoryScreen) {
				this.interactionManager.clickSlot(0, 0, 0, SlotActionType.QUICK_MOVE, this.player); // craft result slot, shift click
			}
		}
	}
}
