package kr.pyke.network.payload.s2c;

import io.netty.buffer.Unpooled;
import kr.pyke.PykeLib;
import kr.pyke.util.PykeHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class S2C_SendColorBGBroadcast {
    public static final ResourceLocation ID = new ResourceLocation(PykeLib.MOD_ID, "s2c_color_bg_broadcast");

    public static void send(ServerPlayer player, int color, String message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeVarInt(color);
        buf.writeUtf(message);
        ServerPlayNetworking.send(player, ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            int color = buf.readVarInt();
            String message = buf.readUtf();

            client.execute(() -> {
                GuiMessageTag messageTag = new GuiMessageTag(color, null, null, "color_chatbox");
                Component component = Component.literal("ꅑ ").append(PykeHelper.parseComponent(message));

                Minecraft.getInstance().gui.getChat().addMessage(Component.literal(" "), null, messageTag);
                Minecraft.getInstance().gui.getChat().addMessage(component, null, messageTag);
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal(" "), null, messageTag);
            });
        });
    }
}