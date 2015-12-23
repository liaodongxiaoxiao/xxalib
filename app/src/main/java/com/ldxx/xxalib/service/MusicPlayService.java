package com.ldxx.xxalib.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.ldxx.xxalib.beans.XXMusic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MusicPlayService extends IntentService {
    private static final String TAG = "MusicPlayService";
    private MediaPlayer mediaPlayer;
    private int currentPasition = 0;

    private Map<String, XXMusic> tmp = new HashMap<>();

    public MusicPlayService(String name) {
        super(name);
    }

    public MusicPlayService() {
        super("MusicPlayService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent: ");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Log.i("Completion Listener", "Song Complete");
                mp.stop();
            }
        });
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPasition = mediaPlayer.getCurrentPosition();
            if (listener != null) {
                listener.onPause();
            }
        }
    }

    public void resume(){
        play(tmp.get("current"));
    }
    public void play(XXMusic music) {

        try {
            Log.e(TAG, "playOrPlay: " + music.getUrl());
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getUrl());
            mediaPlayer.prepare();
            mediaPlayer.seekTo(currentPasition);
            mediaPlayer.start();
            if(listener!=null){
                listener.onPlaying(music);
            }
            tmp.put("current",music);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class MusicBinder extends Binder {
        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }

    private MusicPlayingListener listener;

    public void setListener(MusicPlayingListener listener) {
        this.listener = listener;
    }

    public interface MusicPlayingListener {
        void onPlaying(XXMusic music);

        void onPause();

        void onCompletion(XXMusic music);
    }
}
