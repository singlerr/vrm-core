package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.NodeModel;

public interface HumanBone {

    String getName();

    int getNode();

    boolean useDefaultValues();

    NodeModel getTargetNode();
}
