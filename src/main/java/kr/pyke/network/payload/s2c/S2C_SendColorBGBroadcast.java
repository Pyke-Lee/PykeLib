package kr.pyke.network.payload.s2c;

import kr.pyke.PykeLib;
import kr.pyke.util.PykeHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record S2C_SendColorBGBroadcast(int color, String message) implements CustomPacketPayload {
    public static final Type<S2C_SendColorBGBroadcast> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(PykeLib.MOD_ID, "s2c_color_bg_broadcast"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2C_SendColorBGBroadcast> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, S2C_SendColorBGBroadcast::color,
        ByteBufCodecs.STRING_UTF8, S2C_SendColorBGBroadcast::message,
        S2C_SendColorBGBroadcast::new
    );

    @Override public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return ID; }

    public static void handle(S2C_SendColorBGBroadcast payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            GuiMessageTag messageTag = new GuiMessageTag(payload.color(), null, null, "color_chatbox");
            Component component = Component.literal("ꅑ ").append(PykeHelper.parseComponent(payload.message()));

            Minecraft.getInstance().gui.getChat().addMessage(Component.literal(" "), null, messageTag);
            Minecraft.getInstance().gui.getChat().addMessage(component, null, messageTag);
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal(" "), null, messageTag);
        });
    }
}
