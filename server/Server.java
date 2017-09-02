import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;

public class Server
{
	public static void main(String[] args)
	{
		new Server();
	}
	
	private static final int CLIENT_PORT = 1627;
	private static final int TRANSLATOR_PORT = 1628;
	
	private ServerSocket clientServer;
	private ServerSocket translatorServer;
	private ArrayDeque<ClientConnection> workQueue;
	
	public Server() 
	{
		workQueue = new ArrayDeque<ClientConnection>();
		try 
		{
			//start the tcp servers
			clientServer = new ServerSocket(CLIENT_PORT);
			translatorServer = new ServerSocket(TRANSLATOR_PORT);
			System.out.println("Client server started on port: " + clientServer.getLocalPort());
			System.out.println("Translator server started on port: " + translatorServer.getLocalPort());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		new ClientListener().start();
		new TranslatorListener().start();
	};
	
	private class ClientListener extends Thread
	{
		@Override
		public void run() 
		{
			//wait for new client connections
			while(true)
			{
				try
				{
					//new Client Connection
					Socket connectionSocket = clientServer.accept();
					ClientConnection connection = new ClientConnection(connectionSocket);
					workQueue.push(connection);
			    } catch (Exception e) {e.printStackTrace();}
			}
		}
	}
	
	private class TranslatorListener extends Thread
	{
		@Override
		public void run() 
		{
			//wait for new translator connections
			while(true)
			{
				try
				{
					//new Translator Connection
					Socket connectionSocket = translatorServer.accept();
					
					//only accept the connection if there is work, otherwise we just close it
					if (!workQueue.isEmpty())
						new Thread(new TranslatorConnection(connectionSocket, workQueue.pop())).start();
					else
						connectionSocket.close();
			    } catch (Exception e) {e.printStackTrace();}
			}
		}
	}
}

