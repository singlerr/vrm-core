package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.singlerr.vrmcore.BlendShapeGroup;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class BlendShapeMaster {

  @JsonProperty("blendShapeGroups")
  @JsonDeserialize(using = BlendShapeGroupDeserializer.class)
  private List<BlendShapeGroup> blendShapeGroups;

}
