package com.application;

import java.net.URL;
import java.util.ResourceBundle;

import com.application.util.ConfigurationStorage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSave;

    @FXML
    private TextField COUNT_SEC;

    @FXML
    private TextField FALL_ANGLE;

    @FXML
    private TextField FORCE_IMPACT_D;

    @FXML
    private TextField FORCE_IMPACT_U;

    @FXML
    private TextField G_SCALE;

    @FXML
    private TextField HELP_REQUEST_DELAY;

    @FXML
    private TextField IMPACT_PASS;

    @FXML
    private TextField IMPACT_POW;

    @FXML
    private TextField LAYING_POW;

    @FXML
    private TextField SKIP_MEASURE;


    @FXML
    void closeWindow(ActionEvent event) {
    	 Stage stage = (Stage) btnClose.getScene().getWindow();
         stage.close();
    }

    @FXML
    void SaveSettings(ActionEvent event) {
    	   	
    	ConfigurationStorage.setIMPACT_POW(Double.valueOf(IMPACT_POW.textProperty().getValue()));
    	ConfigurationStorage.setIMPACT_PASS(Integer.valueOf(IMPACT_PASS.textProperty().getValue()));
    	ConfigurationStorage.setLAYING_POW(Double.valueOf(LAYING_POW.textProperty().getValue()));
    	ConfigurationStorage.setSKIP_MEASURE(Integer.valueOf(SKIP_MEASURE.textProperty().getValue()));
    	ConfigurationStorage.setFALL_ANGLE(Double.valueOf(FALL_ANGLE.textProperty().getValue()));
    	ConfigurationStorage.setCOUNT_SEC(Integer.valueOf(COUNT_SEC.textProperty().getValue()));
    	ConfigurationStorage.setFORCE_IMPACT_D(Double.valueOf(FORCE_IMPACT_D.textProperty().getValue()));
    	ConfigurationStorage.setFORCE_IMPACT_U(Double.valueOf(FORCE_IMPACT_U.textProperty().getValue()));
    	ConfigurationStorage.setG_SCALE(Double.valueOf(G_SCALE.textProperty().getValue()));
    	ConfigurationStorage.setHELP_REQUEST_DELAY(Integer.valueOf(HELP_REQUEST_DELAY.textProperty().getValue()));
    }

    @FXML
    void SetDefault(ActionEvent event) {
    	this.IMPACT_POW.textProperty().setValue(String.valueOf(ConfigurationStorage.getIMPACT_POW()));
        this.IMPACT_PASS.textProperty().setValue(String.valueOf(ConfigurationStorage.getIMPACT_PASS()));
        this.LAYING_POW.textProperty().setValue(String.valueOf(ConfigurationStorage.getLAYING_POW()));
        this.SKIP_MEASURE.textProperty().setValue(String.valueOf(ConfigurationStorage.getSKIP_MEASURE()));
        this.FALL_ANGLE.textProperty().setValue(String.valueOf(ConfigurationStorage.getFALL_ANGLE()));
        this.COUNT_SEC.textProperty().setValue(String.valueOf(ConfigurationStorage.getCOUNT_SEC()));
        this.FORCE_IMPACT_D.textProperty().setValue(String.valueOf(ConfigurationStorage.getFORCE_IMPACT_D()));
        this.FORCE_IMPACT_U.textProperty().setValue(String.valueOf(ConfigurationStorage.getFORCE_IMPACT_U()));
        this.G_SCALE.textProperty().setValue(String.valueOf(ConfigurationStorage.getG_SCALE()));
        this.HELP_REQUEST_DELAY.textProperty().setValue(String.valueOf(ConfigurationStorage.getHELP_REQUEST_DELAY()));
    }
    
    @FXML
    void initialize() {
        assert btnClose != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'settings.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'settings.fxml'.";

        this.IMPACT_POW.textProperty().setValue(String.valueOf(ConfigurationStorage.getIMPACT_POW()));
        this.IMPACT_PASS.textProperty().setValue(String.valueOf(ConfigurationStorage.getIMPACT_PASS()));
        this.LAYING_POW.textProperty().setValue(String.valueOf(ConfigurationStorage.getLAYING_POW()));
        this.SKIP_MEASURE.textProperty().setValue(String.valueOf(ConfigurationStorage.getSKIP_MEASURE()));
        this.FALL_ANGLE.textProperty().setValue(String.valueOf(ConfigurationStorage.getFALL_ANGLE()));
        this.COUNT_SEC.textProperty().setValue(String.valueOf(ConfigurationStorage.getCOUNT_SEC()));
        this.FORCE_IMPACT_D.textProperty().setValue(String.valueOf(ConfigurationStorage.getFORCE_IMPACT_D()));
        this.FORCE_IMPACT_U.textProperty().setValue(String.valueOf(ConfigurationStorage.getFORCE_IMPACT_U()));
        this.G_SCALE.textProperty().setValue(String.valueOf(ConfigurationStorage.getG_SCALE()));
        this.HELP_REQUEST_DELAY.textProperty().setValue(String.valueOf(ConfigurationStorage.getHELP_REQUEST_DELAY()));
    }
	
}
