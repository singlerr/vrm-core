package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.GltfModel;

public class VRMLoader {

  private static VRMFactory factory;

  public static void setFactory(VRMFactory factory) {
    if (VRMLoader.factory != null) {
      throw new IllegalStateException("Cannot assign factory twice!");
    }
    VRMLoader.factory = factory;
  }

  private final GltfModel model;

  public VRMLoader(GltfModel model) {
    this.model = model;
  }

  public VRMExtension load() throws IllegalStateException {
    if (VRMLoader.factory == null) {
      throw new IllegalStateException("VRMLoader is not initialized!");
    }

    return VRMLoader.factory.create(model);
  }
}
