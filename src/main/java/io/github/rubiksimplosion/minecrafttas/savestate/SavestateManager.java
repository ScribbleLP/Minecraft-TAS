package io.github.rubiksimplosion.minecrafttas.savestate;

import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import io.github.rubiksimplosion.minecrafttas.util.PlayerState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SavestateManager {
    public List<PlayerState> softSavestates = new ArrayList<>();

    public void addSoftSavestate(PlayerState state) {
        if (MinecraftClient.getInstance().isInSingleplayer()) {
            softSavestates.add(state);
            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("savestate.create", softSavestates.size()), false);
        } else {
            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("error.savestate.multiplayer"), false);
        }
    }

    //TODO: make loading savestate in different dimensions consistent
    public void loadSoftSavestate() {
        if (softSavestates.size() > 0) {
            PlayerState state = softSavestates.get(softSavestates.size()-1);
            ServerPlayerEntity player = InputUtil.getServerSidePlayerEntity();
            player.teleport(state.world, state.position.x, state.position.y, state.position.z, state.yaw, state.pitch);
            player.setVelocity(state.velocity);
            player.setVelocityClient(state.velocity.x, state.velocity.y, state.velocity.z);
            player.velocityModified = true;
            player.velocityDirty = true;

            // inventory = 0..35
			for (int i = 0; i < state.inventory_main.length; i++) {
				player.playerContainer.getSlot(i).setStack(state.inventory_main[i].copy());
			}
			// head,chest,leg,feet = 103,102,101,100
			for (int i = 0; i < state.inventory_armor.length; i++) {
				player.playerContainer.getSlot(100 + i).setStack(state.inventory_armor[i].copy());
			}
            //offhand = 99
			player.playerContainer.getSlot(99).setStack(state.inventory_offhand.copy());
            player.container.sendContentUpdates();

            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("savestate.load", softSavestates.size()), false);
        } else {
            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("error.savestate.load.empty"), false);
        }
    }

    public void removeSoftSavestate() {
        if (softSavestates.size() > 0) {
            softSavestates.remove(softSavestates.size() - 1);
            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("Save-state #%s deleted.", softSavestates.size() + 1), false);
        } else {
            InputUtil.getClientSidePlayerEntity().addChatMessage(
                    new TranslatableText("error.savestate.remove.empty"), false);
        }
    }

}
