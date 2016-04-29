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
    *       2 startConnection
    *       3 isMatchFound
    *       4 getData
    *       5 sendData
    *       new StartMultiPlayer().execute("whatever");
    *       //if result is null
    *
    * */
    public static boolean startSocket(){
        try{
            echoSocket = new Socket("ec2-52-34-71-58.us-west-2.compute.amazonaws.com", 9000);
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
            if(str.equals("0039")){
                return true;
            }

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
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
    public static boolean sendData() {
        try {
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write(incoming.length()+"");
            out.flush();
            out.write(incoming);
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
            return startSocket();
        }
        else if(params[0].equals("startConnection")){
            return startConnection();
        }
        else if(params[0].equals("isMatchFound")){
            return isMatchFound();
        }
        else if(params[0].equals("getData")){
            return getData();
        }
        else {
            return sendData();
        }
    }
    @Override
    protected void onPostExecute(Object obj){
        result = obj;
    }
    @Override
    protected void onCancelled() {

        super.onCancelled();
    }
}
