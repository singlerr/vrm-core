package io.github.singlerr.vrmcore;

import de.javagl.jgltf.model.GltfModel;
import java.util.List;

public interface VRMExtension extends GltfModel {

  List<BlendShapeGroup> getBlendShapeGroups();

  Humanoid getHumanoid();


}
