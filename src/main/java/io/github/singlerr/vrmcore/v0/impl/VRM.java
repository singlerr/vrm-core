package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.javagl.jgltf.model.*;
import io.github.singlerr.vrmcore.*;
import io.github.singlerr.vrmcore.utils.Validate;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class VRM implements VRMExtension {

    @JsonIgnore
    private static final JsonMapper MAPPER = new JsonMapper();

    @JsonProperty("blendShapeMaster")
    BlendShapeMaster blendShapes;

    @JsonIgnore
    private GltfModel base;

    @JsonProperty("humanoid")
    @JsonDeserialize(using = HumanoidDeserializer.class)
    private Humanoid humanoid;

    @JsonProperty("secondaryAnimation")
    @JsonDeserialize(using = AnimationsDeserializer.class)
    private Animations animations;

    public static VRM create(GltfModel model) throws JsonProcessingException {
        Map<String, Object> root =
                (Map<String, Object>) Validate.getOrThrow(model.getExtensions(), "VRM");

        return MAPPER.convertValue(root, VRM.class);
    }

    public void init(GltfModel base) {
        this.base = base;
        getBlendShapeGroups().forEach(g -> initBlendShapeGroup(g, base));
        getHumanoid().getBones().forEach(b -> initHumanBone(b, base));
        getAnimations().getBoneGroups().forEach(g -> initBoneGroup(g, base));
        getAnimations().getColliderGroups().forEach(g -> initColliderGroup(g, base));
    }


    private void initColliderGroup(ColliderGroup group, GltfModel model) {
        ((ColliderGroupImpl) group).init(model);
    }

    private void initBoneGroup(BoneGroup group, GltfModel model) {
        ((BoneGroupImpl) group).init(model);
    }

    private void initBlendShapeGroup(BlendShapeGroup group, GltfModel model) {
        group.getBindings().forEach(b -> initBlendShapeBindings(b, model));
    }

    private void initBlendShapeBindings(BlendShapeBinding binding, GltfModel model) {
        ((BlendShapeBindingImpl) binding).init(model);
    }

    private void initHumanBone(HumanBone bone, GltfModel model) {
        ((HumanBoneImpl) bone).init(model);
    }

    @Override
    public List<BlendShapeGroup> getBlendShapeGroups() {
        return blendShapes.getBlendShapeGroups();
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
