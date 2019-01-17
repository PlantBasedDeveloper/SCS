package com.application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;


//import com.application.bluetooth.DbSave;
//import com.application.bluetooth.ProcessMessage;
//import com.application.bluetooth.Sensor;
//import com.application.bluetooth.Server;
//import com.application.bluetooth.Utils;
import com.application.util.FallNotificationService;
import com.mock.connection.MockServer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class mainController {

	//region
/**
 * @author Elis
 * 
 * Some counters for the series update and graph update
 * 
 * */
	int Accel1SerieCnt=0;
	//endregion
	MockServer sr = MockServer.getInstance();
	
	public static StringProperty Scan = new SimpleStringProperty(null);
	public static StringProperty status = new SimpleStringProperty(MockServer.status);
	
	private XYChart.Series<Number, Number> Accel1Serie = new XYChart.Series<>(); 
	private XYChart.Series<Number, Number> Accel2Serie = new XYChart.Series<>(); 
	NumberAxis xAxis = new NumberAxis(0, 50, 1);
	NumberAxis yAxis = new NumberAxis(0,5,0.1);
	
	//TODO: fix range of each gyroaxis
	int gyroSerieCount=0;
	NumberAxis xGxis = new NumberAxis(0, 50, 1);
	NumberAxis yGxis = new NumberAxis(0,180,10);
	private XYChart.Series<Number, Number> Gyro1Serie = new XYChart.Series<>(); 
	private XYChart.Series<Number, Number> Gyro2Serie = new XYChart.Series<>(); 
	//private XYChart.Series<Number, Number> Accel2Serie = new XYChart.Series<>(); 
	//private XYChart.Series<Number, Number> Accel2Serie = new XYChart.Series<>(); 
	
	
    /**
     * @author Elis
     * 
     *   All IDs ofr mainView elements
     * 
     * TODO: From now one all new elements that you create for mainView 
     *       please add the IDs in this section
     *       
     *       Rule 1: always add a new ID at the end
     * */
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    static NumberAxis accel_accel;
    
    @FXML
    static CategoryAxis accel_time;

    @FXML
    static CategoryAxis gyro_time;
    
    @FXML
    static NumberAxis gyro_accel;
    
    @FXML
    private Button btnClean;   
    
    @FXML
    private MenuItem btnClose;
    
    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;
    
    @FXML
    private MenuItem btnSettings;
    
    @FXML
    private Button btnStart;
    
    @FXML
    private Label btnStatus;
    
    @FXML
    private Button btnStop;

    @FXML
    private MenuItem btnUserInfo;
    
    @FXML
    private Label lblConnecting;
    
    @FXML
    private Label lblFallDet;
    
    @FXML
    private Label lblHelpReq;
    
    @FXML
    private Button btnScan;
    
    @FXML
    private ChoiceBox<String> ddlAvSensors;
    
    @FXML
    private AnchorPane idGraphAccl;
    

    @FXML
    private AnchorPane idGraphGyro;
    
    @FXML
    private TextField txtPortName;
	
    @FXML
    private Button btnOpenPort;
    /* End of IDs of mainView */
    
    /**
     * @author Elis
     * 
     * Actions of main View Buttons f
     * 
     * TODO: From now one all actions that you create for this controller
     *       please add them in this section
     *       
     *       Apply Rule 1
     * */
    MainAppliction main = new MainAppliction();
       
    public void setLblHelpReqColor(String color) {
    	 lblHelpReq.setTextFill(Color.web(color));
    }
   
    public void setLblFallDetColor(String color) {
   	 	lblFallDet.setTextFill(Color.web(color));
    }
    
    @FXML
//    TODO: delete
    void Connect(ActionEvent event) {
    	
//    	String dev = this.ddlAvSensors.getValue();   	
//    	sr.connectTo(Utils.reverseHexString(dev), this);
    	
    }
    
    @FXML
    //TODO: delete
    void Disconnect(ActionEvent event) {
//    	sr.WriteToPort("01030C00");
//    	//sr.AutoDiscover();
//    	//new Application();
    
    }
  
    
    @FXML
    void FalseAlrm(ActionEvent event) {
    	FallNotificationService.notifyFalseAlarm();
    	sr.activateButtons();	//TODO: implement
    }
    
    @FXML
    //TODO: delete
    void activateIOService(ActionEvent event) {
    	sr.activateButtons();
    	//sr.WriteToPort("01030C00");
     //   new DbSave();
    }
    
    @FXML
    void doFall(ActionEvent event) {
    	FallNotificationService.notifyFall();
    }    
    
    @FXML
    void CloseApp(ActionEvent event) {
    	
    }   
    
    @FXML
    void Settings(ActionEvent event) throws Exception {
    	
    	//MainAppliction main = new MainAppliction();
    	main.showSettings();
    }

    @FXML
    void StartReceiving(ActionEvent event) {  	
    	sr.readData();
    	
    	// new Application();
    }

    @FXML
    void StopReceiving(ActionEvent event) {
    	sr.activateButtons();
    	
    }
       
    @FXML
    void Userinfo(ActionEvent event) throws Exception {
    	//MainAppliction file = new MainAppliction();
    	main.UserInfo();
    }
    
    @FXML
    //TODO: Change to load
    void ScanForBluetoothDevices(ActionEvent event) {
//    	Server.STATUS="Scanning...";
//          	Scan.setValue(null);
//          	sr.Scan(this);
    
    }
    
    @FXML
    //TODO: remove
    void openComPort(ActionEvent event) {

//    	String portName= this.txtPortName.getText();
//    	if(sr.openCOMPort(portName))
//    	{
//    		sr.DevInit(this);
//    	}
//    	else
//    	{
//    	 sr.STATUS=portName+"not correct";
//    	}
    	
    	
    }
    
    /**End of Actions of main View Buttons */
    
    @FXML
    void initialize() {
    	
    	 initializeGraph(idGraphAccl,Accel1Serie,Accel2Serie);
    	 prepareTimeline();
    	 
    	 initializeGyroGraph(idGraphGyro, Gyro1Serie, Gyro2Serie);
    	// set a reference to this controller so that the FallNotificationService can change the colour of labels
    	FallNotificationService.setMain(this);
		
				
		this.ddlAvSensors.getItems().add("Select a Sensor");
		this.ddlAvSensors.getSelectionModel().selectFirst();
//		this.ddlAvSensors.getSelectionModel().select(1);
		this.lblConnecting.textProperty().bind(status);
        this.btnConnect.disableProperty().bind(BooleanExpression.booleanExpression(Scan.isEmpty()));
        this.btnDisconnect.disableProperty().bind(BooleanExpression.booleanExpression(Scan.isEmpty()));
		//this.btnConnect.disableProperty().bind(observable);
        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'main.fxml'.";       
        assert btnClean != null : "fx:id=\"btnClean\" was not injected: check your FXML file 'main.fxml'.";
        assert btnDisconnect != null : "fx:id=\"btnDisconnect\" was not injected: check your FXML file 'main.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'main.fxml'.";
        assert btnStatus != null : "fx:id=\"btnStatus\" was not injected: check your FXML file 'main.fxml'.";
        assert btnStop != null : "fx:id=\"btnStop\" was not injected: check your FXML file 'main.fxml'.";
        assert lblConnecting != null : "fx:id=\"lblConnecting\" was not injected: check your FXML file 'main.fxml'.";
        assert lblFallDet != null : "fx:id=\"lblFallDet\" was not injected: check your FXML file 'main.fxml'.";
        assert lblHelpReq != null : "fx:id=\"lblHelpReq\" was not injected: check your FXML file 'main.fxml'.";
        assert btnOpenPort != null : "fx:id=\"btnOpenPort\" was not injected: check your FXML file 'main.fxml'.";
        assert txtPortName != null : "fx:id=\"txtPortName\" was not injected: check your FXML file 'main.fxml'.";

        
    }

	/**
	 * @author Elis
	 * 
	 *         TODO: Please Use this section t add other functions that you will
	 *         need for help
	 * 
	 *         Apply Rule1
	 * 
	 */
    
    public void addGyroDataToSerie()
    {
    	for(int i=0; i<3; i++)
    	{
    		if(!MockServer.gyro1DataToDisplay.isEmpty() || !MockServer.gyro2DataToDisplay.isEmpty())
    		{
    			int tmp=gyroSerieCount++;
    			if(!MockServer.gyro1DataToDisplay.isEmpty())
    			{
    				Gyro1Serie.getData().add(new XYChart.Data<>(tmp, MockServer.gyro1DataToDisplay.remove()));
    			}
    			if(!MockServer.gyro2DataToDisplay.isEmpty())
    			{
    				Gyro2Serie.getData().add(new XYChart.Data<>(tmp, MockServer.gyro2DataToDisplay.remove()));
    			}
    		}
    		else
    		{
    			break;
    		}
    	}
    	
    	//Update timeline
    	if (Gyro1Serie.getData().size() > 50) {
    		Gyro1Serie.getData().remove(0, Gyro1Serie.getData().size() - 50);
		}
		if (Gyro2Serie.getData().size() > 50) {
			Gyro2Serie.getData().remove(0, Gyro2Serie.getData().size() - 50);
		}
		if (gyroSerieCount > 50) {
			xGxis.setLowerBound(Accel1SerieCnt - 50);
		} else {
			xGxis.setLowerBound(0);
		}
		if (gyroSerieCount != 0) {
			xGxis.setUpperBound(Accel1SerieCnt - 1);
		} else {
			xGxis.setUpperBound(0);
		}
    }

	public void addAccqDataToSerie() {
		for (int i = 0; i < 3; i++) { 
			if (!MockServer.absAcc1Queue.isEmpty() || !MockServer.absAcc2Queue.isEmpty()) {
				int tmp = Accel1SerieCnt++;
				if (!MockServer.absAcc1Queue.isEmpty()) {
					Accel1Serie.getData().add(new XYChart.Data<>(tmp, MockServer.absAcc1Queue.remove()));
				}  				
				if (!MockServer.absAcc2Queue.isEmpty()) {
					Accel2Serie.getData().add(new XYChart.Data<>(tmp, MockServer.absAcc2Queue.remove()));
				} 
				
			} else {
				break;
			}

		}

		if (Accel1Serie.getData().size() > 50) {
			Accel1Serie.getData().remove(0, Accel1Serie.getData().size() - 50);
		}
		if (Accel2Serie.getData().size() > 50) {
			Accel2Serie.getData().remove(0, Accel2Serie.getData().size() - 50);
		}
		if (Accel1SerieCnt > 50) {
			xAxis.setLowerBound(Accel1SerieCnt - 50);
		} else {
			xAxis.setLowerBound(0);
		}
		if (Accel1SerieCnt != 0) {
			xAxis.setUpperBound(Accel1SerieCnt - 1);
		} else {
			xAxis.setUpperBound(0);
		}
	}
    
       
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
            	addAccqDataToSerie();
            	addGyroDataToSerie();
            }
        }.start();
    }
    
    private void initializeGraph(AnchorPane graphAnchorPane, XYChart.Series<Number, Number> Accel1Series,  XYChart.Series<Number, Number> Accel2Series) {
    	
    	xAxis.setLabel("Time (ms) ");
        yAxis.setLabel("Acceleration (g)");
         
         final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
             // Override to remove symbols on each data point
             @Override
             protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
             }
         };
        
         lineChart.setAnimated(true);
         lineChart.setTitle("Accelerometer");
         lineChart.setHorizontalGridLinesVisible(true);
         lineChart.setVerticalGridLinesVisible(false);
    	
         Accel1Series.setName("Aclr 1");
         lineChart.getData().addAll(Accel1Series);
         Accel2Series.setName("Aclr 2");
         lineChart.getData().addAll(Accel2Serie);
         
         graphAnchorPane.getChildren().add(lineChart);
    }
    
    private void initializeGyroGraph(AnchorPane graphAnchorPane,XYChart.Series<Number, Number> gyro1Serie,XYChart.Series<Number, Number> gyro2Serie)
    { 
    	xGxis.setLabel("Time (ms) ");
    	yGxis.setLabel("Degrees");
    	
    	final LineChart<Number, Number> lineChartGyro = new LineChart<Number, Number>(xGxis, yGxis) {
            // Override to remove symbols on each data point
            @Override
            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
            }
        };
        lineChartGyro.setAnimated(true);
        lineChartGyro.setTitle("Gyroscope");
        lineChartGyro.setHorizontalGridLinesVisible(true);
        lineChartGyro.setVerticalGridLinesVisible(false);
        
        gyro1Serie.setName("Gyro 1");
        lineChartGyro.getData().addAll(gyro1Serie);
        
        gyro2Serie.setName("Gyro 2");
        lineChartGyro.getData().addAll(gyro2Serie);
        
        graphAnchorPane.getChildren().add(lineChartGyro);
        
    }
    public Button getbtnConnect()
    {
    	return this.btnConnect;
    }
    public Button getbtnDisconnect()
    {
    	return this.btnDisconnect;
    }

//    public void showSensorsFound()
//    {
//    	this.ddlAvSensors.getItems().addAll(sr.devicesFound);
//    }
    
//    public static void StatusChanger() {
//
//    	Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//            	
//            	while(MockServer.LUNCHPAD_READY)
//            	{
//            		Thread.sleep(10);
//              	  
//             	   Platform.runLater(new Runnable() {
//
//                        @Override
//                        public void run() {
//                        	status.setValue(String.valueOf(MockServer.STATUS));
//                        	
//                        	if(MockServer.SCAN_COMPLETE)
//                        	{
//                        		Scan.setValue("1");
//                        	}
//                        	
//                        }
//                    });
//             	   
//            	}
//            	return null;
//              
//            }
//         };
//         Thread th = new Thread(task);
//         th.setDaemon(true);
//         th.start();
//      
//    }
}
