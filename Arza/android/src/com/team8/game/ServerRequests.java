package com.team8.game;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ayushnvijay on 4/28/16.
 */
public class ServerRequests {
    public static boolean startSocket(Socket socket){
        try{
            socket = new Socket("ec2-52-34-71-58.us-west-2.compute.amazonaws.com", 9000);
            return true;
        }
        catch (Exception e){
            Log.i("ERROR","Socket Initialization");
            return false;
        }
    }

    public static boolean startConnection(Socket echoSocket) {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write("" + 3);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String str = in.readLine();
            Log.i("CHECK",str+"");
            in.close();
            if(str.equals("0039")){
                return true;
            }

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
        return false;
    }
    public static boolean isMatchFound(Socket echoSocket) {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String str = in.readLine();
            Log.i("CHECK",str+"");
            in.close();
            if(str.equals("0012")){
                return true;
            }

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
        return false;
    }
    public static String getData(Socket echoSocket) {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String str = in.readLine();
            Log.i("CHECK",str+"");
            in.close();
            return str;

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
        return null;
    }
    public static boolean sendData(Socket echoSocket, String str) {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write(str.length()+"");
            out.flush();
            out.write(str);
            out.flush();
            return true;

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
            return false;
        }
    }
}

