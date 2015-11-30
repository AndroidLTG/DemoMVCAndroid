package com.example.mypc.demomvc;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import CommonLib.EventBase;
import CommonLib.EventPool;
import CommonLib.EventString;
import CommonLib.Model;
import Controller.ControlThread;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arr = new ArrayList<>();
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    private RelativeLayout layout2,layout3,layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 100); //
        Button btn = (Button) findViewById(R.id.button);
        layout2  =(RelativeLayout) findViewById(R.id.layout_2);
        layout3  =(RelativeLayout) findViewById(R.id.layout_3);
        layoutMain = (RelativeLayout) findViewById(R.id.layout_main);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.editText);
                if (!edit.getText().toString().isEmpty()) {
                    CommonLib.Model.inst().setText((edit.getText().toString()));
                    EventPool.control().Enqueue(new EventBase(EventBase.Type.TEXT));
                    edit.setText("");
                }
            }
        });
        Button btnSwap1 = (Button) findViewById(R.id.buttonSwap1);
        btnSwap1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              layout2.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
            }
        });
        Button btnSwap2 = (Button) findViewById(R.id.buttonSwap2);
        btnSwap2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                layout3.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
            }
        });
        Button btnBack1  =(Button) findViewById(R.id.btnBack1);
        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.GONE);
                layoutMain.setVisibility(View.VISIBLE);
            }
        });
        Button btnBack2  =(Button) findViewById(R.id.btnBack2);
        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout3.setVisibility(View.GONE);
                layoutMain.setVisibility(View.VISIBLE);
            }
        });
        CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
        chk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
                CommonLib.Model.inst().setChecked(chk.isChecked());
                EventPool.control().Enqueue(new EventBase(EventBase.Type.CHECK));
            }
        });
        ListView lst = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_activated_1, arr);
        lst.setAdapter(adapter);

        lst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lst.setSelector(R.color.colorPrimary);
    }

    private void init() {
        Model.inst();
        EventPool.control();
        EventPool.view();
        ControlThread.inst().start();
    }


    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        EventBase event = EventPool.view().Dequeue();
                        if (event != null) {
                            switch (event.type) {
                                case STRING:
                                    EventString eventString = (EventString) event;
                                    arr.add("Input text: " + eventString.data);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case BOOLEAN:
                                    EventString eventStringCheck = (EventString) event;
                                    arr.add("Checkbox is " + eventStringCheck.checkData);
                                    adapter.notifyDataSetChanged();
                                default:
                                    Log.w("handleMessage_TIMER", "unhandled");
                                    break;
                            }
                        }
                    }
                });
            }
        };
    }
}
