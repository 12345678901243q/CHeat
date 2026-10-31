package com.donutclient;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class DonutClickGui extends Screen {
    private static final int W = 430;
    private static final int H = 292;
    private static final int PURPLE = 0xFF9B5CFF;

    public DonutClickGui() { super(Component.literal("Donut Client")); }

    @Override
    protected void init() {
        int left = (width - W) / 2;
        int top = (height - H) / 2;
        int col1 = left + 22;
        int col2 = left + 224;
        int row = top + 88;

        addRenderableWidget(toggle("Coordinates HUD", () -> ClientConfig.coordinates,
                v -> ClientConfig.coordinates = v, col1, row));
        addRenderableWidget(toggle("FPS HUD", () -> ClientConfig.fps,
                v -> ClientConfig.fps = v, col1, row + 34));
        addRenderableWidget(toggle("Armor HUD", () -> ClientConfig.armor,
                v -> ClientConfig.armor = v, col1, row + 68));
        addRenderableWidget(toggle("Totem reminder", () -> ClientConfig.totemAlert,
                v -> ClientConfig.totemAlert = v, col2, row));
        addRenderableWidget(toggle("Welcome log", () -> ClientConfig.welcomeMessage,
                v -> ClientConfig.welcomeMessage = v, col2, row + 34));

        addRenderableWidget(Button.builder(Component.literal("Save settings"), b -> {
            ClientConfig.save();
            if (minecraft != null && minecraft.player != null) {
                minecraft.player.displayClientMessage(Component.literal("§a[Donut] Settings saved."), true);
            }
        }).bounds(left + 22, top + H - 48, 180, 22).build());

        addRenderableWidget(Button.builder(Component.literal("Close"), b -> onClose())
                .bounds(left + W - 202, top + H - 48, 180, 22).build());
    }

    private Button toggle(String label, java.util.function.BooleanSupplier get,
                          java.util.function.Consumer<Boolean> set, int x, int y) {
        return Button.builder(toggleText(label, get.getAsBoolean()), b -> {
            boolean next = !get.getAsBoolean();
            set.accept(next);
            b.setMessage(toggleText(label, next));
        }).bounds(x, y, 184, 24).build();
    }

    private Component toggleText(String label, boolean enabled) {
        return Component.literal(label + "  [" + (enabled ? "ON" : "OFF") + "]");
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        int left = (width - W) / 2;
        int top = (height - H) / 2;
        graphics.fill(left - 2, top - 2, left + W + 2, top + H + 2, 0xFF070810);
        graphics.fill(left, top, left + W, top + H, 0xFF151724);
        graphics.fill(left, top, left + W, top + 4, PURPLE);
        graphics.fill(left, top + 58, left + W, top + 59, 0xFF343044);
        graphics.drawString(font, "DONUT CLIENT", left + 22, top + 18, 0xFFFFFFFF, true);
        graphics.drawString(font, "2.0  •  FABRIC 1.21.11", left + 22, top + 37, 0xFFBDA1F5, false);
        graphics.drawString(font, "HUD", left + 22, top + 72, PURPLE, true);
        graphics.drawString(font, "UTILITIES", left + 224, top + 72, PURPLE, true);
        graphics.drawString(font, "Right Shift  •  Toggle menu", left + 22, top + H - 18, 0xFF8C899B, false);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override public boolean isPauseScreen() { return false; }

    @Override
    public void onClose() {
        ClientConfig.save();
        super.onClose();
    }
}
