package ui.Controller;

import api.ATM.ATM;
import api.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class  ActionsController implements Initializable, Runnable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Stage stage = new Stage();
        Controller.successOrFailureWindow("Successfully logged in.", stage);
        Controller.pauseTransition(stage);
    }

    @Override
    public void run() {
    }

    public void logOut(ActionEvent event) throws Exception {
        Database.updateBalance(ATM.currentUser.getID(), ATM.currentUser.getBalance());
        Parent registerParent = FXMLLoader.load(getClass().getResource("/ui/fxml/MainWindow.fxml"));
        Scene registerScene = new Scene(registerParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.show();
    }

    public void showBalanceAction() {
        Stage stage = new Stage();
        Controller.successOrFailureWindow("Current balance: " + ATM.currentUser.getBalance() + " €", stage);
        Controller.pauseTransition(stage);
    }

    public void depositAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/DepositWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Deposit");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void withdrawAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/WithdrawWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Withdraw");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}