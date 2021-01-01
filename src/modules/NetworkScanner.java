package modules;
import java.net.*; 
import java.io.*; 
import java.util.*; 
  
public class NetworkScanner {
	
	
	/*
	public static void main(String[] args) throws UnknownHostException, IOException {
		NetworkScanner s = new NetworkScanner();
		s.getHosts("192.168.1");
	}
	*/
	
	public void getAdresses() {
	    try {
	        InetAddress address = InetAddress.getLocalHost();
	        System.out.println(address);
	      } catch (UnknownHostException ex) {
	        System.out.println("Could not find this computer's address.");
	      }
	
	}
	public ArrayList<String> getHosts(String subnet) throws UnknownHostException, IOException{
		   ArrayList<String> hosts = new ArrayList<String>();
			int timeout=200;
		   for (int i=1;i<255;i++){
		       String host=subnet + "." + i;
			   //System.out.println("Checking  "+host);

		       if (InetAddress.getByName(host).isReachable(timeout)){
		           System.out.println(host + "is reachable");
		           hosts.add(host);
		       }
		   }
		   return hosts;
		}
	
	
}
