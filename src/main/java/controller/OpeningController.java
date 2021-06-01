package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;
import java.io.IOException;

public class OpeningController {

    @FXML
    private TextField playerNameTextField;

    @FXML
    private Label errorLabel;

    @FXML
    public void startAction(ActionEvent actionEvent) throws IOException {
        if (playerNameTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a name!");
        } else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().setPlayerName(playerNameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("The user's name is set to {}, loading game scene", playerNameTextField.getText());
        }
    }
    

}
