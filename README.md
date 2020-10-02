# TagInputFx
[ ![Download](https://api.bintray.com/packages/sschoen/JavaLibs/TagInputFx/images/download.svg?version=1.0.0) ](https://bintray.com/sschoen/JavaLibs/TagInputFx/1.0.0/link)

A simple Tag input control for JavaFx.

![tag-input](docs/images/tag-input.png)

License: MIT

### Usage
Declare the repository and add build dependency
```groovy
repositories {
    maven{
        url 'https://dl.bintray.com/sschoen/JavaLibs'
    }
}

...

dependencies {
    implementation 'com.gmail.steffen1995:taginputfx:1.0.0'
}
```

Use the control
```java
import com.gmail.steffen1995.taginputfx.TagInput;

// create a new TagInput
TagInput tagInput = new TagInput();

// optionally, set available tags for suggestions when typing
tagInput.setAvailableTags(myTags);

// selected tags can be specified the following way
tagInput.setSelectedTags(initialTags);

// by default, new tags can be created, if you only want selection from the available tags use
tagInput.setAllowNewTags(false);
```