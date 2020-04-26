package pds.stardust.frequentation.Model;
import org.springframework.data.annotation.Id;


public class Data {
    @Id
    private int idclient ;
    private int idbeacon ;
    private String date ;

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public int getIdbeacon() {
        return idbeacon;
    }

    public void setIdbeacon(int idbeacon) {
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
