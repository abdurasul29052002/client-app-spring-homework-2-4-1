package com.company.UI;

import com.company.Main;
import com.company.entity.Card;
import com.company.payload.TransferDto;
import com.company.service.ConnectionService;
import com.google.gson.Gson;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.InputStreamReader;

public class HistoryUI {
    public static void setHistoryStage(Stage stage, Card fromCard){
        ConnectionService connectionService = new ConnectionService();
        InputStream inputStreamHistory = connectionService.getConnectionWithToken("http://localhost:8082/api/history/" + fromCard.getId(), Main.token);
        Gson gson = new Gson();
        ListView<String> listView = new ListView<>();
        Button buttonBack = new Button("Back");
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane,450,350);

        TransferDto[] transferDtos = gson.fromJson(new InputStreamReader(inputStreamHistory), TransferDto[].class);
        gridPane.add(listView,0,0);
        gridPane.add(buttonBack,0,1);
        for (TransferDto transferDto : transferDtos) {
            listView.getItems().add(transferDto.getFromCard().getNumber()+" => "+transferDto.getToCard().getNumber()+" Amount: "+transferDto.getAmount()+" Date: "+transferDto.getDate());
        }
        listView.setPrefSize(450,300);
        buttonBack.setTranslateX(200);
        buttonBack.setOnAction(event -> {
            UserDashboardUI.setDashboardStage(stage,fromCard.getUser().getUsername());
        });

        stage.setScene(scene);
        stage.setTitle("History");
        stage.show();
    }
}
