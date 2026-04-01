package kr.pyke.type;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public enum TOAST_TYPE {
    SYSTEM(Identifier.withDefaultNamespace("toast/system")),
    ADVANCEMENT(Identifier.withDefaultNamespace("toast/advancement")),
    RECIPE(Identifier.withDefaultNamespace("toast/recipe")),
    TUTORIAL(Identifier.withDefaultNamespace("toast/tutorial"));

    private final Identifier sprite;

    public static final StreamCodec<ByteBuf, TOAST_TYPE> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(
        ordinal -> values()[ordinal],
        TOAST_TYPE::ordinal
    );

    TOAST_TYPE(Identifier background) {
        this.sprite = background;
    }

    public Identifier getSprite() { return this.sprite; }
}
