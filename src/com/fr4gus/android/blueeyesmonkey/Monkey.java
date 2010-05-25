package com.fr4gus.android.blueeyesmonkey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import com.admob.android.ads.AdManager;

public class Monkey extends Activity implements OnTouchListener,
    OnClickListener, OnKeyListener{

  /** Called when the activity is first created. */

  private MediaPlayer obeyMp;

  private MediaPlayer listenMp;

  private ImageView monkey;

  private Drawable monkeyActive;

  private Drawable monkeyInactive;

  private boolean playerActive = false;

  private Button obeyButton;

  private Button listenButton;

  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

//    AdManager.setTestDevices(new String[] {
//      AdManager.TEST_EMULATOR
//    });

    Context context = getApplicationContext();
    obeyMp = MediaPlayer.create(context, R.raw.obey);
    obeyMp.setLooping(true);

    listenMp = MediaPlayer.create(context, R.raw.monkey);
    listenMp.setLooping(true);

    obeyButton = (Button)findViewById(R.id.ObeyButton);
    listenButton = (Button)findViewById(R.id.ListenButton);
    monkey = (ImageView)findViewById(R.id.ImageMonkey);

    Resources resources = getResources();
    monkeyActive = resources.getDrawable(R.drawable.monkey_active);
    monkeyInactive = resources.getDrawable(R.drawable.monkey_inactive);

    obeyButton.setOnTouchListener(this);
    obeyButton.setOnKeyListener(this);
    obeyButton.setOnClickListener(this);

    listenButton.setOnTouchListener(this);
    listenButton.setOnKeyListener(this);
    listenButton.setOnClickListener(this);
  }

  @Override
  public boolean onTouch(View v, MotionEvent event){
    int action = event.getAction();
    handleMotionEvent(v, action);
    return true;
  }

  @Override
  public void onClick(View v){
    handleMotionEvent(v, MotionEvent.ACTION_DOWN);
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event){
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void onDestroy(){
    obeyMp.stop();
    listenMp.stop();
    super.onDestroy();
  }

  private void handleMotionEvent(View v, int action){
    MediaPlayer player = null;
    Button buttonToDisable = null;
    if(v.getId() == R.id.ObeyButton){
      player = obeyMp;
      buttonToDisable = listenButton;
    }else if(v.getId() == R.id.ListenButton){
      player = listenMp;
      buttonToDisable = obeyButton;
    }

    if(action == MotionEvent.ACTION_DOWN){
      if(playerActive){
        monkey.setImageDrawable(monkeyInactive);
        player.pause();
        player.seekTo(0);
        buttonToDisable.setEnabled(true);
      }else{
        monkey.setImageDrawable(monkeyActive);
        player.start();
        buttonToDisable.setEnabled(false);
      }
      playerActive = !playerActive;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.options_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()){
      case R.id.aboutMenuItem:
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        startActivity(intent);
    }
    return true;
  }
}