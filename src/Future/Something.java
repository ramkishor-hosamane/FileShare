package Future;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

class Something
{
   public static void main(String args[]) throws Exception
   {
	   InetAddress myIP = null;
	   try
	   {
	   myIP = InetAddress.getLocalHost();
	   } catch (UnknownHostException e)
	   {
	   e.printStackTrace();
	   }
	   String IPAddress = myIP.getHostAddress();
	   String hostname = myIP.getHostName();

	   //System.out.printf("IP address of LocalHost is %s %n", IPAddress);
	   //System.out.printf("Host name of my machine is %s %n", hostname);

	   // System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress()); // often returns "127.0.0.1"
	   Enumeration n = null;
	   try {
	   n = NetworkInterface.getNetworkInterfaces();
	   } catch (SocketException e1) {
	   // TODO Auto-generated catch block
	   e1.printStackTrace();
	   }
	   for (; n.hasMoreElements();)
	   {
	   NetworkInterface e = (NetworkInterface) n.nextElement();
	   //System.out.println("Interface: " + e.getName());
	   Enumeration a = e.getInetAddresses();
	   for (; a.hasMoreElements();)
	   {
	   InetAddress addr = (InetAddress) a.nextElement();
	   System.out.println(" " + addr.getHostAddress());
	   }
	   }
  }
}