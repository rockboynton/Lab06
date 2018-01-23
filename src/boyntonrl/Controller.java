/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 6 Exceptions
 * Name: Rock Boynton
 * Created: 1/18/18
 */

package boyntonrl;

import edu.msoe.se1021.Lab6.WebsiteTester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*; // use of * for many different controls

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Controller class for the Lab06 (WebsiteTester) JavaFX application
 */
public class Controller {

    @FXML
    private TextField urlTextField;
    @FXML
    private Button analyzeButton;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField downloadTimeTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField hostTextField;
    @FXML
    private TextField timeoutTextField;
    @FXML
    private Button setButton;
    @FXML
    private TextArea contentTextArea;

    private WebsiteTester tester = new WebsiteTester();

    @FXML
    private void analyze(ActionEvent event) {
        try {
            tester.setTimeout(timeoutTextField.getText());
            tester.openURL(urlTextField.getText());
            tester.openConnection();
            tester.downloadText();
            sizeTextField.setText("" + tester.getSize());
            downloadTimeTextField.setText("" + tester.getDownloadTime());
            hostTextField.setText(tester.getHostname());
            portTextField.setText("" + tester.getPort());
            contentTextArea.setText(tester.getContent());
        } catch (MalformedURLException e) {
            showInvalidURLAlert();
        } catch (SocketTimeoutException e) {
            // ask if user wants to set a new timeout
            Optional<ButtonType> choice = showSocketTimeoutAlert();
            // sets new timeout if they chose to set new timeout
            if (choice.get() == ButtonType.OK) {
                Optional<String> newTimeout = showSetTimeoutDialog();
                setNewTimeout(newTimeout, event);
            }
        } catch (UnknownHostException e) {
            showInvalidHostAlert();
        } catch (IOException e) {
            showFileNotFoundAlert();
        }
    }

    private void setNewTimeout(Optional<String> newTimeout, ActionEvent event) {
        newTimeout.ifPresent(timeout -> {
            try {
                if (Integer.parseInt(timeout) <= 0) {
                    throw new NumberFormatException();
                }
                timeoutTextField.setText(timeout);
                tester.setTimeout(timeoutTextField.getText());
                analyze(event);
            } catch (NumberFormatException invalidNum) {
                showInvalidTimeoutAlert();
            }
        });
    }

    private Optional<String> showSetTimeoutDialog() {
        TextInputDialog setTimeoutDialog = new TextInputDialog("");
        setTimeoutDialog.setTitle("Set timeout");
        setTimeoutDialog.setHeaderText("Set extended timeout");
        setTimeoutDialog.setContentText("Desired timeout: ");
        return setTimeoutDialog.showAndWait();
    }

    private Optional<ButtonType> showSocketTimeoutAlert() {
        Alert timeoutDialog = new Alert(Alert.AlertType.CONFIRMATION, "");
        timeoutDialog.setTitle("Timeout Dialog");
        timeoutDialog.setHeaderText("Wait Longer?");
        timeoutDialog.setContentText("There has been a timeout reaching the site. Click OK to " +
                "extend the timeout period?");
        return timeoutDialog.showAndWait();
    }

    private void showInvalidHostAlert() {
        Alert invalidHostAlert = new Alert(Alert.AlertType.ERROR, "Error: unable to " +
                "reach the host " + urlTextField.getText());
        invalidHostAlert.setTitle("Error Dialog");
        invalidHostAlert.setHeaderText("Host Error");
        invalidHostAlert.showAndWait();
        urlTextField.setText("");
    }

    private void showInvalidURLAlert() {
        Alert invalidURLAlert = new Alert(Alert.AlertType.ERROR, "The URL entered in " +
                "the text box is invalid");
        invalidURLAlert.setTitle("Error Dialog");
        invalidURLAlert.setHeaderText("URL Error");
        invalidURLAlert.showAndWait();
        urlTextField.setText("");
    }

    private void showFileNotFoundAlert() {
        Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR, "Error: File " +
                "not found on the server " + urlTextField.getText());
        fileNotFoundAlert.setTitle("Error Dialog");
        fileNotFoundAlert.setHeaderText("URL Error");
        fileNotFoundAlert.showAndWait();
        urlTextField.setText("");
    }

    @FXML
    private void setTimeout(ActionEvent e) {
        try {
            if (Integer.parseInt(timeoutTextField.getText()) <= 0) {
                throw new NumberFormatException();
            }
            tester.setTimeout(timeoutTextField.getText());
        } catch (NumberFormatException invalidNum) {
            showInvalidTimeoutAlert();
        }
    }

    private void showInvalidTimeoutAlert() {
        Alert invalidTimeout = new Alert(Alert.AlertType.ERROR, "The timeout entered" +
                " in the text box is invalid");
        invalidTimeout.setTitle("Error Dialog");
        invalidTimeout.setHeaderText("Invalid Timeout");
        invalidTimeout.showAndWait();
        timeoutTextField.setText(tester.getTimeout());
    }
}
