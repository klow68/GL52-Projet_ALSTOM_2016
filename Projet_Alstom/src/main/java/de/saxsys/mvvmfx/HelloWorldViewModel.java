package de.saxsys.mvvmfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloWorldViewModel implements ViewModel {

    private StringProperty helloMessage = new SimpleStringProperty("Hello World");
    
    public void changeText(String texte) {
    	System.out.println(texte);
    	setHelloMessage(texte);
    }
    
    public StringProperty helloMessage(){
        return helloMessage;
    }

    public String getHelloMessage(){
        return helloMessage.get();
    }

    public void setHelloMessage(String message){
        helloMessage.set(message);
    }

}