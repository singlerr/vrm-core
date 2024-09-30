package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class FloatListToIntListDeserializer extends JsonDeserializer<List<Integer>> {


  @Override
  public List<Integer> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    List<Float> originals = p.readValueAs(new TypeReference<List<Float>>() {
    });
    return originals.stream().map(Float::intValue).collect(Collectors.toList());
  }
}
