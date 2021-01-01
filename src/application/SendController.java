package application;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modules.Client;
import modules.FileRecord;
import modules.Packet;

public class SendController implements Initializable{

    @FXML
    private Button back;
    
    @FXML
    private TextField reciever_ip_field;
    
    @FXML
    private Button add_files;

    @FXML
    private Button send_files;

    @FXML
    private TableView<FileRecord> file_table;

    @FXML
    private TableColumn<FileRecord, Integer> sl_no;

    @FXML
    private TableColumn<FileRecord, String> file_name;

    @FXML
    private TableColumn<FileRecord, String> file_path;

    @FXML
    private TableColumn<FileRecord, String> is_select;

    @FXML
    private Button delete_files;

    @FXML
    private CheckBox select_all;

    public ObservableList<FileRecord> data =FXCollections.observableArrayList(
			new FileRecord(1,"Pasanga HD.mp4","/home/ram/Pasanga HD.mp4"),
			new FileRecord(2,"ib.jar","/home/ram/lib.jar"),
			new FileRecord(3,"PuriyathaTamil.mp4","/home/ram/Videos/PuriyathaTamil.mp4")

			) ;;
			

			
	//Send Tranfser Variables
			
		    @FXML
		    private VBox receiver_name_container;

		    public String reciever_ip;
		       
		    public String getReciever_ip() {
				return reciever_ip;
			}

			public void setReciever_ip(String reciever_ip) {
				System.out.println("Setiing to "+reciever_ip);
				this.reciever_ip = reciever_ip;
			}
			
			

		    @FXML
		    private ProgressBar progress_bar;
	
			public Client client;			
	
				public double progress = 0;
				public double range = 0.0;
			
			 @FXML
			    private Button back_to_send_page;



			    @FXML
			    private Label Name;

			    @FXML
			    void onBackToSendPage(ActionEvent event) {

			    }
	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	@FXML
    void findReciever(ActionEvent event) throws IOException {
    	//System.out.println("Before creating");
    	//System.out.println(reciever_ip_field.getText()+" is the yes");	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SenderTransferPage.fxml"));
    	
    	Parent root = (Parent) loader.load();
    	Scene scene = new Scene(root);

    	Stage stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
    
    	SendController sec =  loader.getController();
    	sec.setReciever_ip(reciever_ip_field.getText());
    	sec.Name.setText(sec.getReciever_ip());
    	stage.setTitle("Transfer");
    	System.out.println("After creating");

		stage.setScene(scene);
		stage.show();
		
		
	
		
		Thread taskThread = new Thread(new Runnable() {
		      @Override
		      public void run() {

		         
		    	  try
			    	{
		    	        			
		    		  				progress = 0;
			      			    	client=  new Client(new Socket("localhost",5000));
			      			    	for(FileRecord file_rec :data)
			      			    	{
			      			    		client.files.add(file_rec.file_path);
			      			    	}
			      			        
			      			        range = (100/client.files.size())/10.0;
			      			        range/=10.0;
			      			        //System.out.println("range is"+range+"  "+client.files.size());
			      			        Packet packet=  new Packet(client.files);
			      			        client.outStream.writeObject(packet);
			      			        System.out.println("Packet files "+packet.files);
			      			        for(String file : client.files) {
			      			        
			      			        	//Update Sending Text
				    			        Platform.runLater(new Runnable() {
		      					            @Override
		      					            public void run() {
					      		    		 	String[] file_parts = file.split("/");
					      		    		 	String file_name = file_parts[file_parts.length-1];
		      					            	sec.Name.setText("Sending "+file_name);
		      					            }
		      					          });		
			      			        	
			      			        	
			      			        	
			      			        	
			      			        	
			      			        	//System.out.println("Sending "+file);
			      			            client.sendFile(file);
			      			            
			      			            //Update Progressbar
			      				        Platform.runLater(new Runnable() {
			      					            @Override
			      					            public void run() {
			      					            	progress += range;

			      					            	//sec.Name.setText("Progress is "+progress);
			      					            	sec.progress_bar.setProgress(progress);
			      					            }
			      					          });			            
      					            	//System.out.println("Finshed sending "+file);


			      			        }


			      		            


			      			        //After finishing
			    			        Platform.runLater(new Runnable() {
	      					            @Override
	      					            public void run() {

	      					            	sec.Name.setText("Transfer is completed");
	      					            	sec.progress_bar.setProgress(1.0);
	      					            }
	      					          });		
			      			        
			      			        
			      			        client.destroy();
			      			        System.out.println("Iam Destroyed");
			      			    }catch (Exception e){
			      			        e.printStackTrace();
			      			    }  
				            	
				            	

				            }
				          });	
		    		  
		    		  


		
		
			//taskThread.setDaemon(true);
		    taskThread.start();
		    
		
		
		
		
		
		
		
		    	

    	
    }

    @FXML
    void onAddFiles(ActionEvent event) {

    	FileChooser fc = new FileChooser();
    	fc.setInitialDirectory(new File("/home/ram"));
    	List<File> selectedFiles = fc.showOpenMultipleDialog(null);
    	if(selectedFiles!=null)
    	{
    		for(File file:selectedFiles) {
    			data.add(
    					new FileRecord(data.size()+1, file.getName(), file.getAbsolutePath())
    					);

    		}
    		System.out.println("Files added");
        	
    	}
    	else
    	{
    		System.out.println("File not selected");
    	}
    	
    	select_all.setSelected(false);
	
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Scene scene = new Scene(root);
    	Stage stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
    	stage.setTitle("FileShare");
		stage.setScene(scene);
		stage.show();
    	
    }

    @FXML
    void onDeleteFiles(ActionEvent event) {

    	ObservableList<FileRecord> dataListRemove = FXCollections.observableArrayList();
    	
    	for(FileRecord file:data) {
    		if(file.getIs_select().isSelected())
    			dataListRemove.add(file);
    	}
    	
    	data.removeAll(dataListRemove);
    	
    	int i =1;
    	for(FileRecord file:data) {
    		file.setSl_no(i);
    		i++;
    	}
    	
    	select_all.setSelected(false);
    }

    @FXML
    void onSelectAll(ActionEvent event) {

    	for(FileRecord file:data) {
    		if(file.getIs_select().isSelected())
    		{
        		file.is_select.setSelected(false);
    			
    		}
    		else
    		{
        		file.is_select.setSelected(true);
    			
    		}

    	}    	
    	
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
		
			reciever_ip_field.setText("192.168.1.1");
			System.out.println("Inside initi");
			sl_no.setCellValueFactory(
					new PropertyValueFactory<FileRecord,Integer>("sl_no")				
					);
			file_name.setCellValueFactory(
					new PropertyValueFactory<FileRecord,String>("file_name")				
					);
			file_path.setCellValueFactory(
					new PropertyValueFactory<FileRecord,String>("file_path")				
					);
			is_select.setCellValueFactory(
					new PropertyValueFactory<FileRecord,String>("is_select")				
					);

			

			
			file_table.setItems(data);

			
			
		}catch (Exception e) {

		
		
		
		}
				
	}

}
