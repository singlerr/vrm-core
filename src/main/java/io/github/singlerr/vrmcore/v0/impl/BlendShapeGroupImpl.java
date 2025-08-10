package io.github.singlerr.vrmcore.v0.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.singlerr.vrmcore.BlendShapeBinding;
import io.github.singlerr.vrmcore.BlendShapeGroup;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class BlendShapeGroupImpl implements BlendShapeGroup {

    @JsonProperty("binds")
    @JsonDeserialize(using = BlendShapeBindingDeserializer.class)
    private List<BlendShapeBinding> bindings;

    @JsonProperty("isBinary")
    private boolean binary;

    @JsonProperty("name")
    private String name;

    @JsonProperty("presetName")
    private String presetName;


}
