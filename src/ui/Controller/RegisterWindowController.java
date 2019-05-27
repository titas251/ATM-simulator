package ui.Controller;

import api.Database.Database;
import api.ATM.User;
import api.RFID.RFID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindowController implements Initializable, Runnable {

    @FXML
    private TextField name, surname, email, pin, balance;
    @FXML
    private Label nameLabel, surnameLabel, emailLabel, pinLabel, balanceLabel, ConfirmationLabel;

    Boolean nameValid = false, surnameValid = false, emailValid = false, pinValid = false, balanceValid = false;

    @Override
    public void run() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.textProperty().addListener((observable, oldText, newText) -> {
            nameValid = false;
            if (newText.trim().length() == 0) nameLabel.setText ("*Field is required");
            else if (newText.trim().length() > 255) nameLabel.setText ("*Name is too long");
            else if (!newText.trim().chars().allMatch(Character::isLetter)) nameLabel.setText ("*Name can only contain letters");
            else {
                nameLabel.setText ("");
                nameValid = true;
            }
        });

        surname.textProperty().addListener((observable, oldText, newText) -> {
            surnameValid = false;
            if (newText.trim().length() == 0) surnameLabel.setText ("*Field is required");
            else if (newText.trim().length() > 255) surnameLabel.setText ("*Name is too long");
            else if (!newText.trim().chars().allMatch(Character::isLetter)) surnameLabel.setText ("*Surname can only contain letters");
            else {
                surnameLabel.setText ("");
                surnameValid = true;
            }
        });

        email.textProperty().addListener((observable, oldText, newText) -> {
            emailValid = false;
            if (newText.trim().length() == 0) emailLabel.setText ("*Field is required");
            else if (newText.trim().length() > 255) emailLabel.setText("*Email address is too long");
            else if (!EmailValidator.getInstance().isValid(newText.trim())) emailLabel.setText ("*Enter a valid email address");
            else {
                emailLabel.setText("");
                emailValid = true;
            }
        });

        pin.textProperty().addListener((observable, oldText, newText) -> {
            pinValid = false;
            if (newText.trim().length() == 0) pinLabel.setText ("*Field is required");
            else if (newText.trim().length() > 8 || newText.trim().length() < 4) pinLabel.setText ("*Pin code must be between 4 and 8 digits");
            else if (!newText.trim().chars().allMatch(Character::isDigit)) pinLabel.setText ("*Pin code can only contain digits");
            else {
                pinLabel.setText ("");
                pinValid = true;
            }
        });

        balance.textProperty().addListener((observable, oldText, newText) -> {
            balanceValid = false;
            if (newText.trim().length() == 0) balanceLabel.setText ("*Field is required");
            else if (!newText.trim().matches("[0-9]*\\.{0,1}[0-9]{0,2}")) balanceLabel.setText ("*Invalid amount");
            else if (newText.trim().length() > 15) balanceLabel.setText ("*Initial deposit is too large");
            else {
                balanceLabel.setText ("");
                balanceValid = true;
            }
        });
    }

    public void takeInformationWhenRegister() {
        if (!(nameValid && surnameValid && emailValid && pinValid && balanceValid)) {
            ConfirmationLabel.setText ("Please correctly fill in the form before submitting it");
            return;
        }
        ConfirmationLabel.setText ("");
        Stage stage_1 = new Stage();
        Stage stage_2 = new Stage();

        registerWindowStatus("Please scan your card", stage_1);
        Runnable runnable = () -> {
            if (Database.createUser(new User(RFID.readID(), name.getText().trim(), surname.getText().trim(), email.getText().trim(),
                    Integer.parseInt(pin.getText().trim()), balance.getText().trim()))) {

                Platform.runLater(
                        () -> {
                            cleanTextFields();
                            stage_1.hide();
                            registerWindowStatus("Registration successful.", stage_2);
                            Controller.pauseTransition(stage_2);
                        }
                );
            } else {
                Platform.runLater(
                        () -> {
                            cleanTextFields();
                            stage_1.hide();
                            registerWindowStatus("Registration unsuccessful, please try again.", stage_2);
                            Controller.pauseTransition(stage_2);

                        }
                );
            }
        };
        Thread register = new Thread(runnable);
        register.start();
    }

    public void cleanTextFields() {
        name.clear();
        surname.clear();
        pin.clear();
        email.clear();
        balance.clear();
    }


    public void registerWindowStatus(String message, Stage stage) {
        stage.setMinHeight(100);
        stage.setMinWidth(300);
        stage.setTitle(" ");
        stage.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        Text label = new Text(message);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        Scene registerStatus = new Scene(layout, 300, 100);
        stage.setScene(registerStatus);
        stage.show();
    }


    public void backToMainWindow(ActionEvent event) throws Exception {
        Parent MainParent = FXMLLoader.load(getClass().getResource("/ui/fxml/MainWindow.fxml"));
        Scene LoginScene = new Scene(MainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(LoginScene);
        window.show();
    }

}
