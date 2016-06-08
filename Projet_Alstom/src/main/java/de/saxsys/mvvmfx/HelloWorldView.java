package de.saxsys.mvvmfx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class HelloWorldView implements FxmlView<HelloWorldViewModel>, Initializable  {

    @FXML
    private Label helloLabel;
    
    @FXML
    private TextArea textAreaP;
    
    @FXML
    public void changeText(){
    	System.out.println(this.textAreaP.getText());
    	
    	viewModel.changeText(this.textAreaP.getText());
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
