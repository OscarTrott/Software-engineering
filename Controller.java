package antgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Abdullah Rowaished
 */
public class Controller implements Initializable {
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    /**
     * hides the Launcher panel as it opens a new Tourney panel via clicking 'Start' from the Launcher panel.
     */
    @FXML private Button quitButton; //the 'Quit' button in Launcher.fxml
    @FXML public void startGame() {
        Stages.stages.push(((Stage)quitButton.getScene().getWindow()));
        Parent root = null;
        Scene scene = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Tourney.fxml"));
            scene = new Scene(root);
        } catch (NullPointerException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        Stages.stages.push(stage);
        Stages.stages.pop().show();
        Stages.stages.peek().hide();
        Stages.stages.push(stage);
        System.out.println("size before: " + Stages.stages.size());
    }
    /**
     * closes all the panels associated with the program via clicking on the 'Quit' button from the Launcher panel.
     */
    @FXML public void quitGame() {
        Stages.stages.push((Stage)quitButton.getScene().getWindow());
        for(Stage stage : Stages.stages) {
            stage.close();
        }
        Stages.stages.clear();
    }
    
    
    /**
     * opens a file chooser after clicking on the 'Brain' button from the Subtourney Panel.
     */
    @FXML public void firstBrain() {
        FileChooser fc = new FileChooser();
        Window win = new Popup();
        fc.setTitle("Pick your Brain!");
        fc.showOpenDialog(win);
    }
    @FXML private Button homeButton;
    @FXML public void goHome() {
        System.out.println("size after: " + Stages.stages.size());
        Stages.stages.pop().close();
        Stages.stages.pop().show();
    }
}
