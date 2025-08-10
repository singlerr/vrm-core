package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.Animations;

import java.io.IOException;

public class AnimationsDeserializer extends JsonDeserializer<Animations> {
    @Override
    public Animations deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return p.readValueAs(SecondaryAnimation.class);
    }
}
