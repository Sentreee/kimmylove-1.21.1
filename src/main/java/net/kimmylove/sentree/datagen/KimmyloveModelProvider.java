package net.kimmylove.sentree.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.kimmylove.sentree.KimmyLoveMod;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.BlockStateModelGenerator;

public class KimmyloveModelProvider extends FabricModelProvider {

    public KimmyloveModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // No blocks
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(KimmyLoveMod.LOVE_LETTER, Models.GENERATED);
    }
}
