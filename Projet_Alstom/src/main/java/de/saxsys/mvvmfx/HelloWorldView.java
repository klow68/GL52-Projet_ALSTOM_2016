package de.saxsys.mvvmfx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

public class HelloWorldView implements FxmlView<HelloWorldViewModel>, Initializable  {

    @FXML
    private Label helloLabel;
    
    @FXML
    public void changeText(){
    	viewModel.changeText();
    };
    
    
    @FXML
    private AnchorPane menuViewPane;

    @InjectViewModel
    private HelloWorldViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessage());
    }
}