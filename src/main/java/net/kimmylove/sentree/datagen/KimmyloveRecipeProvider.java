package net.kimmylove.sentree.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.kimmylove.sentree.KimmyLoveMod;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class KimmyloveRecipeProvider extends FabricRecipeProvider {
    public KimmyloveRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, KimmyLoveMod.LOVE_LETTER)
                .pattern("AAA")
                .pattern("APA")
                .pattern("AAA")
                .input('A', Items.ALLIUM)
                .input('P', Items.PAPER)
                .criterion(hasItem(Items.ALLIUM), conditionsFromItem(Items.ALLIUM))
                .offerTo(exporter);
    }
}