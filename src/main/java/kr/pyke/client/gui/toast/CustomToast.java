package kr.pyke.client.gui.toast;

import kr.pyke.network.payload.s2c.S2C_CustomToastPayload;
import kr.pyke.type.TOAST_TYPE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomToast implements Toast {
    private static final Map<Identifier, CustomToast> ACTIVE_TOASTS = new HashMap<>();

    private final TOAST_TYPE type;
    private final ItemStack itemStack;
    private final Component title;
    private final List<FormattedCharSequence> messageLines;
    private final int displayTime;
    private float progress;
    private Visibility visibility = Visibility.SHOW;
    private final boolean autoProgress;

    public CustomToast(Font font, S2C_CustomToastPayload payload) {
        this.type = payload.toastType();
        this.itemStack = new ItemStack(payload.item());
        this.title = payload.title().copy().withColor(0xFF000000);
        this.messageLines = font.split(payload.message().copy().withColor(0xFF555555), 125);
        this.progress = payload.progress();
        this.displayTime = payload.displayTime();
        this.autoProgress = payload.autoProgress();
    }

    @Override public @NonNull Visibility getWantedVisibility() { return this.visibility; }

    @Override
    public void update(@NonNull ToastManager manager, long fullyVisibleForMs) {
        if (fullyVisibleForMs > (long) this.displayTime) {
            this.visibility = Visibility.HIDE;
        }
        else {
            if (this.autoProgress) {
                this.progress = Math.clamp((float) fullyVisibleForMs / this.displayTime, 0.f, 1.f);
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, @NonNull Font font, long fullyVisibleForMs) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.type.getSprite(), 0, 0, this.width(), this.height());

        int titleColor = (this.type == TOAST_TYPE.SYSTEM || this.type == TOAST_TYPE.TUTORIAL) ? -11534256 : -256;
        graphics.text(font, this.title, 30, 7, titleColor, false);

        for (int i = 0; i < Math.min(this.messageLines.size(), 2); ++i) {
            graphics.text(font, this.messageLines.get(i), 30, 18 + i * 11, -1, false);
        }

        if (!this.itemStack.isEmpty()) {
            graphics.fakeItem(this.itemStack, 8, 8);
        }

        if (this.type == TOAST_TYPE.TUTORIAL && this.progress >= 0) {
            int y = this.height() - 4;
            graphics.fill(3, y, 157, y + 1, -1);
            graphics.fill(3, y, (int) (3.f + 154.f * this.progress), y + 1, -16755456);
        }
    }

    public void updateData(float progress) {
        this.progress = progress;
    }

    public void hide() {
        this.visibility = Visibility.HIDE;
    }

    public static void addOrUpdate(Minecraft client, S2C_CustomToastPayload payload) {
        CustomToast existing = ACTIVE_TOASTS.get(payload.id());
        if (existing != null && existing.visibility == Visibility.SHOW) {
            existing.updateData(payload.progress());
            if (payload.progress() >= 1.f) {
                existing.hide();
                ACTIVE_TOASTS.remove(payload.id());
            }
        }
        else {
            CustomToast newToast = new CustomToast(client.font, payload);
            ACTIVE_TOASTS.put(payload.id(), newToast);
            client.getToastManager().addToast(newToast);
        }
    }
}