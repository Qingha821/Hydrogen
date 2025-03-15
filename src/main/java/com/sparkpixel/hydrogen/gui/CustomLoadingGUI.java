package com.sparkpixel.hydrogen.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CustomLoadingGUI {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("hydrogen:textures/gui/loading_bg.png");
    private static final ResourceLocation PROGRESS_BAR = new ResourceLocation("hydrogen:textures/gui/progress.png");

    // 原始素材基准分辨率（根据你的1920x1080设定）
    private static final int BASE_WIDTH = 1920;
    private static final int BASE_HEIGHT = 1080;

    public static void renderCustomLoading(GuiGraphics guiGraphics, float progress, boolean done) {
        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();

        // 获取实际窗口尺寸（GUI缩放后）
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();

        // 计算宽高比例因子
        float widthScale = (float) screenWidth / BASE_WIDTH;
        float heightScale = (float) screenHeight / BASE_HEIGHT;

        // 渲染自适应背景（保持原始宽高比）
        renderBackground(guiGraphics, screenWidth, screenHeight, widthScale, heightScale);

        // 渲染动态进度条
        renderProgressBar(guiGraphics, screenWidth, screenHeight, widthScale, heightScale, progress);

        // 渲染自适应文本
        renderProgressText(guiGraphics, screenWidth, screenHeight, widthScale, progress);
    }

    private static void renderBackground(GuiGraphics guiGraphics, int screenWidth, int screenHeight,
                                         float widthScale, float heightScale) {
        // 保持原始宽高比的缩放
        float targetRatio = (float)BASE_WIDTH / BASE_HEIGHT;
        float currentRatio = (float)screenWidth / screenHeight;

        if (currentRatio > targetRatio) {
            // 以高度为基准缩放
            int scaledWidth = (int)(screenHeight * targetRatio);
            int xOffset = (screenWidth - scaledWidth) / 2;
            guiGraphics.blit(
                    BACKGROUND,
                    xOffset, 0,
                    scaledWidth, screenHeight,
                    0, 0,
                    BASE_WIDTH, BASE_HEIGHT,
                    BASE_WIDTH, BASE_HEIGHT
            );
        } else {
            // 以宽度为基准缩放
            int scaledHeight = (int)(screenWidth / targetRatio);
            int yOffset = (screenHeight - scaledHeight) / 2;
            guiGraphics.blit(
                    BACKGROUND,
                    0, yOffset,
                    screenWidth, scaledHeight,
                    0, 0,
                    BASE_WIDTH, BASE_HEIGHT,
                    BASE_WIDTH, BASE_HEIGHT
            );
        }
    }

    private static void renderProgressBar(GuiGraphics guiGraphics, int screenWidth, int screenHeight,
                                          float widthScale, float heightScale, float progress) {
        // 进度条尺寸动态计算（基于原始比例）
        int barWidth = (int)(600 * widthScale);  // 原始设计600px宽
        int barHeight = (int)(30 * heightScale); // 原始设计30px高
        int xPos = (screenWidth - barWidth) / 2;
        int yPos = screenHeight - (int)(100 * heightScale);

        // 进度填充部分
        int filledWidth = (int)(barWidth * progress);

        guiGraphics.blit(
                PROGRESS_BAR,
                xPos, yPos,
                0, 0,
                filledWidth, barHeight,
                barWidth, barHeight
        );
    }

    private static void renderProgressText(GuiGraphics guiGraphics, int screenWidth, int screenHeight,
                                           float widthScale, float progress) {
        Minecraft mc = Minecraft.getInstance();
        String text = String.format("%.1f%%", progress * 100);

        // 动态字体大小（基于宽度比例）
        int fontSize = (int)(48 * widthScale);
        Font font = mc.font;

        // 临时调整字体渲染大小
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.scale(fontSize / 48f, fontSize / 48f, 1);

        int textWidth = (int)(font.width(text) * (fontSize / 48f));
        int xPos = (screenWidth - textWidth) / 2;
        int yPos = screenHeight - (int)(150 * (screenHeight / 1080f));

        guiGraphics.drawString(
                font,
                Component.literal(text),
                xPos, yPos,
                0xFFFFFF,
                false
        );

        poseStack.popPose();
    }
}