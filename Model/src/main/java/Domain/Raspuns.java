package Domain;

import java.io.Serializable;

public class Raspuns implements Serializable {
    private String masina;
    private String tigari;
    private String telefon;
    private int points;
    private String username;
    private int id;

    public Raspuns(int id,String masina, String tigari, String telefon,int points,String username) {
        this.masina = masina;
        this.tigari = tigari;
        this.telefon = telefon;
        this.username = username;
        this.points = points;
        this.id = id;
    }

    public Raspuns() {
    }

    public String getMasina() {
        return masina;
    }

    public void setMasina(String masina) {
        this.masina = masina;
    }

    public String getTigari() {
        return tigari;
    }

    public void setTigari(String tigari) {
        this.tigari = tigari;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

