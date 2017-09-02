import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection
{
	protected Socket socket;
	protected DataOutputStream outToServer;
	protected BufferedReader inFromServer;
	
	public Connection(Socket inSocket)
	{
		socket = inSocket;
		
		try 
		{
			//get the output and input stream
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void close()
	{
		//close the connection
		try 
		{
			inFromServer.close();
			outToServer.close();
			socket.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
