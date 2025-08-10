package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.javagl.jgltf.model.GltfModel;
import io.github.singlerr.vrmcore.VRMExtension;

public interface VRMFactory {

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

    VRMExtension create(GltfModel model);
}
