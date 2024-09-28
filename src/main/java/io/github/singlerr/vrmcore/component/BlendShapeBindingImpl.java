package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.BlendShapeBinding;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Data
@Getter
class BlendShapeBindingImpl implements BlendShapeBinding {

  @JsonProperty("mesh")
  @JsonDeserialize(using = FloatToIntDeserializer.class)
  private int index;
  @JsonProperty("index")
  @JsonDeserialize(using = FloatToIntDeserializer.class)
  private int node;
  @JsonProperty("weight")
  private float weight;

  @Nullable
  private NodeModel targetNode;

  public void init(GltfModel model) {
    this.targetNode = model.getNodeModels().get(node);
  }
}
