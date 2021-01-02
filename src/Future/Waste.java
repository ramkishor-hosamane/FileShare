package Future;
import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
public class Waste {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		File file = new File("Data");
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		else {
			System.out.println("Directory is there");
		}
	}


}
