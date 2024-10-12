package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.NodeModel;
import io.github.singlerr.vrmcore.utils.Vector3f;
import java.util.List;

public interface BoneGroup {

  List<Integer> getBones();

  List<NodeModel> getBoneNodes();

  List<Integer> getColliderGroups();

  int getCenter();

  NodeModel getCenterNode();

  float getDragForce();

  String getComment();

  Vector3f getGravityDir();

  float getGravityPower();

  float getHitRadius();

  float getStiffiness();

}
