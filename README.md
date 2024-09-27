# VRM Core

---

A simple java wrapper of VRM based on [JglTF](https://github.com/javagl/JglTF). \
It simply inherits and
wraps [GLTFModel](https://github.com/javagl/JglTF/blob/master/jgltf-model/src/main/java/de/javagl/jgltf/model/GltfModel.java),
with adding extra helper methods to it.

There are some features VRMCore provides:

- [ ] Load humanoid
- [ ] Load blend shape groups

and also not implemented currently:

- All features except I mentioned above

-------

# Getting started

### 1. Add maven repository:

``` groovy
    maven {
        url = 'https://github.com/singlerr/mvn-repo/raw/maven2/'
    }
```

### 2. Add VRMCore to your dependencies:

``` groovy
    dependencies {
        implementation 'io.github.singlerr.vrmcore:vrm-core:${vrm_core_version}'
    }
```

For `${vrm_core_version}`, you may look up [maven repo](https://github.com/singlerr/mvn-repo/raw/maven2/) to figure out
which version is available

----

## Get access to VRM extensions

Following code snippets retrieve VRMExtension that is an implementation of VRM

``` java
    GltfModel model = ...;
    VRMLoader loader = new VRMLoader(model);
    VRMExtension vrm = loader.load(); // VRMLoader#load will throw IllegalStateException when it cannot parse vrm json from GltfModel
```


