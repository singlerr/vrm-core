package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.GltfModel;
import io.github.singlerr.vrmcore.v0.impl.VRMFactory;
import lombok.Setter;

public class VRMLoader {

  private final GltfModel model;
  @Setter
  private VRMFactory factory;

  public VRMLoader(GltfModel model) {
    this.model = model;
  }


  public VRMExtension load() throws IllegalStateException {
    if (factory == null) {
      throw new IllegalStateException("VRMLoader is not initialized!");
    }

    return factory.create(model);
  }
}
