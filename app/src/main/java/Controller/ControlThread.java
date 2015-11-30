package Controller;

import android.util.Log;

import CommonLib.EventBase;
import CommonLib.EventPool;
import CommonLib.EventString;
import CommonLib.Model;

/**
 * Created by My PC on 27/11/2015.
 */
public class ControlThread extends Thread{
    private static ControlThread instance = null;
    private ControlThread() {super(); }
    public synchronized static ControlThread inst(){
        if (instance == null) {
            instance = new ControlThread();
            Log.i("ControlThread", "Create new instance");
        }
        return instance;
    }

    @Override
    public void run() {
        try {
            Log.w("Thread Control",Thread.currentThread().getId()+"");
            while(true) {
                EventBase event = EventPool.control().Dequeue();
                if (event == null) {
                    sleep(30);
                }
                else {
                    processEvent(event);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void processEvent(EventBase event) {
        switch (event.type) {
            case TEXT:
                Log.i("processEvent", "TEXT " + Model.inst().getText());
                EventPool.view().Enqueue(new EventString( CommonLib.Model.inst().getText()));
                break;
            case CHECK:
                Log.i("processEvent", "CHECK " + Model.inst().isChecked());
                EventPool.view().Enqueue(new EventString(Model.inst().isChecked()));
                break;
            default:
                Log.i("processEvent", "unhandled " + event.type);
                break;
        }
    }
}
