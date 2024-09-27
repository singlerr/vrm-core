package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.singlerr.vrmcore.HumanBone;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Getter
class HumanBoneImpl implements HumanBone {

  @JsonProperty("bone")
  private String name;
  @JsonProperty("node")
  @JsonDeserialize(using = FloatToIntDeserializer.class)
  private int node;

  @JsonProperty("useDefaultValues")
  @Accessors(fluent = true)
  private boolean useDefaultValues;
}
