package com.ldxx.xxalib.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ldxx.android.base.adapter.XXBaseAdapter;
import com.ldxx.android.base.bean.XXViewHolder;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXMusic;
import com.ldxx.xxalib.service.MusicPlayService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 音乐播放demo
 * 1. 获取本地音乐
 * 2. Service 绑定
 * 3. Audio Focus
 */
public class CaseMusicPlayActivity extends AppCompatActivity {
    private static final String TAG = "CaseMusicPlayActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.music_list)
    ListView musicList;
    @Bind(R.id.music_img)
    ImageView musicImg;
    @Bind(R.id.music_title)
    TextView musicTitle;
    @Bind(R.id.music_artist)
    TextView musicArtist;
    @Bind(R.id.music_next)
    ImageView musicNext;
    @Bind(R.id.music_play_pause)
    ImageView musicPlayPause;

    private List<XXMusic> musicData = new ArrayList<>();

    private XXMusic currentMusic;
    private boolean isPlaying;
    private MusicPlayService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_music_play);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        loadMusic();

        MusicAdapter adapter = new MusicAdapter(this, musicData, R.layout.content_case_music_item);
        musicList.setAdapter(adapter);

        Intent intent = new Intent(this, MusicPlayService.class);
        bindService(intent, con, Context.BIND_AUTO_CREATE);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playOrPause(musicData.get(position));
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(con);
    }

    ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: " + name.getClassName());
            mService = ((MusicPlayService.MusicBinder) (service)).getService();
            mService.setListener(new MusicPlayService.MusicPlayingListener() {
                @Override
                public void onPlaying(XXMusic music) {
                    isPlaying = true;
                    musicPlayPause.setImageResource(R.drawable.play);
                    currentMusic = music;
                }

                @Override
                public void onPause() {
                    isPlaying = false;
                    musicPlayPause.setImageResource(R.drawable.pause);
                }

                @Override
                public void onCompletion(XXMusic music) {
                    isPlaying = false;
                    currentMusic = null;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick(R.id.music_play_pause)
    void playOrPauseMusic(View view) {
        if (isPlaying) {
            playOrPause(currentMusic);

        } else {
            if (!musicData.isEmpty()) {
                playOrPause(musicData.get(0));

            } else {
                Toast.makeText(CaseMusicPlayActivity.this, "没有可播放的音乐", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void playOrPause(XXMusic music) {
        if (mService != null) {
            if (isPlaying) {
                mService.pause();
            } else if (currentMusic != null) {
                mService.resume();
            } else {
                mService.play(music);
            }
        }
    }

    private void loadMusic() {
        ContentResolver musicResolver = this.getContentResolver();
        Cursor musicCursor = musicResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            XXMusic music;
            do {
                music = new XXMusic();
                //id
                music.setId(musicCursor.getInt(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                //title
                music.setTitle(musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                //album
                music.setAlbum(musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                //
                music.setArtist(musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));

                music.setDuration(musicCursor.getInt(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));

                music.setSize(musicCursor.getInt(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));

                music.setUrl(musicCursor.getString(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

                musicData.add(music);
            }
            while (musicCursor.moveToNext());

            musicCursor.close();
        }

    }

    class MusicAdapter extends XXBaseAdapter<XXMusic> {

        public MusicAdapter(Context context, List<XXMusic> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        public void convert(XXViewHolder helper, XXMusic item) {
            helper.setText(R.id.music_title, item.getTitle());
            StringBuilder msg = new StringBuilder();
            msg.append(TextUtils.isEmpty(item.getArtist()) ? "未知" : item.getArtist())
                    .append(" - ").append(TextUtils.isEmpty(item.getAlbum()) ? "未知" : item.getAlbum());
            helper.setText(R.id.music_artist_album, msg.toString());
        }
    }

}
