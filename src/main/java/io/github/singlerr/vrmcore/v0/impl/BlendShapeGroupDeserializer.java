package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.BlendShapeGroup;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class BlendShapeGroupDeserializer extends JsonDeserializer<List<BlendShapeGroup>> {
  @Override
  public List<BlendShapeGroup> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    List<BlendShapeGroupImpl> groups =
        p.readValueAs(new TypeReference<List<BlendShapeGroupImpl>>() {
        });
    return groups.stream().map(g -> (BlendShapeGroup) g).collect(Collectors.toList());
  }
}
