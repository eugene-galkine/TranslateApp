import java.io.IOException;
import java.net.Socket;

public class TranslatorConnection extends Connection implements Runnable
{
	private ClientConnection client;
	
	public TranslatorConnection(Socket inSocket, ClientConnection client)
	{
		super(inSocket);
		this.client = client;
		
		System.out.println("new translator connection");
		
		try
		{
			//write the initial translation request
			outToServer.write((client.getInput() + "\nEND\n").getBytes());
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
			//wait for translation and then post it to the client
			String in;
			String inputString = "";
			while (!socket.isClosed() && (in = inFromServer.readLine()) != null && !in.equals("END"))
				inputString += in + "\n";
			
			client.postResult(inputString);
			
			//close because we are now done
			close();
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}

}
