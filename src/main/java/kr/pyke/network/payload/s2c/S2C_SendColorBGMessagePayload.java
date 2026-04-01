package kr.pyke.network.payload.s2c;

import kr.pyke.PykeLib;
import kr.pyke.util.PykeHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record S2C_SendColorBGMessagePayload(int color, String message) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2C_SendColorBGMessagePayload> ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(PykeLib.MOD_ID, "s2c_color_bg_message"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2C_SendColorBGMessagePayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, S2C_SendColorBGMessagePayload::color,
        ByteBufCodecs.STRING_UTF8, S2C_SendColorBGMessagePayload::message,
        S2C_SendColorBGMessagePayload::new
    );

    @Override public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() { return ID; }

    public static void handle(S2C_SendColorBGMessagePayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            GuiMessageTag messageTag = new GuiMessageTag(payload.color(), null, null, "color_chatbox");
            Component component = PykeLib.SYSTEM_PREFIX.copy().append(PykeHelper.parseComponent(payload.message()));

            Minecraft.getInstance().gui.getChat().addPlayerMessage(component, null, messageTag);
        });
    }
}
