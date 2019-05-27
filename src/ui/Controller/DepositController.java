package ui.Controller;

import api.ATM.ATM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class  DepositController implements Initializable {
    @FXML
    Label amountLabel, confirmationLabel;
    @FXML
    TextField amountField;

    Boolean amountValid = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountField.textProperty().addListener((observable, oldText, newText) -> {
            amountValid = false;
            if (newText.trim().length() == 0) amountLabel.setText ("*Field is required");
            else if (!newText.trim().matches("[0-9]*\\.{0,1}[0-9]{0,2}")) amountLabel.setText ("*Invalid amount");
            else if (newText.trim().length() > 15) amountLabel.setText ("*Deposit is too large");
            else {
                amountLabel.setText ("");
                amountValid = true;
            }
        });
    }

    public void Deposit(ActionEvent event) {
        if (!amountValid) {
            confirmationLabel.setText ("Please correctly fill in all fields before submitting");
            return;
        }

        ATM.deposit(amountField.getText().trim());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }
}