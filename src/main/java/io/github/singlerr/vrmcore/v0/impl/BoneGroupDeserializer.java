package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.BoneGroup;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BoneGroupDeserializer extends JsonDeserializer<List<BoneGroup>> {
  @Override
  public List<BoneGroup> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    List<BoneGroupImpl> impl = p.readValueAs(new TypeReference<List<BoneGroupImpl>>() {
    });
    return impl.stream().map(b -> (BoneGroup) b).collect(Collectors.toList());
  }
}
