package ui.Controller;

import api.ATM.ATM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class  WithdrawController implements Initializable {
    @FXML
    Label amountLabel, pinLabel, confirmationLabel;
    @FXML
    TextField amountField, pinField;

    Boolean amountValid = false, pinValid = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountField.textProperty().addListener((observable, oldText, newText) -> {
            amountValid = false;
            if (newText.trim().length() == 0) amountLabel.setText ("*Field is required");
            else if (!newText.trim().matches("[0-9]*\\.{0,1}[0-9]{0,2}")) amountLabel.setText ("*Invalid amount");
            else if (newText.trim().length() > 15) amountLabel.setText ("*Withdrawal is too large");
            else {
                amountLabel.setText ("");
                amountValid = true;
            }
        });

        pinField.textProperty().addListener((observable, oldText, newText) -> {
            pinValid = false;
            if (newText.trim().length() == 0) pinLabel.setText ("*Field is required");
            else if (newText.trim().length() > 8 || newText.trim().length() < 4) pinLabel.setText ("*Pin code must be between 4 and 8 digits");
            else if (!newText.trim().chars().allMatch(Character::isDigit)) pinLabel.setText ("*Pin code can only contain digits");
            else {
                pinLabel.setText ("");
                pinValid = true;
            }
        });
    }

    public void Withdraw(ActionEvent event) {
        if (!(amountValid && pinValid)) {
            confirmationLabel.setText ("Please correctly fill in all fields before submitting");
            return;
        }

        if (!pinField.getText().trim().equals(Integer.toString(ATM.currentUser.getPin()))) {
            confirmationLabel.setText ("Incorrect pin code");
            return;
        }

        BigDecimal request = new BigDecimal(amountField.getText().trim()),
                balance = new BigDecimal(ATM.currentUser.getBalance().trim());
        if (balance.compareTo(request) < 0) {
            confirmationLabel.setText("Insufficient funds in the account");
            return;
        }

        ATM.withdraw(amountField.getText().trim());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }
}