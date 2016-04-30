package com.team8.game;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by ayushnvijay on 4/27/16.
 */
public class GameModesFragment extends Fragment {
    Button challengeButton;
    Button endlessButton;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_modes, container, false);
        challengeButton = (Button) view.findViewById(R.id.challenge_mode_scores);
        endlessButton = (Button) view.findViewById(R.id.endless_mode_scores);
        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoNetworking().execute();
            }
        });
        return view;
    }
    public static void sendGetReq(int type, int reqType, String arg) {
        try {
            Socket echoSocket = new Socket("ec2-52-34-71-58.us-west-2.compute.amazonaws.com", 9000);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.write("" + type);
            out.flush();
            out.write("0" + reqType);
            out.flush();
            out.write(arg);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            System.out.println(in.readLine());
            echoSocket.close();
        } catch (Exception e) {

        }
    }
    class DoNetworking extends AsyncTask<Void,Void,Void>{

        private DoNetworking() {
            super();

        }

        @Override
        protected Void doInBackground(Void... params) {
            sendGetReq(1, 2, "ayushVijayjayjay");
            sendGetReq(1, 3, "ayushVijayjayjay");
            sendGetReq(1, 4, "ayushVijayjayjay");
            sendGetReq(1, 5, "ayushVijayjayjay");
            sendGetReq(1, 6, "ayushVijayjayjay");
            sendGetReq(1, 7, "ayushVijayjayjay");
            sendGetReq(1, 8, "1");
            sendGetReq(2, 1, "{'username':'ayeshEatsEggs'}");
            sendGetReq(2, 2, "{'username':'ayeshEatsEggs', 'gamemode':1, 'score':159357}");
            sendGetReq(2, 4, "{'followingName':'ayushVijayjayjay', 'followerName':'ayeshEatsEggs'}");
            return null;
        }


    }
}
