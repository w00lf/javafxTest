package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by w00lf on 10.06.2015.
 */
public class Circles {
    private Scene prevousScene;
    private Group root;
    private Stage currentStage;
    private Scene currentScene;
    private TextField radiusTextField;
    private TextField circlesCountTextField;
    private final Group circles = new Group();
    private String currentColor = "white";


    public void start(Stage stage){
        currentStage = stage;
        root = new Group();
//        addCloseButton();

        radiusTextField = new TextField();
        currentScene = new Scene(root, 800, 600, Color.BLACK);
        Rectangle colors = getGradient(currentScene);
        int initialCirclesCount = 20;
        Group circles = produceCircles(initialCirclesCount, currentColor);

        Group blendModeGroup =
                new Group(new Group(new Rectangle(currentScene.getWidth(), currentScene.getHeight(),
                        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);

        circlesCountTextField = new TextField();
        circlesCountTextField.setText(String.valueOf(initialCirclesCount));
        circlesCountTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                circlesCountTextField.setDisable(true);
                handleCircles(Integer.parseInt(newValue));
                circlesCountTextField.setDisable(false);
            }
        });
        circlesCountTextField.setAlignment(Pos.TOP_RIGHT);
        radiusTextField.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().add(blendModeGroup);
        GridPane grid = new GridPane();
        grid.add(radiusTextField, 0, 0);
        grid.add(circlesCountTextField, 0, 1);
        root.getChildren().add(grid);
        prevousScene = currentStage.getScene();
        currentStage.setScene(currentScene);
    }

    private void handleCircles(int newValue) {
        if(newValue < 0)
            return;
        int circlesCount = circles.getChildren().size();
        if(circlesCount > newValue){
            removeCircles(circlesCount - newValue);
            return;
        }
        produceCircles(newValue - circlesCount, currentColor);
    }

    private Rectangle getGradient(Scene scene) {
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new
                        Stop[]{
                        new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14, Color.web("#c0fe56")),
                        new Stop(0.28, Color.web("#5dfbc1")),
                        new Stop(0.43, Color.web("#64c2f8")),
                        new Stop(0.57, Color.web("#be4af7")),
                        new Stop(0.71, Color.web("#ed5fc2")),
                        new Stop(0.85, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f")),}));
        colors.widthProperty().bind(scene.widthProperty());
        colors.heightProperty().bind(scene.heightProperty());
        return colors;
    }

    private void addCloseButton() {
        Button closeButton = new Button();
        closeButton.setText("X");
        root.getChildren().add(closeButton);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStage.setScene(prevousScene);
            }
        });
    }

    private Group produceCircles(int count, String color) {
        for (int i = 0; i < count; i++) {
            Circle circle = new Circle(150, Color.web(color, 0.05));
            Bindings.bindBidirectional(radiusTextField.textProperty(), circle.radiusProperty(), new NumberStringConverter());
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        ArrayList myList = new ArrayList();
        myList.addAll(circles.getChildren());
        CirclesAnimator circlesAnimator = new CirclesAnimator(myList, currentScene);
        circlesAnimator.animate();
        return circles;
    }

    private void removeCircles(int count) {
        int circlesCount = circles.getChildren().size();
        int circlesToRemove = (count > circlesCount) ? circlesCount : count;
        circles.getChildren().remove(0, circlesToRemove);
    }
}
