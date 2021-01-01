package modules;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
	public  DataOutputStream dataOutputStream = null;
    public  DataInputStream dataInputStream = null;
	public ObjectOutputStream outStream;
	public ObjectInputStream inStream;
	public  ArrayList<String> files;
	public Socket socket;
    
	  public Client(Socket socket) throws IOException {
			this.socket = socket;
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    		outStream=  new ObjectOutputStream(this.socket.getOutputStream());
    		inStream= new ObjectInputStream(this.socket.getInputStream());
            files = new ArrayList<String>();

	  }
	  
	  public void destroy() throws IOException
	  {
		  dataInputStream.close();
          dataInputStream.close();
	  }
	  /*
	public static void main(String[] args) {

    	
    	
		try
        	{
	    	Client client=  new Client(new Socket("localhost",5000));
        	
            client.files.add("/home/ram/Videos/www.TamilRockers.to - Maheshinte Prathikaaram [2016] Malayalam DVDRip x264 700MB ESubs.mkv");
            client.files.add("/home/ram/Videos/C U Soon (2020) Malayalam HDRip 700MB.mkv");
            
            Packet packet=  new Packet(client.files);
            client.outStream.writeObject(packet);
            System.out.println("Packet files "+packet.files);
            for(String file : client.files) {
            	System.out.println("Sending "+file);
                client.sendFile(file);
            	
            }
            
            client.destroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/
  
	public  void sendFile(String current_file) throws Exception{
        int bytes = 0;
        File file = new File(current_file);
        FileInputStream fileInputStream = new FileInputStream(file);
        
        // send file size
        dataOutputStream.writeLong(file.length());  
        // break file into chunks
        byte[] buffer = new byte[16*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
        	//System.out.println("Sending in "+ current_file +" size is ");
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
}