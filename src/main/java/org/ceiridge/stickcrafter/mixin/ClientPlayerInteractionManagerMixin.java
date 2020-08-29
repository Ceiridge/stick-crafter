package org.ceiridge.stickcrafter.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

// A mixin only for debug purposes => disabled
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(at = @At("HEAD"), method = "clickSlot")
	private void clickSlot(int syncId, int slotId, int clickData, SlotActionType actionType, PlayerEntity player,
			CallbackInfoReturnable<ItemStack> info) {
		System.out.println("clickSlot: " + syncId + " " + slotId + " " + clickData + " " + actionType.name() + " " + player.getName().toString());
//		new Throwable().printStackTrace();
	}
}
