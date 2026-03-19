package kr.pyke.util.constants;

public enum COLOR {
    // ===== 빨강 =====
    RED(0xFF5555),
    DARK_RED(0xAA0000),
    SOFT_RED(0xEF5350),
    ROSE(0xEF9A9A),

    // ===== 주황 =====
    ORANGE(0xFF8C00),
    GOLD(0xFFAA00),
    BRONZE(0xCD7F32),

    // ===== 노랑 =====
    YELLOW(0xFFFF55),
    SOFT_YELLOW(0xFFD54F),
    AMBER(0xFFC107),

    // ===== 초록 =====
    LIME(0x55FF55),
    GREEN(0x4CAF50),
    SOFT_GREEN(0x81C784),
    DARK_GREEN(0x00AA00),
    EMERALD(0x2ECC71),
    XP_GREEN(0x7EFC20),

    // ===== 파랑 =====
    BLUE(0x5555FF),
    DARK_BLUE(0x0000AA),
    SKY_BLUE(0x4FC3F7),
    SOFT_BLUE(0x29B6F6),
    OCEAN(0x2196F3),

    // ===== 청록 =====
    AQUA(0x55FFFF),
    DARK_AQUA(0x00AAAA),
    TEAL(0x009688),

    // ===== 보라 =====
    PURPLE(0xAA00AA),
    LIGHT_PURPLE(0xFF55FF),
    SOFT_PURPLE(0xAB47BC),
    VIOLET(0x7C4DFF),

    // ===== 무채색 =====
    WHITE(0xFFFFFF),
    SOFT_WHITE(0xE0E0E0),
    SILVER(0xB0BEC5),
    GRAY(0xAAAAAA),
    DARK_GRAY(0x555555),
    CHARCOAL(0x3A3A3A),
    BLACK(0x000000),

    // ===== 갈색 =====
    BROWN(0x8D6E63),
    DARK_BROWN(0x5D4037);

    private final int color;

    COLOR(int color) { this.color = color; }

    public int getColor() { return color; }
}
