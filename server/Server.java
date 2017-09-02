import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
	public static void main(String[] args)
	{
		new Server();
	}
	
	private static final int CLIENT_PORT = 1627;
	private static final int TRANSLATOR_PORT = 1628;
	
	private ServerSocket clientServer;
	
	public Server() 
	{
		try 
		{
			clientServer = new ServerSocket(CLIENT_PORT);
			System.out.println("Server started on port: " + clientServer.getLocalPort());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		this.start();
	};
	
	@Override
	public void run() 
	{
		
		
		while(true)
		{
			try
			{
				Socket connectionSocket = clientServer.accept();
				new Thread(new ClientConnection(connectionSocket)).start();
		    } catch (Exception e) {e.printStackTrace();}
		}
	}
}

