package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.BlendShapeGroup;
import java.io.IOException;
import java.util.List;

public final class BlendShapeGroupDeserializer extends JsonDeserializer<List<BlendShapeGroup>> {
  @Override
  public List<BlendShapeGroup> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
    List<BlendShapeGroupImpl> groups =
        p.readValueAs(new TypeReference<List<BlendShapeGroupImpl>>() {
        });
    return groups.stream().map(g -> (BlendShapeGroup) g).toList();
  }
}
