package controller;

import highscores.GameStat;
import highscores.ResultManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BoardGameModel;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GameController {
    @FXML
    private Label playerNameLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private GridPane board;

    @FXML
    private Button finishButton;

    @FXML
    private Label bombsLabel;

    private int flaggedBombs;

    private String playerName;

    private BoardGameModel model = new BoardGameModel();

    private ArrayList<GameStat> stat;

    private ResultManager resMan = new ResultManager();

    private final Date date = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    File f = new File("target/minesweeper.json");

    @FXML
    public void initialize(){
        tablecreater();
        finishButton.setDisable(true);
        resultLabel.setVisible(false);

        try {
            if (f.exists() && !f.isDirectory()) {
                stat = resMan.fileReader();
            }
            else{
                stat = new ArrayList<>();
            }

        }catch (Exception e){ Logger.warn(e);}
    }

    private void tablecreater(){
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                StackPane square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
        playerNameLabel.setText(playerName);
    }

    private StackPane createSquare(int i, int j) {
        StackPane square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // bal mouse
                try {
                    leftClick(event);
                } catch (Exception e) { Logger.trace(e); }
            }
            else if (event.getButton() == MouseButton.SECONDARY) { // jobb mouse
                rightClick(event);
            }
        });

        return square;
    }

    private void leftClick(MouseEvent event) throws IOException {
        StackPane squareActive = (StackPane) event.getSource();
        int row = GridPane.getRowIndex(squareActive);
        int col = GridPane.getColumnIndex(squareActive);
        Logger.info("Click on square ("+row+","+col+")");

        if(!model.getFlaggedCell(row, col)){
            if(model.getBoardModel(row, col) == "X"){
                for (int i = 0; i < board.getRowCount(); i++) {
                    for (int j = 0; j < board.getColumnCount(); j++) {
                        if(model.getBoardModel(i, j) == "X"){
                            var square = new StackPane();
                            square.getStyleClass().add("squareOpenedBomb");
                            square.getChildren().add(new Text("X"));
                            board.add(square, j, i);
                        }
                    }
                }
                board.setDisable(true);
                finishButton.setDisable(false);
                resultLabel.setText("LOSE");
                resultLabel.setVisible(true);
                Logger.info("You Lost");

                f.createNewFile();
                stat.add(new GameStat(playerName,"LOSE",formatter.format(date)));
                resMan.fileWriter(stat);
            }
            else{
                cellRefresh(row, col, model.getBoardModel(row, col));
            }
        }
    }

    private void rightClick(MouseEvent event){
        StackPane squareActive = (StackPane) event.getSource();
        int row = GridPane.getRowIndex(squareActive);
        int col = GridPane.getColumnIndex(squareActive);
        Logger.info("Click on square ("+row+","+col+")");

        if(model.getFlaggedCell(row, col)){
            squareActive.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            model.setFlag(row, col, false);
            flaggedBombs--;
            bombsLabel.setText(flaggedBombs+"");
        }
        else{
            squareActive.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            model.setFlag(row, col, true);
            flaggedBombs++;
            bombsLabel.setText(flaggedBombs+"");
        }
    }

    private void cellRefresh(int row, int col, String value) throws IOException {
        StackPane square = new StackPane();
        square.getStyleClass().add("squareOpened");
        Text text = new Text();
        text.setText(value);
        square.getChildren().add(text);
        board.add(square, col, row);
        model.setOpened(row, col, true);
        if(model.winCheck()){
            Logger.info("You win!");
            board.setDisable(true);
            finishButton.setDisable(false);
            resultLabel.setVisible(true);

            f.createNewFile();
            stat.add(new GameStat(playerName,"WIN",formatter.format(date)));
            resMan.fileWriter(stat);
        }
    }
    @FXML
    public void handleResetButton(ActionEvent actionEvent) throws IOException {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Logger.info("Resetting game");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/opening.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleFinishButton(ActionEvent actionEvent) throws IOException {
        var buttonText = ((Button) actionEvent.getSource()).getText();
        Logger.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            Logger.info("");
        }
        Logger.debug("Saving result");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
