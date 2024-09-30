package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.BlendShapeBinding;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BlendShapeBindingDeserializer extends JsonDeserializer<List<BlendShapeBinding>> {

  @Override
  public List<BlendShapeBinding> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    List<BlendShapeBindingImpl> bindings =
        p.readValueAs(new TypeReference<List<BlendShapeBindingImpl>>() {
        });
    return bindings.stream().map(b -> (BlendShapeBinding) b).collect(Collectors.toList());
  }
}
