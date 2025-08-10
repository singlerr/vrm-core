package io.github.singlerr.vrmcore.springbone;

import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.Animations;
import io.github.singlerr.vrmcore.BoneGroup;
import io.github.singlerr.vrmcore.Collider;
import io.github.singlerr.vrmcore.ColliderGroup;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpringBoneController {

    private final List<VRMSpringBone> springs;

    public SpringBoneController(Animations ext) {
        List<VRMColliderGroup> colliderGroups = constructColliderGroups(ext.getColliderGroups());
        this.springs = constructSprings(ext.getBoneGroups(), colliderGroups);
    }

    public void update(float delta) {
        for (VRMSpringBone spring : this.springs) {
            spring.update(delta);
        }
    }

    private List<VRMColliderGroup> constructColliderGroups(List<ColliderGroup> colliderGroups) {
        List<VRMColliderGroup> groups = new ArrayList<>();
        for (ColliderGroup colliderGroup : colliderGroups) {
            NodeModel bone = colliderGroup.getTargetNode();
            VRMColliderGroup g = new VRMColliderGroup(bone);
            for (Collider collider : colliderGroup.getColliders()) {
                g.addCollider(new Vector3f(-collider.getOffset().getX(), collider.getOffset().getY(),
                        -collider.getOffset().getZ()), collider.getRadius());
            }

            groups.add(g);
        }

        return groups;
    }

    private List<VRMSpringBone> constructSprings(List<BoneGroup> boneGroups,
                                                 List<VRMColliderGroup> colliderGroups) {
        List<VRMSpringBone> springs = new ArrayList<>();
        for (BoneGroup boneGroup : boneGroups) {
            List<NodeModel> rootBones = boneGroup.getBoneNodes();
            List<VRMColliderGroup> springColliders = boneGroup.getColliderGroups().stream().map(
                    colliderGroups::get).collect(Collectors.toList());
            springs.add(new VRMSpringBone(boneGroup.getComment(), boneGroup.getStiffiness(),
                    boneGroup.getGravityPower(),
                    new Vector3f(-boneGroup.getGravityDir().getX(), boneGroup.getGravityDir().getY(),
                            -boneGroup.getGravityDir().getZ()).normalize(), boneGroup.getDragForce(),
                    boneGroup.getCenterNode(), boneGroup.getHitRadius(), rootBones, springColliders));
        }

        return springs;
    }

}
