package pds.stardust.scsensorinteraction.beans;

public class CommonResponse {

    private String data;

    public CommonResponse(String data) {
        this.data = data;
    }

    public CommonResponse() { }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
