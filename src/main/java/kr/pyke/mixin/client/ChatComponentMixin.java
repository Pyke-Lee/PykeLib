package kr.pyke.mixin.client;

import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @Mixin(targets = "net.minecraft.client.gui.components.ChatComponent$DrawingBackgroundGraphicsAccess")
    public static abstract class BackgroundMixin {
        @Shadow @Final private GuiGraphics graphics;

        @Inject(method = "fill(IIIII)V", at = @At("HEAD"), cancellable = true)
        private void handleCustomBackgroundFill(int x1, int y1, int x2, int y2, int color, CallbackInfo ci) {
            Minecraft mc = Minecraft.getInstance();

            ChatComponent chat = mc.gui.getChat();
            List<GuiMessage.Line> lines = ((ChatComponentAccessor) chat).getTrimmedMessages();

            GuiMessage.Line currentLine = null;
            if (!lines.isEmpty()) {
                for (GuiMessage.Line line : lines) {
                    if (line.tag() != null) {
                        currentLine = line;
                        break;
                    }
                }
            }

            if (currentLine != null) {
                GuiMessageTag tag = currentLine.tag();

                int alpha = ARGB.alpha(color);
                int indicatorColor = tag.indicatorColor();
                int startColor = ARGB.color(alpha, indicatorColor);
                int endColor = ARGB.color(0, indicatorColor);
                int middleX = x1 + (int) ((x2 - x1) * 0.4);

                this.graphics.fill(x1, y1, middleX, y2, startColor);
                this.drawHorizontalGradient(this.graphics, middleX, y1, x2, y2, startColor, endColor);

                ci.cancel();
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
    }
}