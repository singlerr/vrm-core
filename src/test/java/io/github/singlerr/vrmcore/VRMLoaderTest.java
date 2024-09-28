package io.github.singlerr.vrmcore;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelReader;
import io.github.singlerr.vrmcore.component.VRMFactory;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VRMLoaderTest {

  private static VRMExtension vrm;

  @Test
  @Order(1)
  void loadModel() throws IOException {
    String path = System.getenv("model_path");
    GltfModelReader reader = new GltfModelReader();
    GltfModel model = reader.read(Paths.get(path));

    VRMLoader loader = new VRMLoader(model);
    loader.setFactory(VRMFactory.getDefault());
    vrm = loader.load();
  }

  @Test
  @Order(2)
  void validateModel() {
    assertNotNull(vrm.getBlendShapeGroups());
    assertNotNull(vrm.getHumanoid());
  }

  @Test
  @Order(3)
  void validateModelInitialization() {
    for (BlendShapeGroup g : vrm.getBlendShapeGroups()) {
      for (BlendShapeBinding binding : g.getBindings()) {
        assertNotNull(binding.getTargetNode());
      }
    }
  }
}