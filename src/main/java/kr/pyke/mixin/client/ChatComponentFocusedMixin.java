package kr.pyke.mixin.client;

import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(targets = "net.minecraft.client.gui.components.ChatComponent$DrawingFocusedGraphicsAccess")
public abstract class ChatComponentFocusedMixin {
    @Shadow @Final private GuiGraphics graphics;

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("ChatBackgroundDebug");

    @Inject(method = "fill(IIIII)V", at = @At("HEAD"), cancellable = true)
    private void handleCustomBackgroundFill(int x1, int y1, int x2, int y2, int color, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        ChatComponent chat = mc.gui.getChat();

        if (mc.screen != null && y1 > mc.getWindow().getGuiScaledHeight() - 40) { return; }

        GuiMessage.Line targetLine = getLine(y2, (ChatComponentAccessor) chat, mc);

        if (targetLine != null && targetLine.tag() != null) {
            GuiMessageTag tag = targetLine.tag();
            if (tag.indicatorColor() != 0) {
                int alpha = ARGB.alpha(color);
                int indicatorColor = tag.indicatorColor() & 0xFFFFFF;
                int startColor = ARGB.color(alpha, indicatorColor);
                int endColor = ARGB.color(0, indicatorColor);

                int width = x2 - x1;
                int middleX = x1 + (int) (width * 0.4);

                this.graphics.fill(x1, y1, middleX, y2, startColor);
                this.drawHorizontalGradient(this.graphics, middleX, y1, x2, y2, startColor, endColor);

                ci.cancel();
            }
        }
    }

    @Unique
    private void drawHorizontalGradient(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int startColor, int endColor) {
        int width = x2 - x1;
        if (width <= 0) { return; }

        for (int i = 0; i < width; i++) {
            float delta = (float) i / (float) width;
            int currentColor = ARGB.srgbLerp(delta, startColor, endColor);

            guiGraphics.fill(x1 + i, y1, x1 + i + 1, y2, currentColor);
        }
    }

    @Unique
    private static GuiMessage.@Nullable Line getLine(int y2, ChatComponentAccessor chatAccessor, Minecraft mc) {
        float scale = (float) mc.options.chatScale().get().doubleValue();
        int guiHeight = mc.getWindow().getGuiScaledHeight();

        int m = Mth.floor((float) (guiHeight - 40) / scale);

        double lineSpacing = mc.options.chatLineSpacing().get();
        int p = (int) (9.0 * (lineSpacing + 1.0));
        if (p == 0) { return null; }

        int lx = (m - y2) / p;
        if (lx < 0) { return null; }

        int lineIndex = lx + chatAccessor.getChatScrollbarPos();
        List<GuiMessage.Line> lines = chatAccessor.getTrimmedMessages();

        if (lineIndex >= 0 && lineIndex < lines.size()) { return lines.get(lineIndex); }
        return null;
    }
}