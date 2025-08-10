package io.github.singlerr.vrmcore.utils;

import de.javagl.jgltf.model.*;
import de.javagl.jgltf.model.impl.DefaultAccessorModel;
import de.javagl.jgltf.model.impl.DefaultMeshModel;
import de.javagl.jgltf.model.impl.DefaultMeshPrimitiveModel;
import de.javagl.jgltf.model.impl.DefaultNodeModel;
import lombok.experimental.UtilityClass;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.ByteBuffer;

@UtilityClass
public class SphereBuilder {

    private static final int INTEGER = 0x1404;
    private static final int FLOAT = 0x1406;
    private static final int TRIANGLES = 0x4;

    public NodeModel createSphere(float radius) {
        DefaultNodeModel sphereNode = new DefaultNodeModel();
        DefaultMeshModel sphereMesh = new DefaultMeshModel();

        int segments = 6;
        float diameter = radius * 2.0f;

        float diameterX = diameter;
        float diameterY = diameter;
        float diameterZ = diameter;

        float arc = 1.0f;
        float slice = 1.0f;

        int totalZRotationSteps = 2 + segments;
        int totalYRotationSteps = 2 * totalZRotationSteps;

        ByteBuffer indices =
                ByteBuffer.allocate((totalZRotationSteps * totalYRotationSteps * 6) * Integer.BYTES);
        ByteBuffer positions = ByteBuffer.allocate(
                ((totalZRotationSteps + 1) * (totalYRotationSteps + 1) * 3) * Float.BYTES);
        ByteBuffer normals = ByteBuffer.allocate(
                ((totalZRotationSteps + 1) * (totalYRotationSteps + 1) * 3) * Float.BYTES);
        ByteBuffer uvs = ByteBuffer.allocate(
                ((totalZRotationSteps + 1) * (totalYRotationSteps + 1) * 2) * Float.BYTES);

        for (int zRotationStep = 0; zRotationStep <= totalZRotationSteps; zRotationStep++) {
            float normalizedZ = (float) zRotationStep / totalZRotationSteps;
            float angleZ = normalizedZ * (float) Math.PI * 2 * arc;
            for (int yRotationStep = 0; yRotationStep <= totalYRotationSteps; yRotationStep++) {
                float normalizedY = (float) yRotationStep / totalYRotationSteps;
                float angleY = normalizedY * (float) Math.PI * 2 * arc;
                Matrix4f rotationZ = new Matrix4f().rotationZ(-angleZ);
                Matrix4f rotationY = new Matrix4f().rotationY(angleY);
                Vector3f afterRotZ = new Vector3f();
                MathUtils.transformToWorldSpace(new Vector3f(0f, 1f, 0f), rotationZ, afterRotZ);
                Vector3f complete = new Vector3f();
                MathUtils.transformToWorldSpace(afterRotZ, rotationY, complete);
                Vector3f vertex = complete.mul(radius);
                Vector3f normal = complete.div(radius).normalize();
                positions.putFloat(vertex.x);
                positions.putFloat(vertex.y);
                positions.putFloat(vertex.z);
                normals.putFloat(normal.x);
                normals.putFloat(normal.y);
                normals.putFloat(normal.z);
                uvs.putFloat(normalizedY);
                uvs.putFloat(1.0f - normalizedZ);
            }

            if (zRotationStep > 0) {
                int verticesCount = positions.capacity() / 3;
                for (int firstIndex = verticesCount - 2 * (totalYRotationSteps + 1);
                     firstIndex + totalYRotationSteps + 2 < verticesCount; firstIndex++) {
                    indices.putInt(firstIndex);
                    indices.putInt(firstIndex + 1);
                    indices.putInt(firstIndex + totalYRotationSteps + 1);
                    indices.putInt(firstIndex + totalYRotationSteps + 1);
                    indices.putInt(firstIndex + 1);
                    indices.putInt(firstIndex + totalYRotationSteps + 2);
                }
            }
        }

        DefaultMeshPrimitiveModel primitiveModel = new DefaultMeshPrimitiveModel(TRIANGLES);
        primitiveModel.putAttribute("POSITION", createVec3Float(positions));
        primitiveModel.putAttribute("NORMAL", createVec3Float(normals));
        primitiveModel.setIndices(createIndices(indices));
        sphereMesh.addMeshPrimitiveModel(primitiveModel);
        sphereNode.addMeshModel(sphereMesh);
        return sphereNode;
    }

    private AccessorModel createIndices(ByteBuffer data) {
        DefaultAccessorModel accessorModel =
                new DefaultAccessorModel(INTEGER, data.capacity(), ElementType.SCALAR);
        accessorModel.setByteOffset(0);
        accessorModel.setAccessorData(
                new AccessorIntData(INTEGER, data, 0, data.capacity() / (Integer.BYTES),
                        ElementType.SCALAR, accessorModel.getByteStride()));
        return accessorModel;
    }

    private AccessorModel createVec3Float(ByteBuffer data) {
        DefaultAccessorModel accessorModel =
                new DefaultAccessorModel(FLOAT, data.capacity(), ElementType.VEC3);
        accessorModel.setByteOffset(0);
        accessorModel.setAccessorData(
                new AccessorFloatData(FLOAT, data, 0, data.capacity() / (Float.BYTES * 3),
                        ElementType.VEC3, accessorModel.getByteStride()));
        return accessorModel;
    }
}
