package io.github.singlerr.vrmcore.springbone;

import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.utils.MathUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Supplier;

public class SpringBoneLogic {
    private static final Matrix4f IDENTITY = new Matrix4f().identity();
    private static final Vector3f _v3A = new Vector3f();
    private static final Vector3f _v3B = new Vector3f();
    private static final Vector3f _v3C = new Vector3f();
    private static final Quaternionf _quatA = new Quaternionf();
    private static final Matrix4f _matA = new Matrix4f();
    private static final Matrix4f _matB = new Matrix4f();

    private final Matrix4f initialLocalMatrix;
    private final Quaternionf initialLocalRotation;
    private final Vector3f initialLocalChildPosition;
    private final Vector3f centerSpacePosition;
    private final Vector3f boneAxis;
    private final Vector3f currentTail;
    private final NodeModel center;
    private final float radius;
    private final NodeModel transform;
    private final Vector3f prevTail;
    private final Vector3f nextTail;
    private final float x;
    private float centerSpaceBoneLength;

    public SpringBoneLogic(NodeModel center, float radius, NodeModel transform) {
        this.center = center;
        this.radius = radius;
        this.transform = transform;

        Matrix4f worldMatrix = MathUtils.getMatrix(transform.computeGlobalTransform(null));
        Vector3f pos = new Vector3f();
        worldMatrix.getTranslation(pos);

        this.centerSpacePosition = pos;
        this.initialLocalMatrix = MathUtils.getMatrix(transform.computeLocalTransform(null));
        this.initialLocalRotation = getRotation(worldMatrix);

        List<NodeModel> children = transform.getChildren();
        if (children.isEmpty()) {
            this.initialLocalChildPosition =
                    getTranslation(transform.computeGlobalTransform(null)).normalize().mul(0.07f);
        } else {
            this.initialLocalChildPosition =
                    new Vector3f(getTranslation(children.get(0).computeGlobalTransform(null)));
        }

        this.currentTail = new Vector3f();
        this.prevTail = new Vector3f();
        this.nextTail = new Vector3f();
        MathUtils.transformToWorldSpace(this.initialLocalChildPosition, worldMatrix, this.currentTail);
        this.prevTail.set(this.currentTail);
        this.nextTail.set(this.currentTail);

        this.boneAxis = new Vector3f();
        this.initialLocalChildPosition.normalize(this.boneAxis);
        MathUtils.transformToWorldSpace(this.initialLocalChildPosition, worldMatrix, _v3A);
        this.centerSpaceBoneLength = _v3A.sub(this.centerSpacePosition).length();

        if (center != null) {
            this.getMatrixWorldToCenter(_matA);

            MathUtils.transformToWorldSpace(this.currentTail, _matA, this.currentTail);
            MathUtils.transformToWorldSpace(this.prevTail, _matA, this.prevTail);
            MathUtils.transformToWorldSpace(this.nextTail, _matA, this.nextTail);

            worldMatrix.mul(_matA, _matA);
            _matA.getTranslation(this.centerSpacePosition);

            MathUtils.transformToWorldSpace(this.initialLocalChildPosition, _matA, _v3A);
            this.centerSpaceBoneLength = _v3A.sub(this.centerSpacePosition).length();
        }

        x = (float) Math.random();
    }

    private Quaternionf getRotation(Matrix4f worldMatrix) {
        Quaternionf r = new Quaternionf();
        worldMatrix.getUnnormalizedRotation(r);
        return r;
    }

    private Vector3f getTranslation(float[] global) {
        Matrix4f matrix = MathUtils.getMatrix(global);
        Vector3f v = new Vector3f();
        matrix.getTranslation(v);
        return v;
    }

