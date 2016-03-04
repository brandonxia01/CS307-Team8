package com.team8.game;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class GooglePlayActivity extends AppCompatActivity
		implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,
		GoogleApiClient.ConnectionCallbacks, ResultCallback {
	private GoogleApiClient mGoogleApiClient;
	private static final int RC_SIGN_IN = 9001;
	private static final String TAG = "GooglePlayActivity";
	private byte[] mSaveGameData;
	private String[] eventID = {"CgkIs_nF5-EDEAIQAg", "CgkIs_nF5-EDEAIQBQ", "CgkIs_nF5-EDEAIQBg", "CgkIs_nF5-EDEAIQBw"};
	private TextView[] eventValueTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_play);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER)
				.build();

		findViewById(R.id.load_game_button).setOnClickListener(this);
		findViewById(R.id.test_save_button).setOnClickListener(this);

		findViewById(R.id.refresh_button).setOnClickListener(this);

		findViewById(R.id.time_inc_button).setOnClickListener(this);
		findViewById(R.id.time_dec_button).setOnClickListener(this);
		findViewById(R.id.games_inc_button).setOnClickListener(this);
		findViewById(R.id.games_dec_button).setOnClickListener(this);
		findViewById(R.id.good_inc_button).setOnClickListener(this);
		findViewById(R.id.good_dec_button).setOnClickListener(this);
		findViewById(R.id.bad_inc_button).setOnClickListener(this);
		findViewById(R.id.bad_dec_button).setOnClickListener(this);

		eventValueTV = new TextView[4];
		eventValueTV[0] = (TextView) findViewById(R.id.time_textview);
		eventValueTV[1] = (TextView) findViewById(R.id.games_textview);
		eventValueTV[2] = (TextView) findViewById(R.id.good_textview);
		eventValueTV[3] = (TextView) findViewById(R.id.bad_textview);

		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.d(TAG, "connection failed " + connectionResult.getErrorCode() + " " + connectionResult.getErrorMessage() + " " + connectionResult.hasResolution());
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void loadGame() {
		final String debugSnapshotName = "Debug-0000000001";

		AsyncTask<Void, Void, Integer> task = new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... params) {
				Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mGoogleApiClient, debugSnapshotName, true).await();

				if (result.getStatus().isSuccess()) {
					Snapshot snapshot = result.getSnapshot();
					try {
						mSaveGameData = snapshot.getSnapshotContents().readFully();

						String output;
						try {
							output = new String(mSaveGameData, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							output = new String(mSaveGameData);
						}
						Log.d(TAG, "" + output);
					} catch (IOException e) {
						Log.d(TAG, "Error reading snapshot");
					}
				} else {
					Log.d(TAG, "Error loading " + result.getStatus().getStatusCode());
				}
				return result.getStatus().getStatusCode();
			}
		};

		task.execute();
	}

	private void testSendSave() {
		String sampleData = "{user:bob,gamesPlayed:123,highscore:987}";
		byte[] data = new byte[0];
		try {
			data = sampleData.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.d(TAG, "charset");
		}
		final String debugSnapshotName = "Debug-0000000001";

		AsyncTask<byte[], Void, Snapshots.OpenSnapshotResult> task = new AsyncTask<byte[], Void, Snapshots.OpenSnapshotResult>() {
			byte[] data;

			@Override
			protected Snapshots.OpenSnapshotResult doInBackground(byte[]... params) {
				data = params[0];
				Snapshots.OpenSnapshotResult result = Games.Snapshots.open(mGoogleApiClient, debugSnapshotName, true).await();
				return result;
			}

			@Override
			protected void onPostExecute(Snapshots.OpenSnapshotResult result) {
				Snapshot toWrite = processSnapshotOpenResult(result, 0);
				Log.d(TAG, "" + data);
				writeSnapshot(toWrite, data);
			}
		};

		task.execute(data);
	}

	Snapshot processSnapshotOpenResult(Snapshots.OpenSnapshotResult result, int retryCount) {
		Snapshot mResolvedSnapshot;
		retryCount++;
		int status = result.getStatus().getStatusCode();

		Log.i(TAG, "Save Result status: " + status);

		if (status == GamesStatusCodes.STATUS_OK) {
			return result.getSnapshot();
		} else if (status == GamesStatusCodes.STATUS_SNAPSHOT_CONTENTS_UNAVAILABLE) {
			return result.getSnapshot();
		} else if (status == GamesStatusCodes.STATUS_SNAPSHOT_CONFLICT) {
			Snapshot snapshot = result.getSnapshot();
			Snapshot conflictSnapshot = result.getConflictingSnapshot();

			// Resolve between conflicts by selecting the newest of the conflicting snapshots.
			mResolvedSnapshot = snapshot;

			if (snapshot.getMetadata().getLastModifiedTimestamp() <
					conflictSnapshot.getMetadata().getLastModifiedTimestamp()) {
				mResolvedSnapshot = conflictSnapshot;
			}

			Snapshots.OpenSnapshotResult resolveResult = Games.Snapshots.resolveConflict(
					mGoogleApiClient, result.getConflictId(), mResolvedSnapshot)
					.await();

			if (retryCount < 3) {
				return processSnapshotOpenResult(resolveResult, retryCount);
			} else {
				String message = "Could not resolve snapshot conflicts";
				Log.e(TAG, message);
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
			}

		}
		// Fail, return null.
		return null;
	}

	private String writeSnapshot(Snapshot snapshot, byte[] data) {
		// Set the data payload for the snapshot.
		snapshot.getSnapshotContents().writeBytes(data);

		// Save the snapshot.
		SnapshotMetadataChange metadataChange = new SnapshotMetadataChange.Builder()
				.setDescription("Modified data at: " + Calendar.getInstance().getTime())
				.build();
		Games.Snapshots.commitAndClose(mGoogleApiClient, snapshot, metadataChange);
		return snapshot.toString();
	}

	private void submitEvent(int eventIndex, int change) {
		Games.Events.increment(mGoogleApiClient, eventID[eventIndex], change);
	}

	private void refreshEventValues() {
		PendingResult<Events.LoadEventsResult> pr = Games.Events.load(mGoogleApiClient, true);
		pr.setResultCallback(this);
	}

	@Override
	public void onResult(Result result) {
		Log.d(TAG, "onResult " + result.toString());
		Events.LoadEventsResult r = (Events.LoadEventsResult) result;
		Log.d(TAG, "onResult " + r.toString());
		EventBuffer eb = r.getEvents();
		Log.d(TAG, "onResult " + eb.toString());

		for (int i = 0; i < eb.getCount(); i++) {
			Log.d(TAG, "onResult " + eb.get(i).toString());
			for (int x = 0; x < eventID.length; x++) {
				if (eb.get(i).getEventId().equals(eventID[x])) {
					eventValueTV[x].setText("" + ((int) eb.get(i).getValue()));
				}
			}
		}
		eb.close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.load_game_button:
				loadGame();
				break;
			case R.id.test_save_button:
				testSendSave();
				break;
			case R.id.time_inc_button:
				submitEvent(0, 123);
				break;
			case R.id.time_dec_button:
				submitEvent(0, -123);
				break;
			case R.id.games_inc_button:
				submitEvent(1, 1);
				break;
			case R.id.games_dec_button:
				submitEvent(1, -1);
				break;
			case R.id.good_inc_button:
				submitEvent(2, 1);
				break;
			case R.id.good_dec_button:
				submitEvent(2, -1);
				break;
			case R.id.bad_inc_button:
				submitEvent(3, 1);
				break;
			case R.id.bad_dec_button:
				submitEvent(3, -1);
				break;
			case R.id.refresh_button:
				refreshEventValues();
				break;
		}
	}

	@Override
	public void onConnected(Bundle bundle) {
		Log.d(TAG, "onConnected");
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.d(TAG, "onConnectionSuspended");
	}

}
