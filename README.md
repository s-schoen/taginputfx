# TagInputFx
A simple Tag input control for JavaFx.

![tag-input](docs/images/tag-input.png)

### Usage
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