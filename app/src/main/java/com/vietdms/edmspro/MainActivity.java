package com.vietdms.edmspro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import CommonLib.Const;
import CommonLib.EventPool;
import CommonLib.EventType;
import Controller.ControlThread;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, BackgroundService.class));
    }
    @Override
    protected void onStart() {
        Log.d("MainActivity", "onStart begin");
        super.onStart();
        isRunning = true;
        handler.postDelayed(runnable, Const.QueueTimerView);
        Log.d("MainActivity", "onStart end");
    }
    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop begin");
        super.onStop();
        isRunning = false;
        Log.d("MainActivity", "onStop end");
    }
    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy begin");
        super.onDestroy();
        System.exit(0);
        Log.d("MainActivity", "onDestroy end");
    }

    private boolean isRunning = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.v("QueueTimerView", "timedout");
            EventType.EventBase event = EventPool.view().deQueue();
            while (event != null) {
                processEvent(event);
                if (!isRunning) break;
                event = EventPool.view().deQueue();
            }
            if (isRunning) handler.postDelayed(this, Const.QueueTimerView);
        }
    };
    private void processEvent(EventType.EventBase event) {
        switch (event.type) {
            case Login:
                break;
            case ChangePass:
                break;
            default:
                Log.w("View_processEvent", "unhandled " + event.type);
                break;
        }
    }
}
