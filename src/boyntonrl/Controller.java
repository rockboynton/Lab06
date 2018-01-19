package boyntonrl;

import edu.msoe.se1021.Lab6.WebsiteTester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;

public class Controller {

    @FXML
    private TextField URLTextField;
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
//            tester = new WebsiteTester();
            tester.setTimeout(timeoutTextField.getText());
            tester.openURL(URLTextField.getText());
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
            Alert timeoutDialog = new Alert(Alert.AlertType.CONFIRMATION, "");
            timeoutDialog.setTitle("Timeout Dialog");
            timeoutDialog.setHeaderText("Wait Longer?");
            timeoutDialog.setContentText("There has been a timeout reaching the site. Click OK to " +
                    "extend the timeout period?");
            Optional<ButtonType> choice = timeoutDialog.showAndWait();
            if (choice.get() == ButtonType.OK) {
                TextInputDialog setTimeoutDialog = new TextInputDialog("");
                setTimeoutDialog.setTitle("Set timeout");
                setTimeoutDialog.setHeaderText("Set extended timeout");
                setTimeoutDialog.setContentText("Desired timeout: ");
                Optional<String> newTimeout = setTimeoutDialog.showAndWait();
                newTimeout.ifPresent(timeout -> {
                    try {
                        tester.setTimeout(String.valueOf(newTimeout));
                        timeoutTextField.setText(String.valueOf(newTimeout));
                    } catch (NumberFormatException invalidNum) {
                        showInvalidTimeoutAlert();
                    }
                }); // TODO test if that is a valid timeout timeoutTextField.setText(String.valueOf(newTimeout))

            }
        } catch (UnknownHostException e) {
            showInvalidHostAlert();
        } catch (IOException e) {
            showFileNotFoundAlert();
        }
    }

    private void showInvalidHostAlert() {
        Alert invalidHostAlert = new Alert(Alert.AlertType.ERROR, "Error: unable to reach the " +
                "host " + URLTextField.getText());
        invalidHostAlert.setTitle("Error Dialog");
        invalidHostAlert.setHeaderText("Host Error");
        invalidHostAlert.showAndWait();
        URLTextField.setText("");
    }

    private void showInvalidURLAlert() {
        Alert invalidURLAlert = new Alert(Alert.AlertType.ERROR, "The URL entered in the text box is invalid");
        invalidURLAlert.setTitle("Error Dialog");
        invalidURLAlert.setHeaderText("URL Error");
        invalidURLAlert.showAndWait();
        URLTextField.setText("");
    }

    private void showFileNotFoundAlert() {
        Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR, "Error: File " +
                "not found on the server " + URLTextField.getText());
        fileNotFoundAlert.setTitle("Error Dialog");
        fileNotFoundAlert.setHeaderText("URL Error");
        fileNotFoundAlert.showAndWait();
        URLTextField.setText("");
    }

    @FXML
    private void setTimeout(ActionEvent event) {

        // TODO make sure number is greater than 0
        if () {
            try {
                tester.setTimeout(timeoutTextField.getText());
            } catch (NumberFormatException e) {
                showInvalidTimeoutAlert();
            }
        } else {
        }
        if (temp < 0) {
            throw new NumberFormatException("Timeout must be greater than or equal to 0.");
        } else {
            this.timeout = temp;
        }
    }

    private void showInvalidTimeoutAlert() {
        Alert invalidTimeout = new Alert(Alert.AlertType.ERROR, "The timeout entered in the " +
                "text box is invalid");
        invalidTimeout.setTitle("Error Dialog");
        invalidTimeout.setHeaderText("Invalid Timeout");
        invalidTimeout.showAndWait();
        timeoutTextField.setText(tester.getTimeout());
    }
}
