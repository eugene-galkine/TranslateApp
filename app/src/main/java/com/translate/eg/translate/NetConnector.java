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
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private Socket socket;

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
            socket = new Socket(IP, port);
            outToServer = new DataOutputStream(socket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //write our request to the server
            if (messageOnLaunch != null)
                outToServer.write((messageOnLaunch + "\nEND\n").getBytes());

            //wait for response and put all the lines into one resulting message
            String in;
            String result = "";
            while (!socket.isClosed() && (in = inFromServer.readLine()) != null && !in.equals("END"))
            {
                result += in + "\n";
            }

            //if we are the client, set the result and close connection, otherwise we wait for translator to respond before closing
            if (owner instanceof ClientFragment)
            {
                ((ClientFragment) owner).setResult(result);

                //close everything
                close();
            }
            else
            {
                ((TranslatorFragment) owner).setRequest(result);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendResult(String input)
    {
        try
        {
            outToServer.write((input + "\nEND\n").getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        close();
    }

    private void close()
    {
        //close everything
        try
        {
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

    public static NetConnector connectAsTranslator(TranslatorFragment clientFragment)
    {
        //factory method for when requesting a translation
        return new NetConnector(clientFragment, 1628, null);
    }
}
