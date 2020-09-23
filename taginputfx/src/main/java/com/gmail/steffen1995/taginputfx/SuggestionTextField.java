package com.gmail.steffen1995.taginputfx;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * A {@link TextField} that allows selection from a list of suggestions based on the current input.
 * @author Steffen Schoen
 */
public class SuggestionTextField extends TextField {
  private static final int MAX_SUGGESTIONS = 10;

  private final SortedSet<String> suggestions;
  private final ContextMenu suggestionMenu;

  public SuggestionTextField() {
    super();

    suggestions = new TreeSet<>();
    suggestionMenu = new ContextMenu();

    setupSuggestionListener();
  }

  /**
   * Setup listeners that updates the suggestion menu based on the current input
   */
  private void setupSuggestionListener() {
    // update suggestions when text changes
    textProperty().addListener((observable, oldValue, newValue) -> {
      String enteredText = getText();
      if (enteredText == null || enteredText.isEmpty()) {
        // no text -> hide suggestions menu
        suggestionMenu.hide();
      } else {
        //filter all possible suggestions depend in on current text
        List<String> filteredEntries = suggestions
                .stream()
                .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                .collect(Collectors.toList());

        if (!filteredEntries.isEmpty()) {
          // matching suggestions -> update menu with suggestions
          populateSuggestionsMenu(filteredEntries, enteredText);

          // show menu if not already visible
          if (!suggestionMenu.isShowing()) {
            suggestionMenu.show(SuggestionTextField.this, Side.BOTTOM, 0, 0);
          }
        } else {
          // no matching suggestions -> hide suggestions menu
          suggestionMenu.hide();
        }
      }
    });

    // hide on focus change
    focusedProperty().addListener((observableValue, oldValue, newValue) -> {
      suggestionMenu.hide();
    });
  }

  /**
   * Populates the suggestion menu with items
   * @param matchingSuggestions The set of matching strings.
   */
  private void populateSuggestionsMenu(List<String> matchingSuggestions, String inputText) {
    List<CustomMenuItem> menuItems = new LinkedList<>();
    int count = Math.min(matchingSuggestions.size(), MAX_SUGGESTIONS);

    //Build list as set of labels
    for (int i = 0; i < count; i++) {
      final String result = matchingSuggestions.get(i);
      Label entryLabel = new Label();
      entryLabel.setGraphic(buildSuggestionText(result, inputText));
      entryLabel.setPrefHeight(10);
      CustomMenuItem item = new CustomMenuItem(entryLabel, true);
      menuItems.add(item);

      // set text of textfield and hide menu
      item.setOnAction(actionEvent -> {
        setText(result);
        positionCaret(result.length());
        suggestionMenu.hide();
      });
    }

    suggestionMenu.getItems().setAll(menuItems);
  }

  /**
   * Builds the text of an item in the suggestions menu where the matching part of the suggestion
   * is highlighted
   * @param suggestion - the suggestion
   * @param highlightText - part of the suggestion to highlight
   * @return a {@link TextFlow} that contains the suggestions with highlight
   */
  private TextFlow buildSuggestionText(String suggestion, String highlightText) {
    int filterIndex = suggestion.toLowerCase().indexOf(highlightText.toLowerCase());
    Text textBefore = new Text(suggestion.substring(0, filterIndex));
    Text textAfter = new Text(suggestion.substring(filterIndex + highlightText.length()));
    Text textFilter = new Text(suggestion.substring(filterIndex,  filterIndex + highlightText.length()));
    textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
    return new TextFlow(textBefore, textFilter, textAfter);
  }

  public void setSuggestions(List<String> suggestions) {
    this.suggestions.clear();
    this.suggestions.addAll(suggestions);
  }
}
