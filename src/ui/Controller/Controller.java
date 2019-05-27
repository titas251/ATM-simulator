package ui.Controller;

import api.ATM.ATM;
import api.Database.Database;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable, Runnable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void run() {
    }

    public void Register(ActionEvent event) throws Exception {
        Parent registerParent = FXMLLoader.load(getClass().getResource("/ui/fxml/RegisterWindow.fxml"));
        Scene registerScene = new Scene(registerParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registerScene);
        window.show();
    }

    public void Exit(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }

    public void Delete(ActionEvent event) {
        Stage stage = new Stage();
        Stage stage2 = new Stage();
        successOrFailureWindow("Please scan your card.", stage);
        Thread logInToSystem = new Thread(deleteUser(stage, stage2));
        logInToSystem.start();
    }

    public void changeWhenLogIn(ActionEvent event) {
        Stage stage = new Stage();
        Stage stage2 = new Stage();
        successOrFailureWindow("Please scan your card.", stage);
        Thread logInToSystem = new Thread(logIn(stage, stage2, event));
        logInToSystem.start();
    }

    public Runnable deleteUser(Stage stage, Stage stage2) {
        Runnable runnable = () -> {
            if (Database.deleteUser()) {
                Platform.runLater(
                        () -> {
                            stage.close();
                            successOrFailureWindow("Successfully deleted.", stage2);
                            pauseTransition(stage2);
                        }
                );
            } else {
                Platform.runLater(
                        () -> {
                            stage.close();
                            successOrFailureWindow("Failed to delete. Try again.", stage2);
                            pauseTransition(stage2);
                        }
                );
            }
        };
        return runnable;
    }

    public Runnable logIn(Stage stage, Stage stage2, ActionEvent event) {
        Runnable runnable = () -> {
            if (Database.logUserIn()) {
                Platform.runLater(
                        () -> {
                            stage.close();
                            try {
                                ActionWindow(event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else {
                Platform.runLater(
                        () -> {
                            stage.close();
                            successOrFailureWindow("Failed to scan your card. Try again.", stage2);
                            pauseTransition(stage2);
                        }
                );
            }
        };
        return runnable;
    }

    public static void successOrFailureWindow(String message, Stage stage) {
        stage.setMinHeight(100);
        stage.setMinWidth(250);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(" ");
        StackPane layout = new StackPane();
        Text label = new Text(message);
        layout.getChildren().add(label);
        Scene LoginScene = new Scene(layout, 250, 100);
        stage.setScene(LoginScene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }


    public void ActionWindow (ActionEvent event) throws Exception{
        Parent ActionWindowParent = FXMLLoader.load(getClass().getResource("/ui/fxml/Actions.fxml"));
        Scene ActionScene = new Scene(ActionWindowParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ActionScene);
        window.show();
        window.setOnCloseRequest((WindowEvent event1) -> {
            Database.updateBalance(ATM.currentUser.getID(), ATM.currentUser.getBalance());
        });
    }

    public static void pauseTransition(Stage stage) {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> stage.close());
        pause.play();
    }
//--------------------------------------------------------------------------------------





}
