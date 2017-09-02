import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnection extends Connection 
{
	private DataOutputStream outToServer;
	private BufferedReader inFromServer ;
	public ClientConnection(Socket inSocket) 
	{
		super(inSocket);
		
		try
		{
			//get the output stream
			outToServer = new DataOutputStream(socket.getOutputStream());
			
			//write result
			outToServer.write("test result\nline2\nEND".getBytes());
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}

	@Override
	public void run() 
	{
		try
		{
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String in;
			while (!socket.isClosed() && (in = inFromServer.readLine()) != null && !in.equals("END"))
				System.out.println(in);
			
			inFromServer.close();
			outToServer.close();
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}
}
