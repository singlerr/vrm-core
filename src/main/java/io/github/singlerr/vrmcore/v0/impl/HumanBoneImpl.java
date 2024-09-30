package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.HumanBone;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class HumanBoneImpl implements HumanBone {

  @JsonProperty("bone")
  private String name;
  @JsonProperty("node")
  @JsonDeserialize(using = FloatToIntDeserializer.class)
  private int node;

  @JsonProperty("useDefaultValues")
  @Accessors(fluent = true)
  private boolean useDefaultValues;

  @JsonIgnore
  private NodeModel targetNode;

  public void init(GltfModel model) {
    if (node >= model.getNodeModels().size()) {
      return;
    }
    this.targetNode = model.getNodeModels().get(node);
  }
}
