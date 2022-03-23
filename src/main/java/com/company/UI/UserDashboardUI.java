package com.company.UI;

import com.company.Main;
import com.company.entity.Card;
import com.company.entity.User;
import com.company.service.ConnectionService;
import com.google.gson.Gson;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UserDashboardUI {
    @SneakyThrows
    public static void setDashboardStage(Stage stage, String username) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 450, 350);
        ConnectionService connectionService = new ConnectionService();
        InputStream userInputStream = connectionService.getConnectionWithToken("http://localhost:8082/api/user/" + username, Main.token);
        Gson gson = new Gson();
        User user = gson.fromJson(new InputStreamReader(userInputStream), User.class);
        InputStream cardsInputStream = connectionService.getConnectionWithToken("http://localhost:8082/api/card/byUser/" + user.getId(), Main.token);
        Card[] cards = gson.fromJson(new InputStreamReader(cardsInputStream), Card[].class);
        ListView<String> listView = new ListView<>();
        Button buttonTransfer = new Button("Transfer");
        Button buttonHistory = new Button("History");
        Button buttonLogOut = new Button("Log out");
        Label labelBalance = new Label();

        buttonTransfer.setVisible(false);
        buttonTransfer.setTranslateX(110);
        buttonTransfer.setTranslateY(-200);
        buttonTransfer.setPrefSize(70,40);
        buttonHistory.setVisible(false);
        buttonHistory.setTranslateX(110);
        buttonHistory.setTranslateY(-150);
        buttonHistory.setPrefSize(70,40);
        buttonLogOut.setTranslateX(110);
        buttonLogOut.setTranslateY(-100);
        buttonLogOut.setPrefSize(70,40);
        labelBalance.setVisible(false);
        labelBalance.setFont(Font.font("Times new roman",14));
        labelBalance.setTranslateY(-125);
        labelBalance.setTranslateX(100);
        listView.setPrefSize(200, 400);
        for (Card card : cards) {
            listView.getItems().add(card.getNumber());
        }
        listView.setOnMouseClicked(event -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                String cardNumber = listView.getItems().get(index);
                InputStream fromCardInputStream = connectionService.getConnectionWithToken("http://localhost:8082/api/card/byNumber/" + cardNumber, Main.token);
                Card fromCard = gson.fromJson(new InputStreamReader(fromCardInputStream), Card.class);
                buttonTransfer.setVisible(true);
                buttonTransfer.setOnAction(event1 -> {
                    TransferUI.setTransferStage(stage, fromCard);
                });
                labelBalance.setVisible(true);
                labelBalance.setText("Balance: "+fromCard.getBalance());
                buttonHistory.setVisible(true);
                buttonHistory.setOnAction(event1 -> {
                    HistoryUI.setHistoryStage(stage,fromCard);
                });
            }
        });
        buttonLogOut.setOnAction(event -> {
            JOptionPane.showConfirmDialog(new JFrame(),"Confirm Log out");
            HomepageUI.setHomepageStage(stage);
        });
        gridPane.add(listView, 0, 0);
        gridPane.add(labelBalance,1,0);
        gridPane.add(buttonTransfer, 1, 1);
        gridPane.add(buttonHistory,1,2);
        gridPane.add(buttonLogOut,1,3);

        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}
