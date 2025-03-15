package com.sparkpixel.hydrogen.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sparkpixel.hydrogen.gui.CustomLoadingGUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public abstract class MixinLoadingOverlay extends Overlay {
    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // 使用新的 GuiGraphics 系统
        CustomLoadingGUI.renderCustomLoading(guiGraphics, this.progress, this.reload.isDone());
        ci.cancel();
    }
}