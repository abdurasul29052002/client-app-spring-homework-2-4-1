package com.company.UI;

import com.company.Main;
import com.company.entity.Card;
import com.company.payload.PaymentDto;
import com.company.payload.Result;
import com.company.service.ConnectionService;
import com.google.gson.Gson;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TransferUI {
    public static void setTransferStage(Stage stage, Card fromCard) {
        ConnectionService connectionService = new ConnectionService();
        Label labelAmount = new Label("Amount: ");
        TextField textFieldAmount = new TextField();
        Label labelCards = new Label("Cards: ");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        InputStream inputStreamCardList = connectionService.getConnectionWithToken("http://localhost:8082/api/card", Main.token);
        Gson gson = new Gson();
        Card[] cards = gson.fromJson(new InputStreamReader(inputStreamCardList), Card[].class);
        Label labelFromCard = new Label("From: " + fromCard.getNumber() + "          To:");
        Button buttonOk = new Button("Ok");
        Button buttonBack = new Button("Back");
        Label labelFromCardBalance = new Label("Balance: " + fromCard.getBalance());
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 450, 350);

        gridPane.add(labelAmount, 1, 1);
        gridPane.add(textFieldAmount, 2, 1);
        gridPane.add(labelCards, 1, 0);
        gridPane.add(choiceBox, 2, 0);
        gridPane.add(labelFromCard, 0, 0);
        gridPane.add(labelFromCardBalance, 0, 1);
        gridPane.add(buttonOk, 1, 2, 2, 1);
        gridPane.add(buttonBack,0,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(15);
        for (Card card : cards) {
            if (!card.getNumber().equals(fromCard.getNumber())) {
                choiceBox.getItems().add(card.getNumber());
            }
        }
        labelFromCard.setFont(Font.font("Times new roman", 14));
        labelFromCardBalance.setFont(Font.font("Times new roman", 14));
        choiceBox.setPrefSize(150, 20);
        buttonOk.setPrefSize(70, 30);
        buttonOk.setTranslateX(190);

        buttonOk.setOnAction(event -> {
            int toCardIndex = choiceBox.getSelectionModel().getSelectedIndex();
            String toCardNumber = choiceBox.getItems().get(toCardIndex);
            InputStream inputStreamToCard = connectionService.getConnectionWithToken("http://localhost:8082/api/card/byNumber/" + toCardNumber, Main.token);
            Card toCard = gson.fromJson(new InputStreamReader(inputStreamToCard), Card.class);
            PaymentDto paymentDto = new PaymentDto(fromCard.getId(), toCard.getId(), Double.parseDouble(textFieldAmount.getText()));
            InputStream inputStreamTransfer = connectionService.postConnectionWithToken("http://localhost:8082/api/outcome", gson.toJson(paymentDto), Main.token);
            Result result = gson.fromJson(new InputStreamReader(inputStreamTransfer), Result.class);
            JOptionPane.showMessageDialog(new JFrame(),result.getMessage());
            UserDashboardUI.setDashboardStage(stage,fromCard.getUser().getUsername());
        });
        buttonBack.setOnAction(event -> {
            UserDashboardUI.setDashboardStage(stage,fromCard.getUser().getUsername());
        });

        stage.setScene(scene);
        stage.setTitle("Transfer");
        stage.show();
    }
}
