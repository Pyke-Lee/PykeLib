package kr.pyke.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import kr.pyke.PykeLib;
import kr.pyke.network.payload.s2c.S2C_CustomToastPayload;
import kr.pyke.type.COLOR;
import kr.pyke.type.TOAST_TYPE;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.world.item.Items;

import java.util.List;

public class DebugCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx, Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("디버그")
            .requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(2))))
            .then(Commands.literal("메시지")
                .executes(DebugCommand::sendColorBoxMessage)
            )
            .then(Commands.literal("토스트")
                .executes(DebugCommand::sendCustomToast)
            )
        );
    }

    private static int sendColorBoxMessage(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayer();
        if (player == null) { return 0; }

        List<ServerPlayer> serverPlayers = source.getServer().getPlayerList().getPlayers();
        PykeLib.sendSystemMessage(serverPlayers, COLOR.LIME.getColor(), "해당 메시지는 디버그용 테스트 메시지입니다.");
        PykeLib.sendSystemMessage(serverPlayers, COLOR.RED.getColor(), "해당 메시지는 디버그용 테스트 메시지입니다.");

        return 1;
    }

    private static int sendCustomToast(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayer();
        if (player == null) { return 0; }

        S2C_CustomToastPayload payload = new S2C_CustomToastPayload(Identifier.fromNamespaceAndPath(PykeLib.MOD_ID, "test_toast_0"), TOAST_TYPE.TUTORIAL, Items.BEACON, Component.literal("토스트 타이틀"), Component.literal("테스트용 토스트 메시지 입니다."), 5000, true);
        ServerPlayNetworking.send(player, payload);

        return 1;
    }
}
