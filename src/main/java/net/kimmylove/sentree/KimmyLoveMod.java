package net.kimmylove.sentree;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kimmylove.sentree.item.custom.LoveLetterItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.UUID;

public class KimmyLoveMod implements ModInitializer {
	public static final String MOD_ID = "kimmylove";

	public static final Set<UUID> ALLOWED_UUIDS = Set.of(
			UUID.fromString("c52855ed-a8f6-4a15-89bd-394dc8a15294"),
			UUID.fromString("42090b34-c494-4587-a385-d4d506656355")
	);

	public static final Item ASH = Registry.register(
			Registries.ITEM,
			Identifier.of(MOD_ID, "ash"),
			new Item(new Item.Settings())
	);

	public static final Item LOVE_LETTER = Registry.register(
			Registries.ITEM,
			Identifier.of(MOD_ID, "love_letter"),
			new LoveLetterItem(new Item.Settings().maxCount(1).maxDamage(50))
	);

	@Override
	public void onInitialize() {
		// If someone not allowed holds the letter, it turns into Ash
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				if (ALLOWED_UUIDS.contains(player.getUuid())) continue;

				convertHandIfLetter(player, Hand.MAIN_HAND);
				convertHandIfLetter(player, Hand.OFF_HAND);
			}
		});
	}

	private static void convertHandIfLetter(ServerPlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		if (!stack.isOf(LOVE_LETTER)) return;

		// Convert to ash
		player.setStackInHand(hand, new ItemStack(ASH, stack.getCount()));

		// Send the message ONCE by using a short cooldown gate
		// (If they somehow keep holding more letters, they’ll get messaged again after cooldown)
		if (!player.getItemCooldownManager().isCoolingDown(ASH)) {
			player.sendMessage(Text.literal("This letter isn't for you").formatted(Formatting.RED), false);
			player.getItemCooldownManager().set(ASH, 40); // 2 seconds
		}
	}
}
