package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.Collider;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ColliderDeserializer extends JsonDeserializer<List<Collider>> {

  @Override
  public List<Collider> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    List<ColliderImpl> impl = p.readValueAs(new TypeReference<List<ColliderImpl>>() {
    });
    return impl.stream().map(c -> (Collider) c).collect(Collectors.toList());
  }
}