    public void update(float stiffness, float dragForce, Vector3f external,
                       List<VRMColliderGroup> colliderGroups) {
        if (Float.isNaN(getTranslation(this.transform.computeGlobalTransform(null)).x())) {
            return;
        }
        Supplier<float[]> worldMatrixSupplier = this.transform.createGlobalTransformSupplier();

        getMatrixWorldToCenter(_matA);
        MathUtils.getMatrix(worldMatrixSupplier.get()).mul(_matA, _matA);
        _matA.getTranslation(this.centerSpacePosition);

        this.getMatrixWorldToCenter(_matB);
        this.getParentMatrixWorld().mul(_matB, _matB);

        //Verlet
        this.nextTail.set(this.currentTail);
        {
            _v3A.set(this.currentTail)
                    .sub(this.prevTail)
                    .mul(1.0f - dragForce);
            this.nextTail.add(_v3A);
        }
        {
            _v3A.set(this.boneAxis);
            MathUtils.transformToWorldSpace(_v3A, this.initialLocalMatrix, _v3A);
            MathUtils.transformToWorldSpace(_v3A, _matB, _v3A);
            _v3A.sub(this.centerSpacePosition).normalize().mul(stiffness);
            this.nextTail.add(_v3A);
        }
        {
            this.nextTail.add(external);
        }
        {
            this.nextTail.sub(this.centerSpacePosition).normalize().mul(this.centerSpaceBoneLength)
                    .add(this.centerSpacePosition);
        }
        {
            collide(colliderGroups, this.nextTail);
        }

        this.prevTail.set(this.currentTail);
        this.currentTail.set(this.nextTail);

        this.initialLocalMatrix.mul(_matB, _matA);
        Matrix4f initialCenterSpaceMatrixInv = _matA.invert();
        MathUtils.transformToWorldSpace(this.nextTail, initialCenterSpaceMatrixInv, _v3A);
        _v3A.normalize(_v3B);
        _quatA.rotateTo(this.boneAxis, _v3B);
        Quaternionf t = new Quaternionf();
        MathUtils.getMatrix(worldMatrixSupplier.get()).getUnnormalizedRotation(t);
        this.initialLocalRotation.mul(_quatA, t);
        this.transform.setRotation(new float[]{t.x, t.y, t.z, t.w});
    }

    private void updateNodeModel(NodeModel model) {
        Matrix4f transform = MathUtils.getMatrix(this.transform.computeLocalTransform(null));
        Vector3f translation = new Vector3f();
        Vector3f scale = new Vector3f();
        transform.getTranslation(translation);
        transform.getScale(scale);
        model.setTranslation(new float[]{translation.x, translation.y, translation.z});
        model.setScale(new float[]{scale.x, scale.y, scale.z});
    }

    private void collide(List<VRMColliderGroup> colliderGroups, Vector3f tail) {
        for (VRMColliderGroup colliderGroup : colliderGroups) {
            for (VRMCollider collider : colliderGroup.getColliders()) {
                getMatrixWorldToCenter(_matA);
                MathUtils.getMatrix(collider.getSphere().computeGlobalTransform(null)).mul(_matA, _matA);
                _matA.getTranslation(_v3A);
                Vector3f colliderCenterSpacePosition = _v3A;
                Vector3f scale = new Vector3f();
                MathUtils.getMatrix(collider.getSphere().computeGlobalTransform(null)).getScale(scale);
                float maxAbsScale = Math.max(0, Math.abs(scale.x));
                maxAbsScale = Math.max(maxAbsScale, Math.abs(scale.y));
                maxAbsScale = Math.max(maxAbsScale, Math.abs(scale.z));
                float colliderRadius = collider.getRadius() * maxAbsScale;
                float r = this.radius + colliderRadius;

                tail.sub(colliderCenterSpacePosition, _v3B);
                if (_v3B.lengthSquared() <= r * r) {
                    Vector3f normal = _v3B.set(tail).sub(colliderCenterSpacePosition).normalize();
                    Vector3f posFromCollider = _v3C.set(colliderCenterSpacePosition).add(normal.mul(r));
                    posFromCollider.sub(this.centerSpacePosition).normalize().mul(this.centerSpaceBoneLength)
                            .add(this.centerSpacePosition);
                }
            }
        }
    }

    private Matrix4f getParentMatrixWorld() {
        return this.transform.getParent() != null ?
                MathUtils.getMatrix(this.transform.getParent().computeGlobalTransform(null)) : IDENTITY;
    }

    private Matrix4f getMatrixWorldToCenter(Matrix4f target) {
        if (center != null) {
            MathUtils.getMatrix(center.computeLocalTransform(null)).invert(target);
        } else {
            target.set(IDENTITY);
        }

        return target;
    }

    private Quaternionf getQuaternion(float[] quat) {
        if (quat == null) {
            return new Quaternionf();
        }
        return new Quaternionf(quat[0], quat[1], quat[2], quat[3]);
    }


}
