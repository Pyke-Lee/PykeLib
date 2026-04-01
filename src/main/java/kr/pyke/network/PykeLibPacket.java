package kr.pyke.network;

import kr.pyke.network.payload.s2c.S2C_CustomToastPayload;
import kr.pyke.network.payload.s2c.S2C_SendColorBGBroadcastPayload;
import kr.pyke.network.payload.s2c.S2C_SendColorBGMessagePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PykeLibPacket {
    private PykeLibPacket() { }

    public static void registerCodec() {
        // S2C (Server → Client)
        PayloadTypeRegistry.clientboundPlay().register(S2C_SendColorBGMessagePayload.ID, S2C_SendColorBGMessagePayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(S2C_SendColorBGBroadcastPayload.ID, S2C_SendColorBGBroadcastPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(S2C_CustomToastPayload.ID, S2C_CustomToastPayload.STREAM_CODEC);

        // C2S (Client → Server)

    }

    public static void registerServer() {

    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        // S2C_SendColorBGMessagePayload
        ClientPlayNetworking.registerGlobalReceiver(S2C_SendColorBGMessagePayload.ID, S2C_SendColorBGMessagePayload::handle);
        // S2C_SendColorBGBroadcastPayload
        ClientPlayNetworking.registerGlobalReceiver(S2C_SendColorBGBroadcastPayload.ID, S2C_SendColorBGBroadcastPayload::handle);
        // S2C_CustomToastPayload
        ClientPlayNetworking.registerGlobalReceiver(S2C_CustomToastPayload.ID, S2C_CustomToastPayload::handle);
    }
}
