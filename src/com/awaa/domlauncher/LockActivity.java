package com.awaa.domlauncher;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import locker.glowpad.GlowPadView;
import locker.lockscreen.MyService;

public class LockActivity extends Activity{

//    @Override
//    public void onAttachedToWindow() {
//        // TODO Auto-generated method stub
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG | WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        super.onAttachedToWindow();
//    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Toast.makeText(this, "" + "LockActivity", Toast.LENGTH_SHORT).show();

//    	   setContentView(R.layout.main);
        setContentView(R.layout.main1);
//        droid = (ImageView) findViewById(R.id.droid);

        WebView wv;
        wv = (WebView) findViewById(R.id.webView1);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

        wv.loadUrl("file:///android_asset/clock/clock.html");

        final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
        glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
            @Override
            public void onGrabbed(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onReleased(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onTrigger(View v, int target) {
//                Toast.makeText(LockScreenAppActivity.this, "Target triggered! ID=" + target, Toast.LENGTH_SHORT).show();
                if(target==0){

                    glowPad.reset(true);
                    finish();
                }else{

                    glowPad.reset(true);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                }
            }

            @Override
            public void onGrabbedStateChange(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onFinishFinalAnimation() {
                // Do nothing
            }
        });

        if (getIntent() != null && getIntent().hasExtra("kill") && getIntent().getExtras().getInt("kill") == 1) {
            Toast.makeText(this, "" + "Kill Activity", Toast.LENGTH_SHORT).show();
            finish();
        }

        try {
            // initialize receiver
//            if(!isMyServiceRunning(MyService.class))
//            startService(new Intent(this, MyService.class));

            StateListener phoneStateListener = new StateListener();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("Call Activity off hook");
                    finish();

                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }

    ;

    public void onSlideTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;


        }

    }

    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.
        return;
    }

    //only used in lockdown mode
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) || (keyCode == KeyEvent.KEYCODE_POWER) || (keyCode == KeyEvent.KEYCODE_VOLUME_UP) || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            //this is where I can do my stuff
            return true; //because I handled the event
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {

            return true;
        }

        return false;

    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
            return false;
        }
        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
            return true;
        }
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
    }

}
