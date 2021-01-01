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

public class Server {
	public  DataOutputStream dataOutputStream = null;
	public  DataInputStream dataInputStream = null;
	public ObjectOutputStream outStream;
	public ObjectInputStream inStream;
	public Socket clientSocket;
	public ServerSocket serverSocket;
	public  ArrayList<String> files;

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
	
	/*
    public static void main(String[] args) {
        try{
        	
        	Server server = new Server(new ServerSocket(5000));
            System.out.println("listening to port:5000");
 
            Packet recvPacket = (Packet)server.inStream.readObject();

            server.files = (recvPacket.files);
	        System.out.println("Got files");
            for(String file : server.files) {
            	
    		 	String[] file_parts = file.toString().split("/");
    		 	String file_name = file_parts[file_parts.length-1];
    	        System.out.println("Receving file "+file_name);

                server.receiveFile("/home/ram/Output/"+file_name);
            	
            }
            
            server.destroy();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
*/
    public  void receiveFile(String fileName) throws Exception{
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        
        long size = dataInputStream.readLong();     // read file size
        byte[] buffer = new byte[16*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        	//System.out.println("Receving in in "+ fileName +" size is "+size);

        }
        fileOutputStream.close();
    }
}