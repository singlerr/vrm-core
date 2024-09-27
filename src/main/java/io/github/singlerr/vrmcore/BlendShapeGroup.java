package io.github.singlerr.vrmcore;

import java.util.List;

public interface BlendShapeGroup {

  List<BlendShapeBinding> getBindings();

  boolean isBinary();

  String getName();

  String getPresetName();
}
