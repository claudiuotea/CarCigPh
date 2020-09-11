import ServiceInterface.Services;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {
    private Stage primaryStage;

    private static int defaultPort=1099;
    private static String defaultServer = "localhost";
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        Services server = (Services) factory.getBean("appServer");

        //incarc fxml-ul
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));

        Parent root = loginLoader.load();


        loginController ctrl = loginLoader.getController();
        ctrl.setServer(server);

        Parent appRoot=appLoader.load();
        mainController mainCtrl = appLoader.getController();
        mainCtrl.setMainService(server);

        ctrl.setController(mainCtrl);
        ctrl.setParent(appRoot);

        primaryStage.setOnCloseRequest(e-> {
            Platform.exit();
            System.exit(0);
        } );
        primaryStage.setTitle("My app");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
