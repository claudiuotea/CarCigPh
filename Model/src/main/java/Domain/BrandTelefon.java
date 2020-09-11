package Domain;

import java.io.Serializable;
import java.util.Objects;

public class BrandTelefon implements Serializable {
    private String nume;
    private int id;

    public BrandTelefon() {
    }

    public BrandTelefon(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandTelefon brandTelefon = (BrandTelefon) o;
        return Objects.equals(nume, brandTelefon.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }
}
