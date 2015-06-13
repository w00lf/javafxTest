package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Random;

import static java.lang.Math.random;

/**
 * Created by w00lf on 10.06.2015.
 */
public class Circles {
    private Scene prevousScene;
    private Group root;
    private Stage currentStage;
    private TextField radiusTextField;

    public void start(Stage stage){
        currentStage = stage;
        root = new Group();
//        addCloseButton();

        radiusTextField = new TextField();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        Rectangle colors = getGradient(scene);
        Group circles = getWhiteCircles();

        Group blendModeGroup =
                new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
                        Color.BLACK), circles), colors);
        colors.setBlendMode(BlendMode.OVERLAY);
        root.getChildren().add(blendModeGroup);
        root.getChildren().add(radiusTextField);
        prevousScene = currentStage.getScene();
        currentStage.setScene(scene);
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

    private Group getWhiteCircles() {
        Group circles = new Group();
        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05));
            Bindings.bindBidirectional(radiusTextField.textProperty(), circle.radiusProperty(), new NumberStringConverter());
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16));
            circle.setStrokeWidth(4);
            circles.getChildren().add(circle);
        }
        for (Node circle: circles.getChildren()) {
            new Thread() {
                // runnable for that thread
                public void run() {
                    double currentAnimationLength = 0.0;
                    long startAnimationTime = System.currentTimeMillis();
                    Duration endDuration = new Duration(currentAnimationLength);
                    double currentX = circle.getLayoutX();
                    double currentY = circle.getLayoutY();
                    double nextX = circle.getLayoutX();
                    double nextY = circle.getLayoutY();

                    Timeline timeline = new Timeline();
                    while (!isInterrupted()) {

                        if ((System.currentTimeMillis() - startAnimationTime) < currentAnimationLength)
                            continue;
                        timeline.stop();
                        currentAnimationLength = (random() * 10000) + 10000.0;
                        System.out.println("Current animation length: " + currentAnimationLength);
                        System.out.println("Current time: " + (System.currentTimeMillis() - startAnimationTime));
                        Duration temp = new Duration(currentAnimationLength);
                        nextX = random() * 800;
                        nextY = random() * 600;
                        timeline.getKeyFrames().addAll(
                            new KeyFrame(endDuration, // set start position at 0
                                    new KeyValue(circle.translateXProperty(),currentX),
                                    new KeyValue(circle.translateYProperty(), currentY)
                            ),

                            new KeyFrame(new Duration(currentAnimationLength), // set end position at 40s
                                    new KeyValue(circle.translateXProperty(), nextX),
                                    new KeyValue(circle.translateYProperty(), nextY)
                            )
                        );
                        currentX = nextX;
                        currentY = nextY;
                        startAnimationTime = System.currentTimeMillis();
                        endDuration = temp;
                        timeline.play();
                    }
                }
            }.start();
        }
        return circles;
    }
}
