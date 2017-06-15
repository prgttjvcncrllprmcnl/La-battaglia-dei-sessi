import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
/**
 * Created by Daniel on 14/05/2017.
 */
public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }                  // termina. Può essere invocato una sola volta

    /**
     * Metodo che lancia la finestra di input
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane mainPane = (Pane) FXMLLoader.load(Test.class.getResource("Input.fxml"));
        primaryStage.setTitle("La battaglia dei sessi");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }
}
