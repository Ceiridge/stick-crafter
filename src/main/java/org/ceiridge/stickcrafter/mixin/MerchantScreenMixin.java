package org.ceiridge.stickcrafter.mixin;

import org.ceiridge.stickcrafter.StickCrafter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends HandledScreen<MerchantScreenHandler> {
	private ButtonWidget fastBtn;
	private MerchantScreenHandler handler;

	public MerchantScreenMixin(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(at = @At("RETURN"),
			method = "<init>(Lnet/minecraft/screen/MerchantScreenHandler;Lnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/text/Text;)V")
	private void constructor(MerchantScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo info) {
		this.handler = handler;
	}

	@Inject(at = @At("TAIL"), method = "init")
	private void init(CallbackInfo info) {
		this.fastBtn = this.addButton(new ButtonWidget(3, 50, 100, 20, new LiteralText(""), (widget) -> {
			StickCrafter.instantTrades = !StickCrafter.instantTrades;
			this.updateButtonText();
		}));

		this.updateButtonText();
	}

	@Inject(at = @At("TAIL"), method = "syncRecipeIndex")
	private void syncRecipeIndex(CallbackInfo info) {
		if (StickCrafter.instantTrades) {
			this.client.interactionManager.clickSlot(this.handler.syncId, 2, 0, SlotActionType.QUICK_MOVE, this.client.player);
		}
	}

	private void updateButtonText() {
		this.fastBtn.setMessage(new LiteralText("Fast Trading: " + (StickCrafter.instantTrades ? "On" : "Off")));
	}
}
