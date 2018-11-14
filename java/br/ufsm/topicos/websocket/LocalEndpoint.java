package br.ufsm.topicos.websocket;

import br.ufsm.topicos.model.Card;
import com.google.gson.Gson;
import javafx.application.Platform;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.io.StringReader;


/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Modelagem de Software - Design Patterns
 * Daniel Pinheiro Vargas
 * Criado em 16/10/2018.
 */


public class LocalEndpoint extends Endpoint implements MessageHandler.Whole<String> {

    public static Agent agent;
    private Session session;

    private boolean turno  = false;
    private boolean token  = false;
    private boolean isPlaying = false;
    private boolean envido = false;
    private boolean flor = false;
    private boolean truco = false;
    private boolean isLastRaise = false;
    private int trucoLevel = 0;
    private int florLevel = 0;
    private boolean isLastFlorRaise = false;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Endpoint opened, session = " + session + ", config = " + config);
        this.session = session;
        session.addMessageHandler(this);
        agent.setEndpoint(this);
    }

    //Mensagens recebidas do server
    @Override
    public void onMessage(String message) {

        Platform.runLater(new Runnable() {
            public void run() {
                JsonReader reader = Json.createReader(new StringReader(message));
                JsonObject messageJson = reader.readObject();
                System.out.println(messageJson.toString());
                switch (messageJson.getString("action")) {
                    case "WAIT":
                        agent.setInfo("'Aguardando oponente!'");
                        break;
                    case "START":
                        //TODO: Agent recebe a mensagem inicial e, se for sua vez, joga, senão aguarda o oponente jogar
                        /**
                         * Informações vindas do servidor:
                         * isHand - true/false
                         * isTurn - true/false
                         * isToken - true/false
                         * hasFlor - true/false
                         * OpponentName - String
                         * cartas - Array<Card>
                         * exemplo: messageJson.getString("isHand")
                         */
                        break;
                    case "SHIFT_TURN":
                        //TODO: no cliente existe uma variável booleana chamada turno. Essa mensagem atualiza o valor dessa variável
                        /**
                         * Informações vindas do servidor:
                         * isTurn - true/false
                         * isToken - true/false
                         */
                        turno = Boolean.parseBoolean(messageJson.getString("isTurn"));
                        token = Boolean.parseBoolean(messageJson.getString("isToken"));
                        break;
                    case "SHIFT_TOKEN":
                        //TODO: no cliente existe uma variável booleana chamada token. Essa mensagem atualiza o valor dessa variável
                        /**
                         * Informações vindas do servidor:
                         * isToken - true/false
                         */
                        token = Boolean.parseBoolean(messageJson.getString("isToken"));
                        break;
                    case "FLOR":
                        //TODO: mensagem enviada para os dois jogadores, quem pediu aguarda resposta, caso o adversário tenha flor.
                        /**
                         *  Informações vindas do servidor:
                         *  content - "Aguardando resposta de: ..." / "... cantou ..."
                         *  tipoFlor - (1: FLOR; 2: FLOR_FLOR; 3: CONTRA_FLOR; 4: CONTRA_FLOR_FALTA; 5: CONTRA_FLOR_RESTO);
                         *  isPediu - true/false
                         *  hasFlor - true/false
                         *  florSize - Tamanho da cadeia de Flor, por exemplo FLOR + CONTRA_FLOR, florSize = 2
                         */

                        break;
                    case "ENVIDO":
                        //TODO: mensagem enviada para os dois jogadores, quem pediu aguarda resposta do adversário.
                        /**
                         * Informações vindas do servidor:
                         * content - "Aguardando resposta de: ..." / "... pediu ..."
                         * tipoEnvido - (1: ENVIDO; 2: ENVIDO_ENVIDO; 3: REAL_ENVIDO; 4: FALTA_ENVIDO)
                         * isPediu - true/false
                         * hasFlor - true/false
                         * envidoSize - Tamanho da cadeia de Envido
                         */
                        break;
                    case "TRUCO":
                        //TODO: mensagem enviada para os dois jogadores, quem pediu aguarda resposta do adversário.
                        /**
                         * Informações vindas do servidor:
                         * content", "Aguardando resposta de: ..." / "... pediu ..."
                         * tipoTruco - (1: TRUCO; 2: RETRUCO; 3: VALE4;)
                         * isPediu - true/false
                         * round - int
                         * isHand - true/false
                         * hasFlor - true/false
                         */
                        break;
                    case "ENVIDO_ERROR":
                        //TODO: caso de exception no envido essa mensagem apenas é enviada para quem pediu
                        /**
                         * Informações vindas do servidor:
                         * content - "Exceção"
                         * tipoEnvido - (1: ENVIDO; 2: ENVIDO_ENVIDO; 3: REAL_ENVIDO; 4: FALTA_ENVIDO)
                         * isPediu = false
                         * hasFlor = false
                         * envidoSize = 0
                         */
                        break;
                    case "PLAY_CARD":
                        //TODO: mensagem enviada para os dois jogadores
                        /**
                         * Informações vindas do servidor:
                         * isPlayed - true/false
                         * card - json
                         * isHand - true/false
                         * hasFlor - true/false
                         * trucoSize - Tamanho da cadeia de Truco
                         * envidoSize - Tamanho da cadeia de Envido
                         * florSize - Tamanho da cadeia de Flor
                         */
                        break;
                    case "FACE_DOWN_CARD":
                        //TODO: mensagem enviada para os dois jogadores
                        /**
                         * Informações vindas do servidor:
                         * isPlayed - true/false
                         * round - int
                         * isHand - true/false
                         * hasFlor - true/false
                         * trucoSize - Tamanho da cadeia de Truco
                         * envidoSize - Tamanho da cadeia de Envido
                         * florSize - Tamanho da cadeia de Flor
                         */
                        break;
                    case "RESULT_ROUND":
                        //TODO: mensagem enviada para os dois jogadores. serve para mostra quem ganhou a rodada
                        /**
                         * Informações vindas do servidor:
                         * result - "Resultado da Rodada: " + winner
                         * isWinner - true/false
                         * isEmpate - true/false
                         * round - int
                         */
                        break;
                    case "IR_BARALHO":
                        //TODO: mensagem enviada para os dois jogadores. Apenas para informar quem foi ao baralho
                        /**
                         * Informações vindas do servidor:
                         * content - player.getName() + " Foi ao Baralho"
                         */
                        break;
                    case "RESULT_ENVIDO":
                        //TODO: mensagem enviada para os dois jogadores. Apenas para informar o resultado do envido
                        /**
                         * Informações vindas do servidor:
                         * result - fulano 32 x 27 ciclano / player.getName() + " não quis."
                         */
                        break;
                    case "RESULT_FLOR":
                        //TODO: mensagem enviada para os dois jogadores. Apenas para informar o resultado da flor
                        /**
                         * Informações vindas do servidor:
                         * result - fulano 32 x 27 ciclano / player.getName() + " se achicou."
                         */
                        break;
                    case "UPDATE_PLACAR":
                        //TODO: mensagem enviada para os dois jogadores. Apenas para atualizar o placar
                        /**
                         * Informações vindas do servidor:
                         * myPoints - int
                         * otherPoints - int
                         */
                        break;
                    case "FINISH_HAND":
                        //TODO: mensagem enviada para os dois jogadores. Distribui novas cartas
                        /**
                         * Informações vindas do servidor:
                         * isHand - true/false
                         * isTurn - true/false
                         * isToken - true/false
                         * hasFlor - true/false
                         * cartas - Array<Card>
                         */
                        break;
                    case "FINISH_GAME":
                        //TODO: mensagem enviada para os dois jogadores. Apenas para informar que nova partida esta iniciando
                        /**
                         * Informações vindas do servidor:
                         *
                         */
                        break;
                    case "RESPONSE_TRUCO":
                        //TODO: mensagem enviada para os dois jogadores, quem pediu aguarda resposta do adversário. Serve apenas para atualizar info na tela
                        /**
                         * Informações vindas do servidor:
                         * content", "Aceitou"
                         * tipoTruco - (1: TRUCO; 2: RETRUCO; 3: VALE4;)
                         * isPediu - true/false
                         * round - 0
                         * isHand - true/false
                         * hasFlor - true/false
                         *
                         */
                        break;
                }
            }
        });

    }

    /** TODO: chamar esses métodos para enviar mensagens ao servidor */

    /** :::: Métodos para enviar mensagens ao servidor ::::*/

    /** tipo 1-FLOR; 2-FLOR_FLOR; 3-CONTRA_FLOR; 4-CONTRA_FLOR_FALTA; 5-CONTRA_FLOR_RESTO*/
    public void callFlor(String tipo) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "FLOR")
                .add("tipo", tipo)
                .build();
        sendMessage(jsonMessage.toString());
    }

    /** tipo 1-ENVIDO; 2-ENVIDO_ENVIDO; 3-REAL_ENVIDO; 4-FALTA_ENVIDO; */
    public void callEnvido(String tipo) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "ENVIDO")
                .add("tipo", tipo)
                .build();
        sendMessage(jsonMessage.toString());
    }

    /** tipo 1-TRUCO; 2-RETRUCO; 3-VALE4;*/
    public void callTruco(String tipo) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "TRUCO")
                .add("tipo", tipo)
                .build();
        sendMessage(jsonMessage.toString());
    }

    public void irBaralho() {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "IR_BARALHO")
                .build();
        sendMessage(jsonMessage.toString());
    }

    /** tipo 1-ENVIDO; 2-TRUCO; 3-FLOR;*/
    public void accept(String tipo) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "QUERO")
                .add("tipo", tipo)
                .build();
        sendMessage(jsonMessage.toString());
    }

    /** tipo 1-ENVIDO; 2-TRUCO; 3-FLOR;*/
    public void decline(String tipo) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "NAO_QUERO")
                .add("tipo", tipo)
                .build();
        sendMessage(jsonMessage.toString());
    }


    /** TODO: mapear a carta a ser jogada para essa classe Card*/
    public void playCard(Card card) {
        Gson gson = new Gson();
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "PLAY_CARD")
                .add("card", gson.toJson(card))
                .build();
        sendMessage(jsonMessage.toString());
    }

    /** TODO: mapear a carta a ser jogada para essa classe Card*/
    public void playFacedDownCard(Card card) {
        Gson gson = new Gson();
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("action", "FACE_DOWN_CARD")
                .add("card", gson.toJson(card))
                .build();
        sendMessage(jsonMessage.toString());
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
            System.out.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
