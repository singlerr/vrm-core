package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.javagl.jgltf.model.AccessorModel;
import de.javagl.jgltf.model.AnimationModel;
import de.javagl.jgltf.model.AssetModel;
import de.javagl.jgltf.model.BufferModel;
import de.javagl.jgltf.model.BufferViewModel;
import de.javagl.jgltf.model.CameraModel;
import de.javagl.jgltf.model.ExtensionsModel;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.ImageModel;
import de.javagl.jgltf.model.MaterialModel;
import de.javagl.jgltf.model.MeshModel;
import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.SceneModel;
import de.javagl.jgltf.model.SkinModel;
import de.javagl.jgltf.model.TextureModel;
import io.github.singlerr.vrmcore.BlendShapeGroup;
import io.github.singlerr.vrmcore.Humanoid;
import io.github.singlerr.vrmcore.VRMExtension;
import io.github.singlerr.vrmcore.VRMLoader;
import java.util.List;
import java.util.Map;
import lombok.Getter;


class VRM implements VRMExtension {

  @JsonIgnore
  private static final JsonMapper MAPPER = new JsonMapper();

  static {
    VRMLoader.setFactory(model -> {
      try {
        VRM vrm = create(model);
        vrm.init(model);
        return vrm;
      } catch (JsonProcessingException e) {
        return null;
      }
    });
  }

  private static VRM create(GltfModel model) throws JsonProcessingException {
    String json = MAPPER.writeValueAsString(model.getExtensions());
    return MAPPER.readValue(json, VRM.class);
  }

  @JsonIgnore
  private GltfModel base;

  @JsonProperty("blendShapeGroup")
  @JsonDeserialize(using = BlendShapeGroupDeserializer.class)
  @Getter
  private List<BlendShapeGroup> blendShapeGroups;

  @JsonProperty("humanoid")
  @Getter
  private Humanoid humanoid;

  public void init(GltfModel base) {
    this.base = base;
  }

  @Override
  public List<AccessorModel> getAccessorModels() {
    return base.getAccessorModels();
  }

  @Override
  public List<AnimationModel> getAnimationModels() {
    return base.getAnimationModels();
  }

  @Override
  public List<BufferModel> getBufferModels() {
    return base.getBufferModels();
  }

  @Override
  public List<BufferViewModel> getBufferViewModels() {
    return base.getBufferViewModels();
  }

  @Override
  public List<CameraModel> getCameraModels() {
    return base.getCameraModels();
  }

  @Override
  public List<ImageModel> getImageModels() {
    return base.getImageModels();
  }

  @Override
  public List<MaterialModel> getMaterialModels() {
    return base.getMaterialModels();
  }

  @Override
  public List<MeshModel> getMeshModels() {
    return base.getMeshModels();
  }

  @Override
  public List<NodeModel> getNodeModels() {
    return base.getNodeModels();
  }

  @Override
  public List<SceneModel> getSceneModels() {
    return base.getSceneModels();
  }

  @Override
  public List<SkinModel> getSkinModels() {
    return base.getSkinModels();
  }

  @Override
  public List<TextureModel> getTextureModels() {
    return base.getTextureModels();
  }

  @Override
  public ExtensionsModel getExtensionsModel() {
    return base.getExtensionsModel();
  }

  @Override
  public AssetModel getAssetModel() {
    return base.getAssetModel();
  }

  @Override
  public Map<String, Object> getExtensions() {
    return base.getExtensions();
  }

  @Override
  public Object getExtras() {
    return base.getExtras();
  }

}
