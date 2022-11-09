package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.example.App;
import org.example.models.Project;

import java.io.File;

public class Menu {

    private VBox root;

    public Menu(){
        initialize();
    }

    private void initialize(){
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPrefWidth(400);
        root.setPrefHeight(400);

        Label heading = new Label("Welcome to Ward Board");
        heading.setFont(Font.font("Arial",24));

        Button load = new Button("Load Project");
        Button start = new Button("Start Project");

        load.setFont(Font.font("Arial",14));
        start.setFont(Font.font("Arial",14));

        TextField project_input = new TextField();
        project_input.setFont(Font.font("Arial",14));
        project_input.setPromptText("project input");

        load.setOnAction(e->{
            // load the file here.
            FileChooser dialog = new FileChooser();
            dialog.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.sesp"));

            File file = dialog.showOpenDialog(null);
            if(file == null) return;
            String name = file.getName();
            System.out.println(name);
            App.project = new Project(name);
            App.project.readFile();
            loadHome();
        });

        start.setOnAction(e->{
            if(project_input.getText().isEmpty()) return;

            App.project = new Project(project_input.getText());
            App.project.create_file();
            loadHome();
        });

        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(project_input,start);

        root.getChildren().addAll(heading,load,box);
    }

    public void loadHome(){
        Main main = new Main();
        App.scene.setRoot(main.getContent());
    }

    public VBox getRoot() {
        return root;
    }
}
