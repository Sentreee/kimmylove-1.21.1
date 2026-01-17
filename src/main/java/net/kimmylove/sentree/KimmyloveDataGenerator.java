package net.kimmylove.sentree;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;
import net.kimmylove.sentree.datagen.KimmyloveModelProvider;
import net.kimmylove.sentree.datagen.KimmyloveRecipeProvider;

public class KimmyloveDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(KimmyloveModelProvider::new);
		pack.addProvider(KimmyloveRecipeProvider::new);
	}
}
