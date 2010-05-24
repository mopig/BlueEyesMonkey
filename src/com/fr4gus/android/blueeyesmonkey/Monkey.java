package com.fr4gus.android.blueeyesmonkey;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import com.admob.android.ads.AdManager;

public class Monkey extends Activity implements OnTouchListener {

    /** Called when the activity is first created. */

    MediaPlayer obeyMp;

    MediaPlayer listenMp;

    ImageView monkey;

    Drawable monkeyActive;

    Drawable monkeyInactive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        AdManager.setTestDevices(new String[] { AdManager.TEST_EMULATOR });

        Context context = getBaseContext();
        obeyMp = MediaPlayer.create(context, R.raw.obey);
        obeyMp.setLooping(true);

        listenMp = MediaPlayer.create(context, R.raw.monkey);
        listenMp.setLooping(true);

        Button obeyButton = (Button) findViewById(R.id.ObeyButton);
        Button listenButton = (Button) findViewById(R.id.ListenButton);
        monkey = (ImageView) findViewById(R.id.ImageMonkey);

        Resources resources = getResources();
        monkeyActive = resources.getDrawable(R.drawable.monkey_active);
        monkeyInactive = resources.getDrawable(R.drawable.monkey_inactive);

        obeyButton.setOnTouchListener(this);
        listenButton.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        MediaPlayer player = null;
        if (v.getId() == R.id.ObeyButton) {
            player = obeyMp;
        } else if (v.getId() == R.id.ListenButton) {
            player = listenMp;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            monkey.setImageDrawable(monkeyActive);
            player.start();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            monkey.setImageDrawable(monkeyInactive);
            player.pause();
            player.seekTo(0);
        }
        return false;
    }

}