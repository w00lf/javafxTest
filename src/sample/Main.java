package sample;

import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    final GridPane grid = new GridPane();
    final TextField userTextField = new TextField();
    final Circles circles = new Circles();
    private Stage primaryStage;
    int currentPosition;

    public static void main(String[] args) { launch(args); }

    @Override public void start(Stage stage) throws Exception {
          circles.start(stage);
//        primaryStage = stage;
//        stage.setTitle("TODO list ver 1");
//        grid.setAlignment(Pos.TOP_CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        Insets ins = new Insets(0, 0, 25, 25);
//        grid.setPadding(ins);
//
//        Text scenetitle = new Text("ToDo list");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        grid.add(userTextField, 0, 1);
//        userTextField.setPromptText("Write here");
//
//        Button submitButton = new Button();
//        submitButton.setText("Submit");
//        submitButton.setOnAction(new EventHandler<ActionEvent>() {
//            private void showAlert(String text) {
//                Stage dialogStage = new Stage();
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                Button okButotn = new Button("Ok.");
//                okButotn.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        dialogStage.close();
//                    }
//                });
//                dialogStage.setScene(new Scene(VBoxBuilder.create().
//                    children(new Text(text), okButotn).
//                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
//                dialogStage.show();
//            }
//            @Override
//            public void handle(ActionEvent event) {
//                String currentText  = userTextField.getText();
//                if ( currentText.length() == 0 ){
//                    showAlert("Cannot be blank!");
//                    return;
//                }
//                Text result = new Text( Integer.toString(currentPosition) + ". " + currentText  );
//                currentPosition += 1;
//                grid.add(result, 0, currentPosition, 2, 1);
//
//                Button removeButton = new Button("X");
//                removeButton.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        grid.getChildren().remove(removeButton);
//                        grid.getChildren().remove(result);
//                        currentPosition -= 1;
//                    }
//                });
//                grid.add(removeButton, 1, currentPosition, 2, 1);
//            }
//        });
//        grid.add(submitButton, 1, 3);
//        setCirclesSwitch();
//        currentPosition = 2;
//
//        Scene scene = new Scene(grid, 300, 275);
//        stage.setScene(scene);
        stage.show();
    }

    private void setCirclesSwitch() {
        Button circlesSwitchButton = new Button();
        circlesSwitchButton.setText("Circles!");
        circlesSwitchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                circles.start(primaryStage);
            }
        });
        grid.add(circlesSwitchButton, 1, 1);
    }
}
