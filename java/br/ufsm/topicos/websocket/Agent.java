package br.ufsm.topicos.websocket;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 16/10/2018.
 */


public class Agent extends Application {

    private String SERVER = "ws://localhost:8080/truco/cbr/AgentCBR";
    final StringProperty info = new SimpleStringProperty("starting the game");
    final StringProperty turnInfo = new SimpleStringProperty("");


    @SuppressWarnings("unused")
	public static void main(String[] args) {
        launch(args);
    }

    private LocalEndpoint endpoint;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LocalEndpoint.agent = this;
        Label infoLabel = new Label();
        infoLabel.textProperty().bind(info);
        Label turnInfoLabel = new Label();
        turnInfoLabel.textProperty().bind(turnInfo);
        VBox vbox = new VBox();
        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 100");
        root.setMargin(infoLabel, new Insets(10, 0, 10, 0));
        root.setTop(infoLabel);
        root.setCenter(vbox);
        root.setBottom(turnInfoLabel);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();

    }

    private void startGame() throws URISyntaxException, DeploymentException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(LocalEndpoint.class, null, new URI(SERVER));
    }

    public void setEndpoint(LocalEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void setInfo(String info) {
        this.info.set(info);
    }
}
