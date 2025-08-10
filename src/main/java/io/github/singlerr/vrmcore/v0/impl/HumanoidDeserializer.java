package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.Humanoid;

import java.io.IOException;

public final class HumanoidDeserializer extends JsonDeserializer<Humanoid> {
    @Override
    public Humanoid deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return p.readValueAs(HumanoidImpl.class);
    }
}
