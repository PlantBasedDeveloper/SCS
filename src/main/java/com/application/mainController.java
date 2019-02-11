package com.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.math3.ml.neuralnet.twod.util.TopographicErrorHistogram;

import com.application.math.Mathems;
import com.application.util.FallNotificationService;
import com.mock.connection.MockServer;
import com.mock.connection.SensorDataReader;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class mainController {

	//region
/**
 * @author Xhoni
 * 
 * Some counters for the series update and graph update
 * 
 * */
	int Accel1SerieCnt=0;
	//endregion
	MockServer sr = MockServer.getInstance();
	
	public static StringProperty Scan = new SimpleStringProperty(null);
	
	private XYChart.Series<Number, Number> Accel1Serie = new XYChart.Series<>(); 
	private XYChart.Series<Number, Number> Accel2Serie = new XYChart.Series<>(); 
	NumberAxis xAxis = new NumberAxis(0, 50, 1);
	NumberAxis yAxis = new NumberAxis(0,3,0.1);
	
	//TODO: fix range of each gyroaxis
	int gyroSerieCount=0;
	NumberAxis xGxis = new NumberAxis(0, 50, 1);
	NumberAxis yGxis = new NumberAxis(0,90,10);
	private XYChart.Series<Number, Number> Gyro1Serie = new XYChart.Series<>(); 
	private XYChart.Series<Number, Number> Gyro2Serie = new XYChart.Series<>(); 
	public static File fileToRead = new File(System.getProperty("user.dir") + "\\data\\SA01\\D01_SA01_R01.txt");
	public static SensorDataReader sensorDataReader = new SensorDataReader();
	
    /**
     * @author Xhoni
     * 
     *   All IDs for mainView elements
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
    private MenuItem btnClose;
        
    @FXML
    private MenuItem btnSettings;
    
    @FXML
	public Button btnStart;
    
    @FXML
	public Button btnBrowse;
    
    @FXML
    private Label btnStatus;
    
    @FXML
	public Button btnStop;
    
    @FXML
    private Button btnFall;
    
    @FXML
    private Button btnFlsAlarm;

    @FXML
    private MenuItem btnUserInfo;
    
    @FXML
    private Label lblConnecting;
    
    @FXML
    private Label lblFallDet;
    
    @FXML
    private Label lblHelpReq;
    
    @FXML
    private AnchorPane idGraphAccl;

    @FXML
    private AnchorPane idGraphGyro;
    
    @FXML
    private Button btnClean;   
    
    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;
    
    @FXML
    private Button btnScan;
    
    @FXML
    private ChoiceBox<String> ddlAvSensors;
    /* End of IDs of mainView */
    
    /**
     * @author Xhoni
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
    void FalseAlrm(ActionEvent event) throws InterruptedException {
    	FallNotificationService.notifyFalseAlarm();
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
    	main.showSettings();
    }
//
    @FXML
    void StartReceiving(ActionEvent event) {  	
    	sensorDataReader = new SensorDataReader();
    	Mathems.main();
    	sr.readData();
    	btnStart.setDisable(true);
    	btnBrowse.setDisable(true);
    	btnStop.setDisable(false);
    }
//
    @FXML
    void StopReceiving(ActionEvent event) throws InterruptedException, IOException {
    	sr.stopReading();
    	enableButtons();
    }
//    
    @FXML
    void Browse(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File("data"));
    	fileChooser.getExtensionFilters().addAll(
    		     new FileChooser.ExtensionFilter("Text Files", "*.txt")
    		);
    	fileToRead = fileChooser.showOpenDialog(new Stage());
    	lblConnecting.setText("Loaded File - " + fileToRead.getName().toString());
    }
//       
    @FXML
    void Userinfo(ActionEvent event) throws Exception {
    	//MainAppliction file = new MainAppliction();
    	main.UserInfo();
    }    
//    /**End of Actions of main View Buttons */
    
    @FXML
    void initialize() {
    	
    	 initializeGraph(idGraphAccl,Accel1Serie,Accel2Serie);
    	 prepareTimeline();
    	 
    	 initializeGyroGraph(idGraphGyro, Gyro1Serie, Gyro2Serie);
    	// set a reference to this controller so that the FallNotificationService can change the colour of labels
    	FallNotificationService.setMain(this);
		
				
		assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'main.fxml'.";
        assert btnStatus != null : "fx:id=\"btnStatus\" was not injected: check your FXML file 'main.fxml'.";
        assert btnStop != null : "fx:id=\"btnStop\" was not injected: check your FXML file 'main.fxml'.";
        assert btnBrowse != null : "fx:id=\"btnBrowse\" was not injected: check your FXML file 'main.fxml'.";
        assert lblConnecting != null : "fx:id=\"lblConnecting\" was not injected: check your FXML file 'main.fxml'.";
        assert lblFallDet != null : "fx:id=\"lblFallDet\" was not injected: check your FXML file 'main.fxml'.";
        assert lblHelpReq != null : "fx:id=\"lblHelpReq\" was not injected: check your FXML file 'main.fxml'.";
        
    }

	/**
	 * @author Xhoni
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
         lineChart.getData().addAll(Accel2Series);
         
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
    
    public void enableButtons() {
    	btnStart.setDisable(false);
    	btnBrowse.setDisable(false);
    	btnStop.setDisable(true);
    }
}
