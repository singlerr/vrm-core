package io.github.singlerr.vrmcore.springbone;

import de.javagl.jgltf.model.NodeModel;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VRMSpringBone {
    private final String comment;
    private final float stiffness;
    private final float gravityPower;
    private final Vector3f gravityDir;
    private final float dragForce;
    private final NodeModel center;
    private final float hitRadius;
    private final List<NodeModel> bones;
    private final List<VRMColliderGroup> colliderGroups;
    private final List<NodeModel> activeBones;
    private final List<SpringBoneLogic> verlets;
    private final boolean drawGizmo = false;

    public VRMSpringBone(String comment, float stiffness, float gravityPower, Vector3f gravityDir,
                         float dragForce, NodeModel center, float hitRadius, List<NodeModel> bones,
                         List<VRMColliderGroup> colliderGroups) {
        this.verlets = new ArrayList<>();
        this.comment = comment;
        this.stiffness = stiffness;
        this.gravityPower = 1.0f;
        this.gravityDir = gravityDir;
        this.dragForce = dragForce;
        this.center = center;
        this.hitRadius = hitRadius;
        this.bones = bones;
        this.colliderGroups = colliderGroups;

        this.activeBones = this.bones.stream().filter(Objects::nonNull).collect(Collectors.toList());
        for (NodeModel activeBone : this.activeBones) {
            verlets.add(new SpringBoneLogic(this.center, this.hitRadius, activeBone));
            for (NodeModel child : activeBone.getChildren()) {
                verlets.add(new SpringBoneLogic(this.center, this.hitRadius, child));
            }
        }

        if (drawGizmo) {
            setupGizmo();
        }
    }

    private void setupGizmo() {
        //TODO
    }

    public void update(float delta) {
        float stiffness = this.stiffness * delta;
        Vector3f external = new Vector3f(gravityDir).mul(this.gravityPower * delta);

        for (SpringBoneLogic verlet : this.verlets) {
            verlet.update(stiffness, this.dragForce, external, this.colliderGroups);
        }
    }
}
