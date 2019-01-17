package com.application;

import java.net.URL;

import com.application.util.FallNotificationService;
import com.application.ui.*;

import javafx.application.*;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainAppliction extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		URL viewUrl = getClass().getResource("/com/application/ui/main.fxml");
		
		//BorderPane root= (BorderPane)FXMLLoader.load(viewUrl);
		AnchorPane root = (AnchorPane)FXMLLoader.load(viewUrl);
		 
		//FXMLDocumentController controller = (FXMLDocumentController) loader.getController();
		

		Scene scene = new Scene(root);
		
		
		primaryStage.setScene(scene);	
		primaryStage.setTitle("Main");
		primaryStage.show();
	}
	

	
	public void showSettings() throws Exception {
		AnchorPane sett = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/application/ui/settings.fxml"));
		
		Stage settingsStage = new Stage();
		settingsStage.setTitle("Settings");
		//settingsStage.initModality(Modality.WINDOW_MODAL);
		//settingsStage.initOwner(primaryStage);
		
		Scene scene = new Scene(sett);
		
		settingsStage.setScene(scene);
		settingsStage.showAndWait();
		
	}
	
	public void UserInfo() throws Exception {
		AnchorPane userinfo = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/application/ui/userinfo.fxml"));
		
		Stage settingsStage = new Stage();
		settingsStage.setTitle("User General Information");
		//settingsStage.initModality(Modality.WINDOW_MODAL);
		//settingsStage.initOwner(primaryStage);
		
		Scene scene = new Scene(userinfo);
		
		settingsStage.setScene(scene);
		settingsStage.showAndWait();
		
	}
	@Override
	public void stop(){
	   System.exit(0);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
