package io.github.singlerr.vrmcore.utils;

import lombok.experimental.UtilityClass;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@UtilityClass
public class MathUtils {

    public void transformToWorldSpace(Vector3f target, Matrix4f worldMatrix, Vector3f result) {
        float rx = target.x() * worldMatrix.get(0, 0) + target.y() * worldMatrix.get(0, 1) +
                target.z() * worldMatrix.get(0, 2) + worldMatrix.get(0, 3);
        float ry = target.x() * worldMatrix.get(1, 0) + target.y() * worldMatrix.get(1, 1) +
                target.z() * worldMatrix.get(1, 2) + worldMatrix.get(1, 3);
        float rz = target.x() * worldMatrix.get(2, 0) + target.y() * worldMatrix.get(2, 1) +
                target.z() * worldMatrix.get(2, 2) + worldMatrix.get(2, 3);
        float rw = 1 / (target.x() * worldMatrix.get(3, 0) + target.y() * worldMatrix.get(3, 1) +
                target.z() * worldMatrix.get(3, 2) + worldMatrix.get(3, 3));

        result.x = rx * rw;
        result.y = ry * rw;
        result.z = rz * rw;
    }

    // See https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
    public Quaternionf toQuaternion(float xRot, float yRot, float zRot) {
        double cr = cos(xRot * 0.5);
        double sr = sin(xRot * 0.5);
        double cp = cos(yRot * 0.5);
        double sp = sin(yRot * 0.5);
        double cy = cos(zRot * 0.5);
        double sy = sin(zRot * 0.5);

        return new Quaternionf(sr * cp * cy - cr * sp * sy, cr * sp * cy + sr * cp * sy,
                cr * cp * sy - sr * sp * cy, cr * cp * cy + sr * sp * sy);
    }

    public Matrix4f getMatrix(float[] matrix) {
        return new Matrix4f(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5], matrix[6],
                matrix[7], matrix[8], matrix[9], matrix[10], matrix[11], matrix[12], matrix[13], matrix[14],
                matrix[15]);
    }
}
