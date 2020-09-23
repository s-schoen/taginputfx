/*
 * MIT License
 *
 * Copyright (c) 2020 Steffen S.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated ocumentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.steffen1995.taginputfx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A controls that allows editing a list of tags.
 * @author Steffen Schoen
 */
public class TagInput extends HBox {
  private final ObservableList<String> selectedTags;
  private ObservableList<String> availableTags;

  private final BooleanProperty allowNewTags;
  private final StringProperty inputHint;

  private final SuggestionTextField tagInputField;

  /**
   * Constructor
   */
  public TagInput() {
    super(5);

    getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    getStyleClass().add("tag-input");
    selectedTags = FXCollections.observableArrayList();
    availableTags = FXCollections.observableArrayList();

    allowNewTags = new SimpleBooleanProperty(true);
    inputHint = new SimpleStringProperty("Enter Tag...");

    tagInputField = new SuggestionTextField();
    tagInputField.setSuggestions(availableTags);
    tagInputField.setOnAction(e -> {
      String tag = tagInputField.getText();
      if (!tag.isEmpty() && !selectedTags.contains(tag) && (allowNewTags.get() || availableTags.contains(tag))) {
        selectedTags.add(tag);
        tagInputField.clear();
      }
    });
    tagInputField.prefHeightProperty().bind(this.heightProperty());
    HBox.setHgrow(tagInputField, Priority.ALWAYS);
    tagInputField.setBackground(null);
    tagInputField.setPromptText(inputHint.get());

    selectedTags.addListener((ListChangeListener.Change<? extends String> change) -> {
      while (change.next()) {
        if (change.wasPermutated()) {
          ArrayList<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
          for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
            newSublist.add(null);
          }
          for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
            newSublist.set(change.getPermutation(i), getChildren().get(i));
          }
          getChildren().subList(change.getFrom(), change.getTo()).clear();
          getChildren().addAll(change.getFrom(), newSublist);
        }else if (change.wasRemoved()) {
          getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
        }else if (change.wasAdded()) {
          getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
        }
      }
    });

    getChildren().add(tagInputField);
  }

  /**
   * Sets the given list of strings to be selected tags
   * @param tags the selected tags
   */
  public void setSelectedTags(ObservableList<String> tags) {
    selectedTags.setAll(tags);
  }

  /**
   * Get selected tags
   * @return the selected tags
   */
  public ObservableList<String> getSelectedTags() {
    return selectedTags;
  }

  /**
   * Get available tags (tag suggestions)
   * @return the available tags
   */
  public ObservableList<String> getAvailableTags() {
    return availableTags;
  }

  /**
   * Sets the available tags for the input field
   * @param availableTags the list of tags to suggest when typing
   */
  public void setAvailableTags(ObservableList<String> availableTags) {
    this.availableTags = availableTags;
    tagInputField.setSuggestions(availableTags);
  }

  /**
   * Gets whether creation of tags that are not part of the available tags is allowed
   * @return {@code true} if creation of new tags is allowed, {@code false} otherwise
   */
  public boolean newTagsAllowed() {
    return allowNewTags.get();
  }

  /**
   * Sets whether creation of tags that are not part of the available tags is allowed
   * @param allowNewTags {@code true} to allow creation of new tags, {@code false} otherwise
   */
  public void setAllowNewTags(boolean allowNewTags) {
    this.allowNewTags.set(allowNewTags);
  }

  /**
   * Property whether the creation of new tags is allowed
   * @return allow new tags property
   */
  public BooleanProperty allowNewTagsProperty() {
    return allowNewTags;
  }

  /**
   * Gets the prompt text for the tag input
   * @return the prompt text
   */
  public String getInputHint() {
    return inputHint.get();
  }

  /**
   * Sets the prompt text for the tag input
   * @param inputHint the prompt text
   */
  public void setInputHint(String inputHint) {
    this.inputHint.set(inputHint);
  }

  /**
   * Property for the tag input prompt text
   * @return the prompt text property
   */
  public StringProperty inputHintProperty() {
    return inputHint;
  }

  /**
   * Visual representation of a selected Tag
   */
  private class Tag extends HBox {
    /**
     * Constructor
     * @param text the tag text
     */
    public Tag(String text) {
      getStyleClass().setAll("tag");
      ImageView removeIcon = new ImageView(getClass().getResource("/cross.png").toExternalForm());
      removeIcon.setFitHeight(8);
      removeIcon.setFitWidth(8);

      Button removeButton = new Button();
      removeButton.setOnAction(e -> selectedTags.remove(text));
      removeButton.setGraphic(removeIcon);

      Text tagText = new Text(text);

      HBox.setMargin(tagText, new Insets(0, 0, 0, 10));
      getChildren().addAll(tagText, removeButton);
    }
  }
}
