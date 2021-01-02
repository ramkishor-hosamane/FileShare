package modules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
import application.MainController;

public class Server {
	public  DataOutputStream dataOutputStream = null;
	public  DataInputStream dataInputStream = null;
	public ObjectOutputStream outStream;
	public ObjectInputStream inStream;
	public Socket clientSocket;
	public ServerSocket serverSocket;
	public  ArrayList<String> files;
	private Object progress ;
	private long total_size;

	public Server(ServerSocket s) throws IOException {
		serverSocket = s;
        clientSocket = serverSocket.accept();
        System.out.println(clientSocket+" connected.");
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        files = new ArrayList<String>();
		outStream =  new ObjectOutputStream(clientSocket.getOutputStream());
		inStream  =  new ObjectInputStream(clientSocket.getInputStream());
        
    
	}
	public void destroy() throws IOException {
        dataInputStream.close();
        dataOutputStream.close();
        clientSocket.close();
	}
	

    public  void receiveFile(String fileName,MainController recvr) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        double size = dataInputStream.readLong();     // read file size

        total_size = (long) size;
        
        byte[] buffer = new byte[16*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        	progress = 1.0 - (size/total_size);
			
        	// Update Progressbar
			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					recvr.file_progress_bar.setProgress((double) progress);
				}
			});
            //System.out.println("Receving in in "+ fileName +" progress is "+ progress);

        }
        fileOutputStream.close();
    }
}