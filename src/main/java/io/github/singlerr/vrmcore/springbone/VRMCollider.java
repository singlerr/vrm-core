package io.github.singlerr.vrmcore.springbone;

import de.javagl.jgltf.model.NodeModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.joml.Vector3f;

@Data
@RequiredArgsConstructor
public class VRMCollider {

    private final Vector3f offset;
    private final float radius;
    private final NodeModel sphere;
}
