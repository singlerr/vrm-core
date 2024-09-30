package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.NodeModel;
import java.util.List;

public interface ColliderGroup {

  int getNode();

  NodeModel getTargetNode();

  List<Collider> getColliders();

}
