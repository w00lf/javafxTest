package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.random;

/**
 * Created by w00lf on 14.06.2015.
 */
public class CirclesAnimator {
    private final ArrayList<Circle> circles;
    private HashMap<Integer, Boolean> animataionStatuses = new HashMap<Integer, Boolean>();
    private Scene scene;

    CirclesAnimator( final ArrayList<Circle> circles, final Scene scene){
        this.circles = circles;
        this.scene = scene;
        for (Node circle: circles){
            animataionStatuses.put(circle.hashCode(), false);
        }
    }

    public void animate() {
        new Thread() {
            public void run() {
                while(!isInterrupted()) {
                    for (Node circle : circles) {
                        if (animataionStatuses.get(circle.hashCode()) == true)
                            continue;
                        animateCircle(circle);
                    }
                }
            }
        }.start();
    }

    private void animateCircle(Node circle) {
        Timeline timeline = new Timeline();
        double currentAnimationLength = (random() * 20000);
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO,
                    new KeyValue(circle.translateXProperty(), circle.localToScene(0.0, 0.0).getX() ),
                    new KeyValue(circle.translateYProperty(), circle.localToScene(0.0, 0.0).getY() )
            ),

            new KeyFrame(new Duration(currentAnimationLength), // set end position at 40s
                    new KeyValue(circle.translateXProperty(), random() * scene.getWidth()),
                    new KeyValue(circle.translateYProperty(), random() * scene.getHeight())
            )
        );
        timeline.setOnFinished(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animataionStatuses.put(circle.hashCode(), false);
            }
        });
        animataionStatuses.put(circle.hashCode(), true);
        timeline.play();
    }
}
