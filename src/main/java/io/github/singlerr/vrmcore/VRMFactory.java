package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.GltfModel;

public interface VRMFactory {

  VRMExtension create(GltfModel model);

}
