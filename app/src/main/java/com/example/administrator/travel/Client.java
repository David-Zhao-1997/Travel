package com.example.administrator.travel;


import android.os.Environment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class Client extends Socket
{

    private static final String SERVER_IP = "192.168.0.153"; // ?????IP
//    private static final String SERVER_IP = "192.168.43.44"; // ?????IP
    private static final int SERVER_PORT = 8899; // ???????

    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    /**
     * ??????<br/>
     * ???????????????
     *
     * @throws Exception
     */
    public Client() throws Exception
    {
        super(SERVER_IP, SERVER_PORT);
        this.client = Client.this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] ???????????");
    }

    /**
     * ????????????
     *
     * @throws Exception
     */
    public void sendFile(String path) throws Exception
    {
        try
        {
            File file = new File(path);
            if (file.exists())
            {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());

                // ??????????
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                // ??????????
                System.out.println("======== ??????? ========");
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;
                while ((length = fis.read(bytes, 0, bytes.length)) != -1)
                {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100 * progress / file.length()) + "% |\n");
                }
                System.out.println();
                System.out.println("======== ??????? ========");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
            {
                fis.close();
            }
            if (dos != null)
            {
                dos.close();
            }
            client.close();
        }
    }

    /**
     * ???
     *
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            Client client = new Client(); // ?????????????
            client.sendFile(Environment.getExternalStorageDirectory().toString() + "/abc.png"); // ???????
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}