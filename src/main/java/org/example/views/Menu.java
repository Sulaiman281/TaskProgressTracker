package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.example.App;
import org.example.models.Project;

import java.io.File;

public class Menu {

    private VBox root;

    public Menu(){
        initialize();
        bg_style();
    }

    private void initialize(){
        root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(20);
        root.setPrefWidth(600);
        root.setPrefHeight(700);

        Label heading = new Label("Welcome to Ward Board");
        heading.setTextFill(Color.web("#96F062"));
        heading.setFont(Font.font("Arial",28));

        Button load = new Button("Load Project");
        btnStyle(load);
        Button start = new Button("Start Project");
        btnStyle(start);

        load.setFont(Font.font("Arial",16));
        start.setFont(Font.font("Arial",16));

        TextField project_input = new TextField();
        project_input.setFont(Font.font("Arial",14));
        project_input.setPromptText("project name");

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
        main.autoUpdate();
    }

    private void btnStyle(Node node){
        node.setStyle(
                "-fx-background-color: #96F062;"+
                        "-fx-text-fill: #2157BD;"+
                        "-fx-border-radius: 20"
        );
    }

    private void bg_style(){
        Image img = new Image(this.getClass().getResource("bg.png").toString());
        BackgroundImage myBI= new BackgroundImage(img,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
    }

    public VBox getRoot() {
        return root;
    }
}
