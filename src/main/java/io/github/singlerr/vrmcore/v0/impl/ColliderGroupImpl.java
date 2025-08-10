package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.Collider;
import io.github.singlerr.vrmcore.ColliderGroup;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ColliderGroupImpl implements ColliderGroup {

    @JsonProperty("colliders")
    @JsonDeserialize(using = ColliderDeserializer.class)
    private List<Collider> colliders;

    @JsonProperty("node")
    @JsonDeserialize(using = FloatToIntDeserializer.class)
    private int node;

    @JsonIgnore
    private NodeModel targetNode;

    public void init(GltfModel model) {
        if (node < model.getNodeModels().size()) {
            targetNode = model.getNodeModels().get(node);
        }
    }
}
