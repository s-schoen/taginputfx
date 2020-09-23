package com.gmail.steffen1995.taginputfx.demo;

import com.gmail.steffen1995.taginputfx.TagInput;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TagInputFx Demo");

        VBox root = new VBox();

        ObservableList<String> tags = FXCollections.observableArrayList();
        tags.addAll("Apple", "Pear", "Banana");

        // setup tag input
        TagInput tagInput = new TagInput();

        // set list for tag suggestions
        tagInput.setAvailableTags(tags);

        // allow tags that are now part of the available tags (default: true)
        tagInput.setAllowNewTags(true);

        Text header = new Text("Tags:");
        root.getChildren().addAll(header, tagInput);
        Scene scene = new Scene(root, 600, 50);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
