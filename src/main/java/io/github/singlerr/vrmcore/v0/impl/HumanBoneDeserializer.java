package io.github.singlerr.vrmcore.v0.impl;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.HumanBone;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class HumanBoneDeserializer extends JsonDeserializer<List<HumanBone>> {
    @Override
    public List<HumanBone> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        List<HumanBoneImpl> groups =
                p.readValueAs(new TypeReference<List<HumanBoneImpl>>() {
                });
        return groups.stream().map(g -> (HumanBone) g).collect(Collectors.toList());
    }
}
