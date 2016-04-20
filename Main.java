package antgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
/**
 *
 * @author Abdullah Rowaished
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        Parent root = null;
        Scene scene = null;
        //Image image = new Image("age-of-ants.jpg");
        try {
            root = FXMLLoader.load(getClass().getResource("Launcher.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
