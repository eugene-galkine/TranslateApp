package com.translate.eg.translate;

import android.support.v4.app.Fragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Eugene Galkine on 9/2/2017.
 */

public class NetConnector extends Thread
{
    private static final String IP = "13.58.144.116";

    private int port;
    private String messageOnLaunch;
    private Fragment owner;

    private NetConnector(Fragment fragment, int port, String message)
    {
        //private constructor so it can only be initialized by the factory methods
        this.port = port;
        this.messageOnLaunch = message;
        owner = fragment;
        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            //connect to server and get the reader and writer
            Socket  socket = new Socket(IP, port);
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //write our request to the server
            outToServer.write((messageOnLaunch + "\nEND\n").getBytes());

            //wait for response and put all the lines into one resulting message
            String in;
            String result = "";
            while (!socket.isClosed() && (in = inFromServer.readLine()) != null && !in.equals("END"))
            {
                result += in + "\n";
            }

            if (owner instanceof ClientFragment)
                ((ClientFragment) owner).setResult(result);

            //close everything
            outToServer.close();
            inFromServer.close();
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void connectAsClient(ClientFragment clientFragment, int fromId, int toId, String input)
    {
        //factory method for when requesting a translation
        new NetConnector(clientFragment, 1627, "From: " + ClientFragment.languages[fromId] + " To: " + ClientFragment.languages[toId] + "\n\n" + input);
    }
}
