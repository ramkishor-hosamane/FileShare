package application;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modules.Packet;
import modules.Server;

public class MainController {

	@FXML
	private Button send_btn;

	@FXML
	private Button recieve_btn;

	@FXML
	private Button info;

	@FXML
	private Button settings;

	// Reciever Controller variables

	@FXML
	private Text file_label;

	@FXML
	public ProgressBar file_progress_bar;

	@FXML
	public Text total_label;

	@FXML
	private Button back_to_main_page;
	@FXML
	private ProgressBar progress_bar;
	@FXML
	private Text share_your_info;
	@FXML
	private Text my_ip;

	private String ip;

	public String getMyip() throws SocketException, UnknownHostException {
		final DatagramSocket socket = new DatagramSocket();
		  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		  String ip = socket.getLocalAddress().getHostAddress();
		
		return ip;
	}

	
	@FXML
	void onBackToMainPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
		stage.setTitle("FileShare");
		stage.setScene(scene);
		stage.show();
	}

	public Server server;
	public double progress = 0.0;
	public double range = 0.0;

	@FXML
	void RecievingFiles(ActionEvent event) throws IOException {
		System.out.println("Recieveing");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceivePage.fxml"));

		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);

		Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

		MainController recvr = loader.getController();
		String ip = getMyip();
		System.out.println("My ip is "+ip);

		recvr.my_ip.setText("Your IP is "+ ip);
		stage.setTitle("Receieve");
		System.out.println("After creating Receieve");
		
		stage.setScene(scene);
		stage.show();

		Thread taskThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					// Before recieving
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							recvr.progress_bar.setVisible(false);
							recvr.file_progress_bar.setVisible(false);
							recvr.file_label.setVisible(false);
							recvr.total_label.setVisible(false);

						}
					});

					server = new Server(new ServerSocket(5000));
					System.out.println("listening to port:5000");

					Packet recvPacket = (Packet) server.inStream.readObject();
					server.files = (recvPacket.files);
					System.out.println("Got files");

					// Before Starting receiving
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							recvr.progress_bar.setVisible(true);
							recvr.my_ip.setVisible(false);
							recvr.share_your_info.setText("Recieving ...");
							recvr.file_progress_bar.setVisible(true);
							recvr.file_label.setVisible(true);
							recvr.total_label.setVisible(true);
						}
					});

					// progress_bar.setVisible(true);

					//
					File folder = new File("Data");
					if(!folder.isDirectory()) {
						folder.mkdirs();
					}
					else {
						System.out.println("Directory is there");
					}
					progress = 0;
					range = (100 / server.files.size()) / 10.0;
					range /= 10.0;
					for (String file : server.files) {

						String[] file_parts = file.split("/");
						String file_name = file_parts[file_parts.length - 1];
						//System.out.println("Receving file " + file_name);

						// Update Recieving Text
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String[] file_parts = file.split("/");
								String file_name = file_parts[file_parts.length - 1];
								recvr.share_your_info.setText("Receiving " + file_name);
							}
						});

						server.receiveFile("Data/" + file_name, recvr);

						// Update Progressbar
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								progress += range;

								// sec.Name.setText("Progress is "+progress);
								recvr.progress_bar.setProgress(progress);
							}
						});
						// System.out.println("Finshed sending "+file);

					}

					// After finishing
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							recvr.share_your_info.setText("Transfer is completed");
							recvr.progress_bar.setProgress(1.0);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							recvr.progress_bar.setVisible(false);
							recvr.file_progress_bar.setVisible(false);
							recvr.file_label.setVisible(false);
							recvr.total_label.setVisible(false);
						}
					});

					server.destroy();

					System.out.println("Server Destroyed");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// taskThread.setDaemon(true);
		taskThread.start();

	}



	@FXML
	void SendingFiles(ActionEvent event) throws IOException {
		System.out.println("Sending");

		Parent root = FXMLLoader.load(getClass().getResource("SendPage.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
		stage.setTitle("Send");
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	void openInfo(ActionEvent event) {

	}

	@FXML
	void openSettings(ActionEvent event) {

	}

}
