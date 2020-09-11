import Domain.User;
import ServiceInterface.Services;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class loginController {
    private Services server;
    mainController appController;
    private Stage mainStage;
    private Stage currentStage;
    private Parent mainAppParent;
    private User user;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button cancelButton;
    public loginController() {

    }

    public void setServer(Services server) {
        this.server = server;
    }

    @FXML
    private void login(){
        String username = usernameTextfield.getText();
        String password = passwordField.getText();

        user = new User(1,username,password,0);

        try{
            System.out.println("Controller sending to server : " + username+" "+password);
            user = server.login(user,appController);

            //ii transmit userul mainWindow-ului ca sa stie cu ce are de a face
            appController.setUser(this.user);

            currentStage = (Stage) this.cancelButton.getScene().getWindow();

            //when closing the main stage,the user has to be logged out and the loggin window has to show up
            mainStage.setOnCloseRequest(e->{
                try {
                    server.logout(user,appController);
                } catch (Exception ex) {
                    Utils.getAlert(Alert.AlertType.ERROR,"Error",ex.getMessage()).show();
                }
                currentStage.show();
                mainStage.close();
            });

            mainStage.show();
            currentStage.hide();

        } catch (Exception e) {
            Utils.getAlert(Alert.AlertType.ERROR,"Error","User doesn't exist.").show();
        }


    }

    @FXML
    private void register(){
        String username = usernameTextfield.getText();
        String password = passwordField.getText();

        user = new User(username,password,0);
        try {
            server.register(user);
            Utils.getAlert(Alert.AlertType.INFORMATION,"Succes!","Register succesful!").show();
        } catch (Exception e) {
            Utils.getAlert(Alert.AlertType.ERROR,"Error",e.getMessage()).show();
        }
    }

    @FXML
    private void cancelMethod(){
        Stage currentStage = (Stage) this.cancelButton.getScene().getWindow();
        Platform.exit();
        System.exit(0);
        //currentStage.close();
    }



    public void setController(mainController mController) {
        appController = mController;
    }

    public void setParent(Parent appP) {
        mainAppParent = appP;

        mainStage= new Stage();
        mainStage.setScene(new Scene(mainAppParent));

    }
}
