package Future;
import modules.NetworkScanner;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GetRecieverController  implements Initializable{

    @FXML
    private Button back;

    @FXML
    private VBox receiver_name_container;
    
    private Service<Void> backThread;
    
    
    @FXML
    void onBack(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

    	Scene scene = new Scene(root);

    	
    	Stage stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
    	stage.setTitle("FileShare");
		stage.setScene(scene);
		stage.show();
    	
    }

    public void AddRecieverButton(String name)
    {
    	System.out.println("While adding button "+name);
    	Button button = new Button(name);
		button.setId("dark-blue");
		this.receiver_name_container.getChildren().add(button);
		
    }
	public int ip_size=1;
	public ArrayList<String> recievers_ip_list;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){

		
		
		
		

	
		
		
		
	}



}


