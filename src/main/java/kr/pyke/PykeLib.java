package kr.pyke;

import kr.pyke.command.DebugCommand;
import kr.pyke.network.PykeLibPacket;
import kr.pyke.network.payload.s2c.S2C_SendColorBGBroadcastPayload;
import kr.pyke.network.payload.s2c.S2C_SendColorBGMessagePayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PykeLib implements ModInitializer {
	public static final String MOD_ID = "pykelib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Component SYSTEM_PREFIX = Component.literal("§6[SYSTEM]§r ");

	@Override
	public void onInitialize() {
		PykeLibPacket.registerCodec();
		PykeLibPacket.registerServer();

		CommandRegistrationCallback.EVENT.register(DebugCommand::register);
	}

	public static void sendSystemMessage(List<ServerPlayer> players, int color, String message) {
		S2C_SendColorBGMessagePayload packet = new S2C_SendColorBGMessagePayload(color, message);

		for (ServerPlayer serverPlayer : players) { ServerPlayNetworking.send(serverPlayer, packet); }
	}

	public static void sendSystemMessage(ServerPlayer player, int color, String message) {
		S2C_SendColorBGMessagePayload packet = new S2C_SendColorBGMessagePayload(color, message);

		ServerPlayNetworking.send(player, packet);
	}

	public static void sendBroadcastMessage(List<ServerPlayer> players, int color, String message) {
		S2C_SendColorBGBroadcastPayload packet = new S2C_SendColorBGBroadcastPayload(color, message);

		for (ServerPlayer serverPlayer : players) { ServerPlayNetworking.send(serverPlayer, packet); }
	}
}