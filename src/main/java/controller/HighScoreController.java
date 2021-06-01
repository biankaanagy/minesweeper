package controller;

import highscores.GameStat;
import highscores.ResultManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class HighScoreController {

    @FXML
    private Button resetGame;

    @FXML
    private TableView<GameStat> highScoreTable;

    @FXML
    private TableColumn<GameStat, String> playername;

    @FXML
    private TableColumn<GameStat, String> win;

    @FXML
    private TableColumn<GameStat, String> date;

    @FXML
    private void initialize() throws IOException {
        ResultManager res = new ResultManager();
        ArrayList<GameStat> stat = res.fileReader();

        ObservableList<GameStat> data = FXCollections.observableArrayList();

        playername.setCellValueFactory(new PropertyValueFactory<>("playername"));
        win.setCellValueFactory(new PropertyValueFactory<>("win"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        data.addAll(FXCollections.observableArrayList(stat));
        highScoreTable.setItems(data);
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
}
