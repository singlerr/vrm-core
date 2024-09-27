package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.github.singlerr.vrmcore.BlendShapeBinding;
import java.io.IOException;

public class BlendShapeBindingDeserializer extends JsonDeserializer<BlendShapeBinding> {

  @Override
  public BlendShapeBinding deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
    return p.readValueAs(BlendShapeBindingImpl.class);
  }
}
