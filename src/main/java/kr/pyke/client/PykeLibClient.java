package kr.pyke.client;

import kr.pyke.PykeLib;
import kr.pyke.network.PykeLibPacket;
import kr.pyke.util.PykeHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;

public class PykeLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PykeLibPacket.registerClient();
    }

    public static void sendSystemMessage(int color, String message) {
        GuiMessageTag messageTag = new GuiMessageTag(color, null, null, "color_chatbox");
        Component component = PykeLib.SYSTEM_PREFIX.copy().append(PykeHelper.parseComponent(message));

        Minecraft.getInstance().gui.getChat().addPlayerMessage(component, null, messageTag);
    }
}
