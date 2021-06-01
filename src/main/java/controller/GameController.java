package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.javafx.ControllerHelper;

import javax.inject.Inject;
import java.io.IOException;

public class GameController {
    @Inject
    private FXMLLoader fxmlLoader;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpFinishButton;

    public void handleResetButton(ActionEvent actionEvent)  {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Logger.info("Resetting game");
        //resetGame();
    }

    public void handleGiveUpFinishButton(ActionEvent actionEvent) throws IOException {
        var buttonText = ((Button) actionEvent.getSource()).getText();
        Logger.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            //stopwatch.stop();
            Logger.info("The game has been given up");
        }

        Logger.debug("Saving result");
        //gameResultDao.persist(createGameResult());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

}
