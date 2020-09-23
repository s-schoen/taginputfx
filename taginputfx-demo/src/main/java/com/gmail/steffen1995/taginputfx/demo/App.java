/*
 * MIT License
 *
 * Copyright (c) 2020 Steffen S.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
