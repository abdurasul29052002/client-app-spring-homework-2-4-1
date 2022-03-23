package com.company.UI;

import com.company.Main;
import com.company.payload.UserDto;
import com.company.service.ConnectionService;
import com.google.gson.Gson;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class RegisterAndLoginUI {


    public static void setRegisterAndLoginStage(Stage stage,String url){
        GridPane gridPane = new GridPane();
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        TextField textField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField password = new TextField();
        CheckBox checkBox = new CheckBox("Show password");
        Button button = new Button("Ok");
        Button buttonBack = new Button("Back");

        checkBox.setSelected(false);
        button.setPrefSize(75,35);

        passwordField.visibleProperty().bind(checkBox.selectedProperty().not());
        password.visibleProperty().bind(checkBox.selectedProperty());
        password.textProperty().bindBidirectional(passwordField.textProperty());

        gridPane.setVgap(15);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(usernameLabel,0,0);
        gridPane.add(passwordLabel,0,1);
        gridPane.add(textField,1,0);
        gridPane.add(passwordField,1,1);
        gridPane.add(password,1,1);
        gridPane.add(checkBox,0,2,2,1);
        gridPane.add(button,1,3,2,1);
        gridPane.add(buttonBack,0,3);

        Scene scene = new Scene(gridPane,450,350);

        button.setOnAction(event -> {
            UserDto userDto = new UserDto(textField.getText(),passwordField.getText());
            Gson gson = new Gson();
            String json = gson.toJson(userDto);
            ConnectionService connectionService = new ConnectionService();
            try {
                String token = connectionService.postConnectionWithoutToken(new URL(url), json);
                if(token!=null){
                    Main.token=token;
                    JOptionPane.showMessageDialog(new JFrame(),"Successfully");
                    UserDashboardUI.setDashboardStage(stage,textField.getText());
                }else {
                    HomepageUI.setHomepageStage(stage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        buttonBack.setOnAction(event -> {
            HomepageUI.setHomepageStage(stage);
        });
        stage.setScene(scene);
        if (url.endsWith("login")){
            stage.setTitle("Login");
        }else {
            stage.setTitle("Register");
        }
        stage.show();
    }
}
