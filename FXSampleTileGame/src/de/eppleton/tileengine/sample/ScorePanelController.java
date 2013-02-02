/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.tileengine.sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author antonepple
 */
public class ScorePanelController extends AnchorPane implements Initializable {

    @FXML ProgressBar progressBar;
    
    public ScorePanelController() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(ScorePanelController.class.getResource(
                "ScorePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        progressBar.setProgress(.5);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private static final Logger LOG = Logger.getLogger(ScorePanelController.class.getName());
}
