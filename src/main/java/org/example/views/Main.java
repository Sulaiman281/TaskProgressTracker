package org.example.views;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.example.App;
import org.example.models.Project;
import org.example.models.Task;
import org.example.models.Tasks;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    private BorderPane root;
    private HBox heading,center;

    private TextField addList;

    private Node selectedNode;
    private boolean correct_drag;
    class CardView extends HBox{
        Label label = new Label();
        Task task;
        ContextMenu contextMenu = new ContextMenu();
//        double x,y;
        CardView(Task task){
            this.task = task;
            label.setText(task.getName());
            label.setWrapText(true);
            label.setFont(Font.font("Arial",18));

            this.setPrefWidth(140);
            this.setAlignment(Pos.CENTER);
            this.getChildren().add(label);

            MenuItem edit = new MenuItem("Edit");
            MenuItem remove = new MenuItem("Remove");
            edit.setOnAction(e->{
                org.example.views.Task t = new org.example.views.Task(task);
                label.setText(task.getName());
            });
            remove.setOnAction(e->{
                CardListView parent = (CardListView) this.getParent();
                parent.getChildren().remove(this);
            });

            contextMenu.getItems().addAll(edit,remove);

            label.setContextMenu(contextMenu);

            setOnMouseClicked(e->{
//                System.out.println("Primary: "+(e.getButton() == MouseButton.PRIMARY));
//                System.out.println("Back: "+(e.getButton() == MouseButton.BACK));
//                System.out.println("Forward: "+(e.getButton() == MouseButton.FORWARD));
//                System.out.println("Secondary: "+(e.getButton() == MouseButton.SECONDARY));
//                System.out.println("Middle: "+(e.getButton() == MouseButton.MIDDLE));
                if(e.getButton() == MouseButton.PRIMARY) {
                    org.example.views.Task t = new org.example.views.Task(task);
                    label.setText(task.getName());
                }
            });
            hoverProperty().addListener((observableValue, aBoolean, t1) -> {
                if(t1){
                    setStyle(
                            "-fx-background-color: #a7fabd;"+
                                    "-fx-border-color: rgb(100,100,100)"
                    );
                }else{
                    setStyle(
                            "-fx-background-color: white;"+
                                    "-fx-border-color: rgb(100,100,100)"
                    );
                }
            });
            setOnDragDetected(e->{
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(task.toString());
                db.setContent(content);
                correct_drag = false;
            });
            setOnDragDone(e->{
                if(correct_drag) {
                    CardListView parent = (CardListView) this.getParent();
                    parent.getChildren().remove(this);
                }
            });
//            setOnMousePressed(e->{
//                selectedNode = this;
//                x = e.getSceneX();
//                y = e.getSceneY();
//            });
//            setOnMouseDragged(e->{
//                double offsetX = (e.getSceneX() - x);
//                double offsetY = (e.getSceneY() - y);
//                double new_x = this.getLayoutX() + offsetX;
//                double new_y = this.getLayoutY() + offsetY;
//
//                this.setLayoutX(new_x);
//                this.setLayoutY(new_y);
//            });
            setStyle(
                    "-fx-background-color: white;"+
                            "-fx-border-color: rgb(100,100,100)"
            );
        }
    }

    public void autoUpdate(){
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.minutes(5),e->{
            App.project.tasks.clear();
            for(int i = 0; i < center.getChildren().size()-1; i++){
                CardListView list = (CardListView) center.getChildren().get(i);
                for(int j = 0; j< list.getChildren().size()-1; j++){
                    if(j == 0) continue;
                    CardView task = (CardView) list.getChildren().get(j);
                    if(j == 1){
                        list.list.setMyTasks(task.task.toString());
                    }else
                        list.list.setMyTasks(list.list.getMyTasks().concat(task.task.toString()));
                    App.project.tasks.add(list.list);
                }
            }
            App.project.updateFile();
            System.out.println("Auto Saved.");
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    // here is the listVIew of card. you can add as many as you want here.
    class CardListView extends VBox {
        Label listName = new Label();
        Tasks list;
        ArrayList<CardView> cardView = new ArrayList<>();
        ContextMenu contextMenu;
        TextField addNewCard;
        public CardListView(Tasks list, ArrayList<CardView> cardView){
            if(cardView !=null) {
                this.cardView.addAll(cardView);
                this.getChildren().addAll(cardView);
            }
            this.list = list;
            this.listName.setText(list.getList_name());
            this.listName.setFont(Font.font("Arial",18));

            contextMenu = new ContextMenu();
            MenuItem remove = new MenuItem("Remove");
            remove.setOnAction(e->{
                center.getChildren().remove(this);
            });
            contextMenu.getItems().addAll(remove);
            listName.setContextMenu(contextMenu);

            addNewCard = new TextField();
            addNewCard.setPromptText("+ Add another Card");
            addNewCard.setFont(Font.font("Arial",14));
            addNewCard.setOnKeyPressed(e->{
                if(e.getCode() == KeyCode.ENTER){
                    if(!addNewCard.getText().isEmpty()) {
                        Task task = new Task();
                        task.setName(addNewCard.getText());
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String date = LocalDateTime.now().format(dtf);
                        task.setCreated_dateTime(date);
                        CardView view = new CardView(task);
                        this.getChildren().add(this.getChildren().size()-1,view);
                        addNewCard.setText("");
                    }
                }
            });

            this.setMinWidth(160);
            this.setPrefWidth(160);
            this.setSpacing(20);
            this.setMinHeight(USE_COMPUTED_SIZE);
            this.setAlignment(Pos.TOP_CENTER);

            this.setOnMouseClicked(e->{
                selectedNode = this;
                System.out.println("ListView: "+selectedNode.toString());
                return;
            });

            this.getChildren().add(0,listName);
            this.getChildren().add(addNewCard);
            this.setOnDragOver(e->{
                e.acceptTransferModes(TransferMode.ANY);
            });
            setOnDragDropped(e->{
                try {
                    Task task = extractTask(e.getDragboard().getString());
                    CardView view = new CardView(task);
                    this.getChildren().add(this.getChildren().size() - 1, view);
                    correct_drag = true;
                } catch (Exception exception) {
                    System.err.println(exception.getMessage());
                }
            });
            setStyle(
                    "-fx-background-color: rgb(100,100,100)"
            );
        }
    }

    public Task extractTask(String task){
        try {
            String[] str = task.split("!");
            Task t = new Task();
            t.setName(str[0]);
            t.setDescription(str[1]);
            t.setPoints(Integer.parseInt(str[2]));
            t.setType(str[3]);
            t.setCreated_dateTime(str[4]);
            t.setModified_dateTime(str[5]);
            t.setCreator(str[6]);
            t.setEst_efforts(str[7]);
            t.setEst_duration(str[8]);
            return t;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Main(){
        initiate();
        if(App.project.tasks.size() > 0){
            customList();
        }else
            defaultLists();

        autoUpdate();
    }

    private void initiate(){
        root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        heading = new HBox();
        heading.setAlignment(Pos.CENTER);
        heading.setPrefHeight(50);
        heading.setSpacing(20);

        center = new HBox();
        center.setAlignment(Pos.TOP_LEFT);
        center.setSpacing(20);
        center.setPrefWidth(getContent().getPrefWidth());
        center.setPrefHeight(getContent().getPrefHeight());

        scrollPane.setContent(center);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setTop(heading);
        root.setCenter(scrollPane);

        addList = new TextField();
        addList.setPrefWidth(160);
        addList.setPromptText("+ Add another List");
        addList.setFont(Font.font("Arial",14));
        addList.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.ENTER){
                if(!addList.getText().isEmpty()) {
                    Tasks t = new Tasks();
                    t.setList_name(addList.getText());
                    App.project.tasks.add(t);
                    CardListView cardListView = new CardListView(t,null);
                    center.getChildren().add(center.getChildren().size()-1,cardListView);
                    addList.setText("");
                }
            }
        });
        if(center.getChildren().isEmpty())
            center.getChildren().add(addList);
        else
            center.getChildren().add(center.getChildren().size()-1,addList);

        // heading elements
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.valueProperty().addListener((observableValue, color, t1) -> {
            if(selectedNode != null){
                selectedNode.setStyle("-fx-background-color: #"+Integer.toHexString(t1.hashCode()));
            }
        });

        heading.getChildren().add(colorPicker);
        headingSetup();
    }

    private void headingSetup(){
        Button update = new Button("Update");
        update.setFont(Font.font("Arial",14));
        update.setOnAction(e->{
            App.project.tasks.clear();
            for(int i = 0; i < center.getChildren().size()-1; i++){
                CardListView list = (CardListView) center.getChildren().get(i);
                for(int j = 0; j< list.getChildren().size()-1; j++){
                    if(j == 0) continue;
                    CardView task = (CardView) list.getChildren().get(j);
                    if(j == 1){
                        list.list.setMyTasks(task.task.toString());
                    }else
                        list.list.setMyTasks(list.list.getMyTasks().concat(task.task.toString()));
                    App.project.tasks.add(list.list);
                }
            }
            System.out.println(App.project.tasks.toString());
            App.project.updateFile();
        });
        Button load = new Button("Load Project");
        load.setOnAction(e->{
            App.project.updateFile();
            FileChooser dialog = new FileChooser();
            dialog.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.sesp"));

            File file = dialog.showOpenDialog(null);
            if(file == null) return;
            String name = file.getName();
            System.out.println(name);
            App.project = new Project(name);
            App.project.readFile();
            center.getChildren().clear();
            center.getChildren().add(addList);
            if(App.project.tasks.size() > 0){
                customList();
            }else{
                defaultLists();
            }
        });
        Button delete_btn = new Button("DELETE");
        delete_btn.setOnAction(e->{
            if(selectedNode != null){
//                Node parent = selectedNode.getParent();
                // delete that card.
            }
        });

        heading.getChildren().add(delete_btn);

        heading.getChildren().add(0,update);
        heading.getChildren().add(0,load);
    }

    private void defaultLists(){
        Tasks t1 = new Tasks();
        t1.setList_name("Todo");
        CardListView cardListView = new CardListView(t1,null);
        center.getChildren().add(center.getChildren().size()-1,cardListView);

        Tasks t2 = new Tasks();
        t2.setList_name("In Progress");
        CardListView cardListView1 = new CardListView(t2,null);
        center.getChildren().add(center.getChildren().size()-1,cardListView1);
        Tasks t3 = new Tasks();
        t3.setList_name("Done");
        CardListView cardListView2 = new CardListView(t3,null);
        center.getChildren().add(center.getChildren().size()-1,cardListView2);
    }
    private void customList(){
        for (Tasks task : App.project.tasks) {
            String[] cards = task.getMyTasks().split(",");
            ArrayList<CardView> cardViews = new ArrayList<>();
            for (String card : cards) {
                Task t = extractTask(card);
                if(t == null) continue;
                cardViews.add(new CardView(t));
            }
            CardListView cardListView = new CardListView(task,cardViews);
            center.getChildren().add(center.getChildren().size()-1,cardListView);
        }
    }
    public BorderPane getContent(){
        return root;
    }
}
