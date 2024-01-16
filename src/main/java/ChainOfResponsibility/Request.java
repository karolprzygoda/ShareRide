package ChainOfResponsibility;

import java.io.Serializable;

public class Request implements Serializable {
    private final RequestType type;
    private Object dataToManage;

    public Request(RequestType type) {
        this.type = type;
    }

    public Object getDataToManage(){
        return dataToManage;
    }

    public void setDataToManage(Object dataToManage){
        this.dataToManage = dataToManage;
    }

    public RequestType getType() {
        return type;
    }

    public enum RequestType {
        LOGIN,INSERT,SELECT,SELECT_ALL,SELECT_ID,UPDATE,DELETE,CHECK_IF_ALREADY_IN_RIDE
    }

}
