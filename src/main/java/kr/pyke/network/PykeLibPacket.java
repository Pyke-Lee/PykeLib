package kr.pyke.network;

import kr.pyke.network.payload.s2c.S2C_SendColorBGBroadcast;
import kr.pyke.network.payload.s2c.S2C_SendColorBGMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PykeLibPacket {
    private PykeLibPacket() { }

    public static void registerCodec() {
        // S2C (Server → Client)
        PayloadTypeRegistry.clientboundPlay().register(S2C_SendColorBGMessage.ID, S2C_SendColorBGMessage.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(S2C_SendColorBGBroadcast.ID, S2C_SendColorBGBroadcast.STREAM_CODEC);

        // C2S (Client → Server)

    }

    public static void registerServer() {

    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        // S2C_SendColorBGMessage
        ClientPlayNetworking.registerGlobalReceiver(S2C_SendColorBGMessage.ID, S2C_SendColorBGMessage::handle);
        // S2C_SendColorBGBroadcast
        ClientPlayNetworking.registerGlobalReceiver(S2C_SendColorBGBroadcast.ID, S2C_SendColorBGBroadcast::handle);
    }
}
