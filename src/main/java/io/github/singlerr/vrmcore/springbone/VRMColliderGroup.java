package io.github.singlerr.vrmcore.springbone;

import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.impl.DefaultNodeModel;
import io.github.singlerr.vrmcore.utils.SphereBuilder;
import lombok.Getter;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VRMColliderGroup {

    private final NodeModel transform;
    private final List<VRMCollider> colliders;

    public VRMColliderGroup(NodeModel transform) {
        this.transform = transform;
        this.colliders = new ArrayList<>();
    }

    public void addCollider(Vector3f offset, float radius) {
        DefaultNodeModel sphere = (DefaultNodeModel) SphereBuilder.createSphere(radius);
        sphere.setParent((DefaultNodeModel) transform);
        sphere.setTranslation(new float[]{offset.x, offset.y, offset.z});

        this.colliders.add(new VRMCollider(offset, radius, sphere));
    }

}
