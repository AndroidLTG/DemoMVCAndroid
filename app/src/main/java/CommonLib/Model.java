package CommonLib;

import android.util.Log;

/**
 * Created by My PC on 26/11/2015.
 */
public class Model {
    private static Model instance = null;
    private Model() { }
    public synchronized static Model inst(){
        if (instance == null) {
            instance = new Model();
            Log.i("Model", "Create new instance");
        }
        return instance;
    }

    private Object text_locker = new Object();
    private String text;
    public void setText(String text) {
        synchronized (text_locker) {
            this.text = text;
        }
    }
    public String getText() {
        synchronized (text_locker) {
            return this.text;
        }
    }

    private Object checked_locker = new Object();
    private boolean checked;
    public void setChecked(boolean checked) {
        synchronized (checked_locker) {
            this.checked = checked;
        }
    }
    public boolean isChecked() {
        synchronized (checked_locker) {
            return this.checked;
        }
    }
}
