package com.startraveler.vampiricllamas.entity;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public interface VampiresThatAttack {

    boolean isAttacking();

    @NotNull Identifier trueAppearance();
    @NotNull Identifier eyes();
}
