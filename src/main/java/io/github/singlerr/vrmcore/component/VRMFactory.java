package io.github.singlerr.vrmcore.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.javagl.jgltf.model.GltfModel;
import io.github.singlerr.vrmcore.VRMExtension;

public interface VRMFactory {

  VRMExtension create(GltfModel model);

  static VRMFactory getDefault() {
    return new VRMFactory() {
      @Override
      public VRMExtension create(GltfModel model) {
        try {
          VRM vrm = VRM.create(model);
          vrm.init(model);
          return vrm;
        } catch (JsonProcessingException e) {
          return null;
        }
      }
    };
  }
}
