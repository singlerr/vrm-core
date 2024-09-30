package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.BoneGroup;
import io.github.singlerr.vrmcore.utils.Vector3f;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoneGroupImpl implements BoneGroup {

  @JsonProperty("bones")
  @JsonDeserialize(using = FloatListToIntListDeserializer.class)
  private List<Integer> bones;

  @JsonIgnore
  private List<NodeModel> boneNodes;

  @JsonProperty("colliderGroups")
  @JsonDeserialize(using = FloatListToIntListDeserializer.class)
  private List<Integer> colliderGroups;

  @JsonProperty("center")
  private float center;

  @JsonProperty("comment")
  private String comment;

  @JsonProperty("dragForce")
  private float dragForce;

  @JsonProperty("gravityDir")
  private Vector3f gravityDir;

  @JsonProperty("gravityPower")
  private float gravityPower;

  @JsonProperty("hitRadius")
  private float hitRadius;

  @JsonProperty("stiffiness")
  private float stiffiness;

  public void init(GltfModel model) {
    this.boneNodes = bones.stream().filter(n -> n < model.getNodeModels().size())
        .map(n -> model.getNodeModels().get(n)).collect(
            Collectors.toList());
  }
}
