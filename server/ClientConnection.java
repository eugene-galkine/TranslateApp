import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection 
{
	private String inputString;
	
	public ClientConnection(Socket inSocket) 
	{
		super(inSocket);
		
		try
		{
			//get the input text
			String in;
			inputString = "";
			while (!socket.isClosed() && (in = inFromServer.readLine()) != null && !in.equals("END"))
				inputString += in + "\n";
			
			System.out.println(inputString);
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}

	public void postResult(String result)
	{
		try 
		{
			//write the result and close the connection
			outToServer.write((result + "\nEND\n").getBytes());
			
			close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getInput()
	{
		return inputString;
	}
	
}
