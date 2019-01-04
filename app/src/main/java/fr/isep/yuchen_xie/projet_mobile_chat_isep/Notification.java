package fr.isep.yuchen_xie.projet_mobile_chat_isep;

public class Notification {
    String date,fromuser,status,telephonenumber,donname;

    public Notification(String date, String fromuser, String status, String telephonenumber, String donname) {
        this.date = date;
        this.fromuser = fromuser;
        this.status = status;
        this.telephonenumber = telephonenumber;
        this.donname = donname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephonenumber() {
        return telephonenumber;
    }

    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber = telephonenumber;
    }

    public String getDonname() {
        return donname;
    }

    public void setDonname(String donname) {
        this.donname = donname;
    }
}
