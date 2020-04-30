package pds.stardust.frequentation.Model;
import org.springframework.data.annotation.Id;


public class Data {

    private int idclient ;
    private String idbeacon ;
    private String date ;

    public Data(int idclient, String idbeacon, String date) {
        this.idclient = idclient;
        this.idbeacon = idbeacon;
        this.date = date;
    }

    public Data() {
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public String getIdbeacon() {
        return idbeacon;
    }

    public void setIdbeacon(String idbeacon) {
        this.idbeacon = idbeacon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Data{" +
                "idclient=" + idclient +
                ", idbeacon=" + idbeacon +
                ", date=" + date +
                '}';
    }
}
