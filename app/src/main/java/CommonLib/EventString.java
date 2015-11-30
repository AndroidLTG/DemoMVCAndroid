package CommonLib;

public class  EventString extends EventBase{

    public EventString(String data) {
        super(Type.STRING);
        this.data = data;
    }
    public String data;
    
    public EventString(boolean checkData){
        super((Type.BOOLEAN));
        this.checkData =checkData;
    }
    public Boolean checkData;
}
