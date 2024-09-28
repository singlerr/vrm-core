package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.singlerr.vrmcore.HumanBone;
import io.github.singlerr.vrmcore.Humanoid;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class HumanoidImpl implements Humanoid {

  @JsonProperty("armStretch")
  private float armStretch;

  @JsonProperty("legStretch")
  private float legStretch;

  @JsonProperty("lowerArmTwist")
  private float lowerArmTwist;

  @JsonProperty("lowerLegTwist")
  private float lowerLegTwist;

  @JsonProperty("upperArmTwist")
  private float upperArmTwist;

  @JsonProperty("upperLegTwist")
  private float upperLegTwist;

  @JsonProperty("feetSpacing")
  private float feetSpacing;

  @JsonProperty("hasTranslationDoF")
  @Accessors(fluent = true)
  private boolean hasTranslationDoF;

  @JsonProperty("humanBones")
  @JsonDeserialize(using = HumanBoneDeserializer.class)
  private List<HumanBone> bones;
}
