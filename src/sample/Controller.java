package sample;

import javafx.event.ActionEvent;

import java.awt.*;

public class Controller {
    public Label helloWorld;
    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    }
}
