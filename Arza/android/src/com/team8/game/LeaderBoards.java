package com.team8.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.team8.game.dummy.DummyContent;

import io.karim.MaterialTabs;

public class LeaderBoards extends FragmentActivity implements ScoresFragment.OnListFragmentInteractionListener {
    /*private final OkHttpClient client = new OkHttpClient();
    final static String url = "";*/

    private MaterialTabs tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);
        Intent getIntent = getIntent();
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter adapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs = (MaterialTabs)findViewById(R.id.material_tabs);
        tabs.setViewPager(pager);


    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    public class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new ScoresFragment();
            }
            if(position==1){
                return new ScoresFragment();
            }
            else {
                return new FriendsList();
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "ACHIEVEMENTS";
            }
            if(position==1){
                return "TOP SCORERS";
            }
            if(position==2){
                return "FRIENDS LIST";
            }

            return null;
        }
    }


    /*public void run() throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String str = response.body().string();
                //parse integer value
                int scores = Integer.parseInt(str);
            }
        });
    }*/
}
