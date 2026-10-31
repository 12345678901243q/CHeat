package com.donutclient;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DonutClient implements ClientModInitializer {
    public static final String MOD_ID = "donutclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static KeyMapping openGuiKey;
    private static int lastTotemWarningTick = -100;

    @Override
    public void onInitializeClient() {
        ClientConfig.load();

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main")
        );
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.donutclient.open_gui",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()) {
                if (client.screen == null) client.setScreen(new DonutClickGui());
                else if (client.screen instanceof DonutClickGui) client.setScreen(null);
            }

            if (ClientConfig.totemAlert && client.player != null && client.player.tickCount - lastTotemWarningTick >= 100) {
                boolean found = false;
                for (int i = 0; i < client.player.getInventory().getContainerSize(); i++) {
                    if (client.player.getInventory().getItem(i).is(Items.TOTEM_OF_UNDYING)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    client.player.displayClientMessage(Component.literal("§6[Donut] §eNo Totem of Undying found in inventory."), true);
                    lastTotemWarningTick = client.player.tickCount;
                }
            }
        });

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "main_hud"), (graphics, tickCounter) -> {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null || client.options.hideGui) return;
            int x = 8;
            int y = 8;
            int lines = (ClientConfig.coordinates ? 1 : 0) + (ClientConfig.fps ? 1 : 0) + (ClientConfig.armor ? 1 : 0);
            if (lines == 0) return;
            graphics.fill(x - 4, y - 4, x + 154, y + lines * 12 + 3, 0xA9101018);
            graphics.fill(x - 4, y - 4, x - 2, y + lines * 12 + 3, 0xFF9B5CFF);
            if (ClientConfig.coordinates) {
                graphics.drawString(client.font, String.format("XYZ: %.1f / %.1f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ()), x, y, 0xFFFFFFFF, true);
                y += 12;
            }
            if (ClientConfig.fps) {
                graphics.drawString(client.font, "FPS: " + client.getFps(), x, y, 0xFFE2D4FF, true);
                y += 12;
            }
            if (ClientConfig.armor) {
                graphics.drawString(client.font, "Armor: " + client.player.getArmorValue(), x, y, 0xFFFFFFFF, true);
            }
        });

        if (ClientConfig.welcomeMessage) {
            LOGGER.info("Donut Client 2.0 initialized. Right Shift opens the ClickGUI.");
        }
    }
}
