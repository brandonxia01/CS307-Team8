package com.team8.game;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class  StartMultiPlayer extends AsyncTask<String,Void,Object> {
    static String incoming = "";
    static Socket echoSocket;
    static Object result;
    /*
    * req:
    *       1 startSocket
    *       2 sendData
    *
    *       new StartMultiPlayer().execute("startSocket"); will update incoming string
    *       result will be false, or will go in infinite loop
    *
    *       new StartMultiPlayer().execute("sendData","Whatever data you want to send"); will also change result
    *       //if result is false, means it failed to send data to server.
    *       Major issue, startSocket and sendData, both should be logically synchronized otherwise.....GG RIP
    *
    * */
    public static boolean startSocket(){
        try{
            echoSocket = new Socket("ec2-52-34-71-58.us-west-2.compute.amazonaws.com", 9000);
            Log.i("Socket","Started");
            return true;
        }
        catch (Exception e){
            Log.i("ERROR","Socket Initialization");
            return false;
        }
    }
    public static boolean startConnection() {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write("" + 3);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String str = in.readLine();
            Log.i("CHECK",str+"");
            in.close();
            Log.i("CHECK",str+"");
            if(str.equals("0039")){
                Log.i("CHECK",str+"");
                return true;
            }

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
        Log.i("CHECK","FALSE");
        return false;
    }
    public static boolean isMatchFound() {
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
    public static String getData() {
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
    public static boolean sendData(String send) {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write(send.length()+"");
            out.flush();
            out.write(send);
            out.flush();
            return true;

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
            return false;
        }
    }

    @Override
    protected Object doInBackground(String... params) {
        if(params[0].equals("startSocket")){
            if(startSocket()){
                if(startConnection()){
                    while (!isMatchFound()){
                        String str;
                        while ((str = getData())!=null){
                            incoming = str;
                        }
                        return false;
                    }
                }
            }
            return false;
        }
        else {
            return sendData(params[1]);
        }
    }
    @Override
    protected void onPostExecute(Object obj){
        result = obj;
        Log.i("result",result+"");

    }
    @Override
    protected void onCancelled() {

        super.onCancelled();
    }
}
