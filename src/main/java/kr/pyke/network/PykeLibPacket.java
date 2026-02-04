package kr.pyke.network;

import kr.pyke.network.payload.s2c.S2C_SendColorBGBroadcast;
import kr.pyke.network.payload.s2c.S2C_SendColorBGMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class PykeLibPacket {
    private PykeLibPacket() { }

    public static void registerServer() {

    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        S2C_SendColorBGMessage.register();
        S2C_SendColorBGBroadcast.register();
    }
}