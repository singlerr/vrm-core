package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.ColliderGroup;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ColliderGroupDeserializer extends JsonDeserializer<List<ColliderGroup>> {
  @Override
  public List<ColliderGroup> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    List<ColliderGroupImpl> impl = p.readValueAs(new TypeReference<List<ColliderGroupImpl>>() {
    });
    return impl.stream().map(g -> (ColliderGroup) g).collect(Collectors.toList());
  }
}
