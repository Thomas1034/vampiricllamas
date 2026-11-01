package com.startraveler.vampiricllamas.client;

import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class VampireLlamaRenderState extends LlamaRenderState {
    public boolean isAttacking = false;
    public boolean isNight = false;
    public @NotNull Identifier trueAppearance = VampireLlama.VAMPIRE_LLAMA_LOCATION;
    public @NotNull Identifier eyes = VampireLlama.RED_VAMPIRE_EYES;
}
