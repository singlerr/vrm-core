package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public final class FloatToIntDeserializer extends StdDeserializer<Integer> {

  public FloatToIntDeserializer() {
    super(Float.class);
  }

  @Override
  public Integer deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    if (p.currentToken() == JsonToken.FIELD_NAME) {
      p.nextToken();
    }

    return (int) p.getFloatValue();
  }
}
