package boyntonrl;

import edu.msoe.se1021.Lab6.WebsiteTester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;

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

    private WebsiteTester tester;

    @FXML
    private void analyze(ActionEvent event) {
        try {
            tester = new WebsiteTester();
            tester.openURL(urlTextField.getText());
            tester.openConnection();
            tester.downloadText();
            sizeTextField.setText("" + tester.getSize());
            timeoutTextField.setText("" + tester.getDownloadTime());
            hostTextField.setText(tester.getHostname());
            portTextField.setText("" + tester.getPort());
            contentTextArea.setText(tester.getContent());
        } catch (MalformedURLException e) {
            Alert invalidURLAlert = new Alert(Alert.AlertType.ERROR, "The URL entered in the text box is invalid");
            invalidURLAlert.setTitle("Error Dialog");
            invalidURLAlert.setHeaderText("URL Error");
            invalidURLAlert.showAndWait();
            urlTextField.setText("");
        } catch (SocketTimeoutException e) {
            Alert timeoutDialog = new Alert(Alert.AlertType.CONFIRMATION, "");
            timeoutDialog.setTitle("Timeout Dialog");
            timeoutDialog.setHeaderText("Wait Longer?");
            timeoutDialog.setContentText("There has been a timeout reaching the site. Click OK to extend the timeout period?");
            Optional<ButtonType> choice = timeoutDialog.showAndWait();
            if (choice.get() == ButtonType.OK) {
                TextInputDialog setTimeoutDialog = new TextInputDialog("");
                setTimeoutDialog.setTitle("Set timeout");
                setTimeoutDialog.setHeaderText("Set extended timeout");
                setTimeoutDialog.setContentText("Desired timeout: ");
                Optional<String> newTimeout = setTimeoutDialog.showAndWait();
                newTimeout.ifPresent(name -> timeoutTextField.setText(String.valueOf(newTimeout))); // TODO test if that is a valid timeout
            }
        } catch (UnknownHostException e) {
            // TODO
        } catch (IOException e) {
            Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR, "Error: File not found on the server " + urlTextField.getText() );
            fileNotFoundAlert.setTitle("Error Dialog");
            fileNotFoundAlert.setHeaderText("URL Error");
            fileNotFoundAlert.showAndWait();
            urlTextField.setText("");
        }
    }

    @FXML
    private void setTimeout(ActionEvent event) {
        tester.setTimeout(timeoutTextField.getText());
    }
}
