package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.singlerr.vrmcore.Collider;
import io.github.singlerr.vrmcore.utils.Vector3f;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ColliderImpl implements Collider {

  @JsonProperty("radius")
  float radius;
  @JsonProperty("offset")
  private Vector3f offset;
}
