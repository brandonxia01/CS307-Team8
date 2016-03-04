package com.team8.game;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;

public class GooglePlayActivity extends AppCompatActivity
		implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,
		GoogleApiClient.ConnectionCallbacks {
	private GoogleApiClient mGoogleApiClient;
	private static final int RC_SIGN_IN = 9001;
	private static final int RC_LOAD_GAME = 9002;
	private static final String TAG = "GooglePlayActivity";
	private byte[] mSaveGameData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_play);

//		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//				.requestEmail()
//				.build();

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER)
//				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.load_game_button).setOnClickListener(this);
		findViewById(R.id.test_save_button).setOnClickListener(this);

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

		switch (requestCode) {
			case RC_SIGN_IN:
				GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
				if (result != null)
					handleSignInResult(result);
				break;
//			case RC_LOAD_GAME:
//				handleLoadGame(data);
		}
	}

	private void handleSignInResult(GoogleSignInResult result) {
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();
			Log.d(TAG, "display name:" + acct.getDisplayName());
		}
	}

//	private void handleLoadGame(Intent data) {
//		if (data != null) {
//			if (data.hasExtra(Snapshots.EXTRA_SNAPSHOT_METADATA)) {
//				SnapshotMetadata snapshotMetadata = data.getParcelableExtra(Snapshots.EXTRA_SNAPSHOT_METADATA);
//				String mCurrentSaveName = snapshotMetadata.getUniqueName();
//				Log.d(TAG, "current save: " + mCurrentSaveName);
//				Log.d(TAG, "snapshot meta: " + data.toString());
//			} else if (data.hasExtra(Snapshots.EXTRA_SNAPSHOT_NEW)) {
//				//generate unique string for snapshot
//			}
//		}
//	}

	private void signIn() {
		startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), RC_SIGN_IN);
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
						Log.d(TAG,""+output);
					} catch (IOException e) {
						Log.d(TAG, "Error reading snapshot");
					}
				} else {
					Log.d(TAG, "Error loading "+result.getStatus().getStatusCode());
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
			Log.d(TAG,"charset");
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
				Log.d(TAG,""+data);
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


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.sign_in_button:
				signIn();
				break;
			case R.id.load_game_button:
				loadGame();
				break;
			case R.id.test_save_button:
				testSendSave();
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
