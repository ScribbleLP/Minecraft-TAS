package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeKeyboard;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.mixin.ContainerScreenAccessor;
import io.github.rubiksimplosion.minecrafttas.mixin.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.search.SearchManager.Key;
import net.minecraft.client.util.InputUtil.KeyCode;
import net.minecraft.container.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.client.util.InputUtil.*;

public class InputUtil {
    public static boolean autoJumpEnabled = false;

    public static ServerPlayerEntity getServerSidePlayerEntity() {
        return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList().get(0);
    }

    public static ClientPlayerEntity getClientSidePlayerEntity() {
        return MinecraftClient.getInstance().player;
    }

    //derived from Mouse.updateMouse();
    public static int findXFromYaw(double newYaw) {
        float oldYaw = InputUtil.getClientSidePlayerEntity().yaw;
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newYaw - oldYaw)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getX());
    }

    //derived from Mouse.updateMouse();
    public static int findYFromPitch(double newPitch) {
        float oldPitch = InputUtil.getClientSidePlayerEntity().pitch;
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newPitch - oldPitch)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getY());
    }

    public static void changeYaw(double yaw) {
        FakeMouse.fakeCursorMove(findXFromYaw(yaw), MinecraftClient.getInstance().mouse.getY());
    }

    public static void changePitch(double pitch) {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), findYFromPitch(pitch));
    }

    public static void updateCursorPos() {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), MinecraftClient.getInstance().mouse.getY());
    }

    public static void moveMouseToSlot(int slotId) {
        Slot slot = ((ContainerScreen)MinecraftClient.getInstance().currentScreen).getContainer().getSlot(slotId);

        int x = ((ContainerScreenAccessor)MinecraftClient.getInstance().currentScreen).getX() + slot.xPosition;
        x = x * MinecraftClient.getInstance().window.getWidth() / MinecraftClient.getInstance().window.getScaledWidth();

        int y = ((ContainerScreenAccessor)MinecraftClient.getInstance().currentScreen).getY() + slot.yPosition;
        y = y * MinecraftClient.getInstance().window.getHeight() / MinecraftClient.getInstance().window.getScaledHeight();

        FakeMouse.fakeCursorMove(x, y);
    }

    public static void pressMouseButton(int button) {
        FakeMouse.fakeMouseButton(button, 1);
    }

    public static void releaseMouseButton(int button) {
        FakeMouse.fakeMouseButton(button, 0);
    }

    public static void pressLeftShift() {
        if (MinecraftTas.scriptManager.modifiers % 2 == 0) {
            MinecraftTas.scriptManager.modifiers += 1;
            FakeKeyboard.fakeOnKey(340, 1);
        }
    }
    public static void releaseLeftShift() {
        if (MinecraftTas.scriptManager.modifiers % 2 == 1) {
            MinecraftTas.scriptManager.modifiers -= 1;
            FakeKeyboard.fakeOnKey(340, 0);
        }
    }

    public static void pressLeftControl() {
        FakeKeyboard.fakeOnKey(341, 1);
    }
    public static void releaseLeftControl() {
        FakeKeyboard.fakeOnKey(341, 0);
    }

    public static void enableAutoJump() {
        autoJumpEnabled = true;
    }

    public static void disableAutoJump() {
        autoJumpEnabled = false;
        if (MinecraftClient.getInstance().options.keyJump.isPressed()) {
            InputUtil.releaseJump();
        }
    }

    public static void pressHotbar(int slot) {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.hotbar." + slot)).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseHotbar(int slot) {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.hotbar." + slot)).getKeyCode();
        pressKey(key, false);
    }

    public static void pressAttack() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.attack")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseAttack() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.attack")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressUse() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.use")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseUse() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.use")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressPickItem() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.pickItem")).getKeyCode();
        pressKey(key, true);
    }

    public static void releasePickItem() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.pickItem")).getKeyCode();
        pressKey(key, false);
    }



    public static void pressJump() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseJump() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressSneak() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseSneak() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressSprint() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseSprint() {
    	KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressForward() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseForward() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressBack() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseBack() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressLeft() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseLeft() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressRight() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseRight() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressDrop() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getKeyCode();
        pressKey(key, true);
    }
    public static void releaseDrop() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressInventory() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getKeyCode();
        pressKey(key, true);
    }
    public static void releaseInventory() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressSwapHand() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getKeyCode();
        pressKey(key, true);
    }
    public static void releaseSwapHand() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressChat() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.chat")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseChat() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.chat")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressCommand() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.command")).getKeyCode();
        pressKey(key, true);
    }

    public static void releaseCommand() {
        KeyCode key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.command")).getKeyCode();
        pressKey(key, false);
    }

    public static void pressEscape() {
        pressKey(fromName("key.keyboard.escape"), true);
    }

    public static void releaseEscape() {
        pressKey(fromName("key.keyboard.escape"), false);
    }

    private static void pressKey(KeyCode key, boolean pressed) {
        updateModifiers(key, pressed);
        switch(key.getCategory()) {
            case KEYSYM:
                FakeKeyboard.fakeOnKey(key.getKeyCode(), pressed ? 1 : 0);
                break;
            case MOUSE:
                FakeMouse.fakeMouseButton(key.getKeyCode(), pressed ? 1 : 0);
                break;
        }
    }

    public static void updateModifiers(KeyCode key, boolean pressed) {
        //shift
        if (key.getKeyCode() == 340) {
            MinecraftTas.scriptManager.modifiers += pressed ? 1 : -1;
        }

        //control
        else if (key.getKeyCode() == 341) {
            MinecraftTas.scriptManager.modifiers += pressed ? 2 : -2;
        }

        //alt
        else if (key.getKeyCode() == 342) {
            MinecraftTas.scriptManager.modifiers += pressed ? 4 : -4;
        }
    }
}