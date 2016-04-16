package com.team8.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.team8.game.States.GameStateManager;

public class AndroidLauncher extends AndroidApplication implements GameStateManager.MyGameCallback{
	GameStateManager gsm = new GameStateManager();
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		gsm.setMyGameCallback(this);
		initialize(new MyGdxGame(gsm), config);

	}

	@Override
	public void onStartActivityLeaderboard() {
		Intent intent = new Intent(this, LeaderBoards.class);
		startActivity(intent);
	}
}
