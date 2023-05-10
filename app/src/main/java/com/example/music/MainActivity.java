package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player = null;
    ListView list;
    String [] st = {"Song1", "Song2", "Song3"};
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        player = MediaPlayer.create(this,R.raw.song3);

        seekBar = findViewById(R.id.seek);
        list = findViewById(R.id.lst);




        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (player != null) {
                    seekBar.setMax(player.getDuration());

                    seekBar.setProgress(player.getCurrentPosition());

                }
            }
        },0,900);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int  progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                player.seekTo(seekBar.getProgress());

            }
        });

        ArrayAdapter <String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,st);
        list.setAdapter(arr);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    player = MediaPlayer.create(getApplicationContext(),R.raw.song1);
                }

                if (i==1) {
                    if (player != null) {
                        player.release();
                        player = null;
                        player = MediaPlayer.create(getApplicationContext(), R.raw.song2);
                    }
                }
                if (i==2) {
                    if (player != null) {
                        player.release();
                        player = null;

                        player = MediaPlayer.create(getApplicationContext(), R.raw.song3);
                    }
                }

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);

    }



    public void play(View v) {
        if (player == null){
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }

        player.start();

    }

    public void pause(View v) {

        if (player != null){
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();

    }
    private  void stopPlayer(){
        if (player != null){
            player.release();
            player=null;
            Toast.makeText(this, "Media player", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}