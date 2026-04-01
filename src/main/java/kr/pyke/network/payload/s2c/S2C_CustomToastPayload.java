package kr.pyke.network.payload.s2c;

import kr.pyke.PykeLib;
import kr.pyke.client.gui.toast.CustomToast;
import kr.pyke.type.TOAST_TYPE;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Item item, Component title, Component message, int displayTime, float progress, boolean autoProgress) implements CustomPacketPayload {
    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Item item, Component title, Component message, int displayTime) {
        this(id, toastType, item, title, message, displayTime, -1.f, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Component title, Component message, int displayTime) {
        this(id, toastType, null, title, message, displayTime, -1.f, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Item item, Component title, Component message, float progress) {
        this(id, toastType, item, title, message, 5000, progress, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Component title, Component message, float progress) {
        this(id, toastType, null, title, message, 5000, progress, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Item item, Component title, Component message) {
        this(id, toastType, item, title, message, 5000, -1.f, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Component title, Component message) {
        this(id, toastType, null, title, message, 5000, -1.f, false);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Component title, Component message, int displayTime, boolean autoProgress) {
        this(id, toastType, null, title, message, displayTime, 0.f, autoProgress);
    }

    public S2C_CustomToastPayload(Identifier id, TOAST_TYPE toastType, Item item, Component title, Component message, int displayTime, boolean autoProgress) {
        this(id, toastType, item, title, message, displayTime, 0.f, autoProgress);
    }

    public static final Type<S2C_CustomToastPayload> ID = new Type<>(Identifier.fromNamespaceAndPath(PykeLib.MOD_ID, "s2c_custom_toast"));

    @Override public @NotNull Type<? extends CustomPacketPayload> type() { return ID; }

    public static final StreamCodec<RegistryFriendlyByteBuf, S2C_CustomToastPayload> STREAM_CODEC = StreamCodec.composite(
        Identifier.STREAM_CODEC, S2C_CustomToastPayload::id,
        TOAST_TYPE.STREAM_CODEC, S2C_CustomToastPayload::toastType,
        ByteBufCodecs.registry(Registries.ITEM), S2C_CustomToastPayload::item,
        ComponentSerialization.STREAM_CODEC, S2C_CustomToastPayload::title,
        ComponentSerialization.STREAM_CODEC, S2C_CustomToastPayload::message,
        ByteBufCodecs.VAR_INT, S2C_CustomToastPayload::displayTime,
        ByteBufCodecs.FLOAT, S2C_CustomToastPayload::progress,
        ByteBufCodecs.BOOL, S2C_CustomToastPayload::autoProgress,
        S2C_CustomToastPayload::new
    );

    public static void handle(S2C_CustomToastPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> CustomToast.addOrUpdate(context.client(), payload));
    }
}