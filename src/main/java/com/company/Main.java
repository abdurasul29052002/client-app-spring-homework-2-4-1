package com.company;

import com.company.UI.HomepageUI;
import com.company.UI.RegisterAndLoginUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static String token;

    public static void main(String[] args) {
	launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        HomepageUI.setHomepageStage(primaryStage);
    }
}
