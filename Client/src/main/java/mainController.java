import Domain.Raspuns;
import Domain.User;
import ServiceInterface.Observer;
import ServiceInterface.Services;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class mainController extends UnicastRemoteObject implements Observer, Serializable {
    Services service;
    private User user;
    @FXML
    private TextField telefonText;
    @FXML
    private TextField tigariText;
    @FXML
    private TextField masinaText;
    @FXML
    private Label letterLabel;
    @FXML
    private Label punctajLabel;
    @FXML
    private Button startButton;

    private Parent addParent;
    public mainController() throws RemoteException {
        System.out.println("Constructor controller principal ---");
    }

    public void setMainService(Services mainService) {
        this.service = mainService;
    }

    @Override
    public void clearTextbox(){
        this.telefonText.clear();
        this.masinaText.clear();
        this.tigariText.clear();
    }


    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    void startGame(){
        try {
            clearTextbox();
            service.startGame();

        } catch (Exception e) {
            Utils.getAlert(Alert.AlertType.ERROR,"Eroare!",e.getMessage()).show();
        }
    }

    @FXML
    void sendAnswer(){
        String masina = masinaText.getText().toLowerCase();
        String tigari = tigariText.getText().toLowerCase();
        String telefon = telefonText.getText().toLowerCase();
        masina = masina.substring(0,1).toUpperCase() + masina.substring(1);
        tigari =tigari.substring(0,1).toUpperCase() + tigari.substring(1);
        telefon = telefon.substring(0,1).toUpperCase() + telefon.substring(1);
        Raspuns rasp = new Raspuns(user.getIdUser(),masina,tigari,telefon,0,user.getUsername());
        try {
            service.checkAnswer(this,rasp);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    telefonText.setVisible(false);
                    masinaText.setVisible(false);
                    tigariText.setVisible(false);
                    letterLabel.setText("Please wait..");
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receiveLetter(char letter) throws RemoteException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                letterLabel.setText("Litera este: " + letter);
                letterLabel.setVisible(true);
                telefonText.setVisible(true);
                tigariText.setVisible(true);
                masinaText.setVisible(true);
            }
        });
    };

    @Override
    public void receivePunctaj(int punctaj) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Utils.getAlert(Alert.AlertType.INFORMATION,"Punctaj:","Felicitari, ai obtinut: " + punctaj);
                punctajLabel.setText("Punctaj: " + punctaj);
            }
        });

    }

    @Override
    public void disableStart() {
        this.startButton.setDisable(true);
    }

    @Override
    public void enableStart() {
        this.startButton.setDisable(false);
    }
}
