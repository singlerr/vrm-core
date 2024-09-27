package io.github.singlerr.vrmcore;

import java.util.List;

public interface Humanoid {

  float getArmStretch();

  float getLegStretch();

  float getLowerArmTwist();

  float getLowerLegTwist();

  float getUpperArmTwist();

  float getUpperLegTwist();

  float getFeetSpacing();

  boolean hasTranslationDoF();

  List<HumanBone> getBones();
}
