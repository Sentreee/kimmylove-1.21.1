package net.kimmylove.sentree;

import net.fabricmc.api.ModInitializer;
import net.kimmylove.sentree.item.custom.LoveLetterItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class KimmyLoveMod implements ModInitializer {
	public static final String MOD_ID = "kimmylove";

	public static final Item LOVE_LETTER = Registry.register(
			Registries.ITEM,
			Identifier.of(MOD_ID, "love_letter"),
			new LoveLetterItem(new Item.Settings().maxCount(1).maxDamage(50))
	);

	@Override
	public void onInitialize() {
		// Everything is registered statically
	}
}
