package com.company.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomepageUI {


    public static void setHomepageStage(Stage stage) {
        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane,450,350);

        registerButton.setPrefSize(75,35);
        loginButton.setPrefSize(75,35);

        registerButton.setOnAction(event -> {
            RegisterAndLoginUI.setRegisterAndLoginStage(stage,"http://localhost:8082/api/user/register");
        });

        loginButton.setOnAction(event -> {
            RegisterAndLoginUI.setRegisterAndLoginStage(stage,"http://localhost:8082/api/login");
        });

        gridPane.add(registerButton,0,0);
        gridPane.add(loginButton,0,1);
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);

        stage.setScene(scene);
        stage.setTitle("Homepage");
        stage.show();
    }
}
