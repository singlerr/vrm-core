package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.NodeModel;

public interface BlendShapeBinding {

  int getIndex();

  int getNode();

  float getWeight();

  NodeModel getTargetNode();
}
