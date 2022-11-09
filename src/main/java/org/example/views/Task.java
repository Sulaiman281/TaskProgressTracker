package org.example.views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.NumFieldFX;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private Label created_dateTime,modified_dateTime;
    private VBox box;
    private TextField name;
    private TextArea description;
    private TextField points;
    private TextField est_effort;
    private TextField creator;
    private TextField est_duration;

    private ChoiceBox<String> type;

    private Button save, cancel;
    private Stage stage = new Stage();


    org.example.models.Task task;

    public Task(org.example.models.Task task){
        this.task = task;
        initialize();
        name.setText(task.getName());
        points.setText(String.valueOf(task.getPoints()));
        description.setText(task.getDescription());
        type.setValue(task.getType());
        creator.setText(task.getCreator());
        est_duration.setText(task.getEst_duration());
        est_effort.setText(task.getEst_efforts());

        created_dateTime.setText("Created Time: "+task.getCreated_dateTime());
        modified_dateTime.setText("Last Modified Time: "+task.getModified_dateTime());

        stage.setTitle("Task: "+task.getName());
        stage.setScene(new Scene(box));
        stage.showAndWait();
    }

    private void changeColor(){
        switch(type.getValue()){
            case "Spike":
                box.setStyle(
                        "-fx-background-color: GREEN"
                );
                created_dateTime.setTextFill(Color.WHITE);
                modified_dateTime.setTextFill(Color.WHITE);
                break;
            case "Story":
                box.setStyle(
                        "-fx-background-color: BLUE"
                );
                created_dateTime.setTextFill(Color.WHITE);
                modified_dateTime.setTextFill(Color.WHITE);
                break;
            case "Epic":
                box.setStyle(
                        "-fx-background-color: ORANGE"
                );
                created_dateTime.setTextFill(Color.WHITE);
                modified_dateTime.setTextFill(Color.WHITE);
                break;
            case "Bug":
                box.setStyle(
                        "-fx-background-color: RED"
                );
                created_dateTime.setTextFill(Color.WHITE);
                modified_dateTime.setTextFill(Color.WHITE);
                break;
            default:
                box.setStyle(
                        "-fx-background-color: BLACk"
                );
                created_dateTime.setTextFill(Color.BLACK);
                modified_dateTime.setTextFill(Color.BLACK);
        }
    }

    private void initialize(){
        box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);

        type = new ChoiceBox<>();
        type.getItems().addAll("Spike","Story","Epic","Bug");
        type.valueProperty().addListener((observableValue, s, t1) -> {
            changeColor();
        });

        type.setStyle(
                "-fx-background-color: #96F062;"+
                        "-fx-border-radius: 20"
        );
        box.getChildren().add(type);

        name = new TextField();
        name.setPromptText("title");
        name.setFont(Font.font("Arial",14));
        box.getChildren().add(getBox(getLabel("Title:"),name));

        points = new TextField();
        points.setFont(Font.font("Arial",14));
        new NumFieldFX().numField(points);
        points.setPromptText("Points");
        box.getChildren().add(getBox(getLabel("Points:"),points));

        creator = new TextField();
        creator.setFont(Font.font("Arial",14));
        creator.setPromptText("creator");
        box.getChildren().add(getBox(getLabel("Creator:"),creator));

        description = new TextArea();
        description.setWrapText(true);
        description.setFont(Font.font("Arial",12));
        description.setPromptText("description here.");
        box.getChildren().add(getBox(getLabel("Description:"),description));


        est_effort = new TextField();
        est_effort.setFont(Font.font("Arial",14));
        est_effort.setPromptText("Estimate Efforts");
        box.getChildren().add(getBox(getLabel("Est Efforts:"),est_effort));


        est_duration = new TextField();
        est_duration.setFont(Font.font("Arial",14));
        est_duration.setPromptText("Estimate Duration");
        box.getChildren().add(getBox(getLabel("Est Duration:"),est_duration));

        save = new Button("Save");
        cancel = new Button("Cancel");
        save.setFont(Font.font("Arial",18));
        cancel.setFont(Font.font("Arial",18));

        save.setOnAction(e->{
            if(name.getText().isEmpty() || description.getText().isEmpty() || points.getText().isEmpty()
                || type.getSelectionModel().isEmpty() || est_effort.getText().isEmpty() || est_duration.getText().isEmpty()) return;
            this.task.setName(name.getText());
            this.task.setDescription(description.getText());
            this.task.setType(type.getValue());
            this.task.setPoints(Integer.parseInt(points.getText()));
            this.task.setCreator(creator.getText());
            this.task.setEst_efforts(est_effort.getText());
            this.task.setEst_duration(est_duration.getText());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = LocalDateTime.now().format(dtf);
            if(this.task.getCreated_dateTime().isEmpty()){
                this.task.setCreated_dateTime(date);
            }
            this.task.setModified_dateTime(date);
            stage.close();
        });
        cancel.setOnAction(e->{
            stage.close();
        });

        save.setStyle(
                "-fx-background-color: #96F062;"+
                        "-fx-text-fill: #2157BD;"+
                        "-fx-border-radius: 20"
        );
        cancel.setStyle(
                "-fx-background-color: #96F062;"+
                        "-fx-text-fill: #2157BD;"+
                        "-fx-border-radius: 20"
        );

        box.getChildren().add(getBox(save,cancel));

        created_dateTime = new Label();
        created_dateTime.setFont(Font.font("Arial",18));
        modified_dateTime = new Label();
        modified_dateTime.setFont(Font.font("Arial",18));
        box.getChildren().addAll(created_dateTime,modified_dateTime);
    }

    Label getLabel(String text){
        Label label = new Label(text);
        label.setFont(Font.font("Arial",18));
        label.setTextFill(Color.WHITE);
        return label;
    }

    HBox getBox(Node... node){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(40);
        hbox.getChildren().addAll(node);
        return hbox;
    }

}
