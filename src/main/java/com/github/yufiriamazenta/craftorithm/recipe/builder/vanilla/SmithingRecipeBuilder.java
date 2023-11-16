package com.github.yufiriamazenta.craftorithm.recipe.builder.vanilla;

import com.github.yufiriamazenta.craftorithm.recipe.builder.AbstractRecipeBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class SmithingRecipeBuilder extends AbstractRecipeBuilder {

    protected RecipeChoice base, addition;

    @Override
    public SmithingRecipeBuilder key(NamespacedKey key) {
        return (SmithingRecipeBuilder) super.key(key);
    }

    @Override
    public SmithingRecipeBuilder result(ItemStack result) {
        return (SmithingRecipeBuilder) super.result(result);
    }

    public RecipeChoice getBase() {
        return base.clone();
    }

    public SmithingRecipeBuilder base(RecipeChoice base) {
        this.base = base;
        return this;
    }

    public RecipeChoice getAddition() {
        return addition.clone();
    }

    public SmithingRecipeBuilder addition(RecipeChoice addition) {
        this.addition = addition;
        return this;
    }

    public SmithingRecipe build() {
        return new SmithingRecipe(getKey(), getResult(), base, addition);
    }

    public static SmithingRecipeBuilder builder() {
        return new SmithingRecipeBuilder();
    }

}
