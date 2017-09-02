import java.net.Socket;

public class Connection extends Thread
{
	protected Socket socket;
	
	public Connection(Socket inSocket)
	{
		socket = inSocket;
	}
}
