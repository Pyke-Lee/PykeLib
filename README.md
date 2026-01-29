# PykeLib

```
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

```
dependencies {
    modImplementation "com.github.Pyke-Lee:PykeLib:1.0.3"
}
```

```
PykeLib.sendSystemMessage(List<ServerPlayer> players, int color, String message)
PykeLib.sendSystemMessage(ServerPlayer player, int color, String message)
PykeLib.sendBroadcastMessage(List<ServerPlayer> players, int color, String message)

PykeLibClient.sendSystemMessage(int color, String message)

COLOR: RED, GOLD, YELLOW, LIME, AQUA, DARK_AQUA, BLUE, LIGHT_PURPLE, PURPLE
ex) COLOR.RED.getColor() or 0xFF5555
```
