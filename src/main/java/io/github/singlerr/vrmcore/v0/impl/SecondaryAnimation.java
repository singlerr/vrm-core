package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.singlerr.vrmcore.Animations;
import io.github.singlerr.vrmcore.BoneGroup;
import io.github.singlerr.vrmcore.ColliderGroup;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecondaryAnimation implements Animations {

  @JsonProperty("boneGroups")
  @JsonDeserialize(using = BoneGroupDeserializer.class)
  private List<BoneGroup> boneGroups;

  @JsonProperty("colliderGroups")
  @JsonDeserialize(using = ColliderGroupDeserializer.class)
  private List<ColliderGroup> colliderGroups;

}
