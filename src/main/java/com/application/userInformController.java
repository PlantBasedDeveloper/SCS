package com.application;
import java.net.URL;
import java.util.ResourceBundle;

import com.application.util.ConfigurationStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class userInformController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSave;
    
    @FXML
    private Button btnClose;

    @FXML
    private TextField address;

    @FXML
    private TextField bloodType;

    @FXML
    private TextField dateBirth;

    @FXML
    private TextField emailPerson;

    @FXML
    private TextField firstName;

    @FXML
    private TextField gender;

    @FXML
    private TextField lastName;

    @FXML
    private TextField mobNumber;

    @FXML
    private TextField namePerson;


    @FXML
    void SaveUserInfo(ActionEvent event) {
    	ConfigurationStorage.setFIRST_NAME(firstName.textProperty().getValue());
    	ConfigurationStorage.setLAST_NAME(lastName.textProperty().getValue());
    	ConfigurationStorage.setBIRTH_DATE(dateBirth.textProperty().getValue());
    	ConfigurationStorage.setADDRESS(address.textProperty().getValue());
    	ConfigurationStorage.setPHONE_NUMBER(mobNumber.textProperty().getValue());
    	ConfigurationStorage.setBLOOD_TYPE(bloodType.textProperty().getValue());
    	ConfigurationStorage.setCONTACT_PERSON_NAME(namePerson.textProperty().getValue());
    	ConfigurationStorage.setCONTACT_PERSON_EMAIL(emailPerson.textProperty().getValue());
    	ConfigurationStorage.setGENDER(gender.textProperty().getValue());
    }
    
    @FXML
    void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'userinfo.fxml'.";
        assert btnClose != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'userinfo.fxml'.";

        this.firstName.textProperty().setValue(String.valueOf(ConfigurationStorage.getFIRST_NAME()));
        this.lastName.textProperty().setValue(String.valueOf(ConfigurationStorage.getLAST_NAME()));
        this.dateBirth.textProperty().setValue(String.valueOf(ConfigurationStorage.getBIRTH_DATE()));
        this.address.textProperty().setValue(String.valueOf(ConfigurationStorage.getADDRESS()));
        this.mobNumber.textProperty().setValue(String.valueOf(ConfigurationStorage.getPHONE_NUMBER()));
        this.bloodType.textProperty().setValue(String.valueOf(ConfigurationStorage.getBLOOD_TYPE()));
        this.namePerson.textProperty().setValue(String.valueOf(ConfigurationStorage.getCONTACT_PERSON_NAME()));
        this.emailPerson.textProperty().setValue(String.valueOf(ConfigurationStorage.getCONTACT_PERSON_EMAIL()));
        this.gender.textProperty().setValue(String.valueOf(ConfigurationStorage.getGENDER()));
    }

}


