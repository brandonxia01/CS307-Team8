package com.team8.game;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team8.game.dummy.DummyContent;
import com.team8.game.dummy.DummyContent.DummyItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ScoresFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter recyclerAdapter;
    int mode;
    static ArrayList<String> ITEMS1;
    static ArrayList<String> ITEMS2;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScoresFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ScoresFragment newInstance(int columnCount) {
        ScoresFragment fragment = new ScoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_score, container, false);
        Bundle bundle = getArguments();


        recyclerView = (RecyclerView) view;
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }

        if((mode = bundle.getInt("mode")) == 1){
            Log.i("Mode"," 1");
            recyclerAdapter = new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener);

        }
        else{
            mode = 2;
            Log.i("Mode"," 2");
            recyclerAdapter = new MyItemRecyclerViewAdapter(DummyContent.ITEMS2, mListener);
        }
        recyclerView.setAdapter(recyclerAdapter);

        // Set the adapter
        new DoNetworking().execute();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    public static String sendGetReq(int type, int reqType, String arg) {
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
            String str = in.readLine();
            Log.i("CHECK",str+"");
            in.close();
            echoSocket.close();
            return str;

        } catch (Exception e) {
            Log.i("CHECK","ERROR");
        }
        return null;
    }
    class DoNetworking extends AsyncTask<Void,Void,String> {

        private DoNetworking() {
            super();

        }

        @Override
        protected String doInBackground(Void... params) {

            String str = sendGetReq(1, 8, "1");
            Log.i("Top Scorers",str+"");
            return str;
        }
        @Override
        protected void onPostExecute(String str){
            //Parse it here
            if(mode == 2){
                DummyContent.ITEMS2.clear();
                int i = 0;
                for (String retval: str.split(";")){
                    String newString[] = retval.split(",");
                    DummyContent.addItem(DummyContent.createDummyItem(i,newString[0]+"\n"+newString[1]),DummyContent.ITEMS2);
                    i++;
                }
                recyclerAdapter = new MyItemRecyclerViewAdapter(DummyContent.ITEMS2, mListener);
                recyclerView.setAdapter(recyclerAdapter);
            }
            else{

            }

        }


    }
}
