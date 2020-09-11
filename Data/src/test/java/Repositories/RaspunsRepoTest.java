package Repositories;

import Domain.BrandMasina;
import Domain.BrandTelefon;
import Domain.BrandTigari;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class RaspunsRepoTest {

    @Test
    public void checkExistence() throws SQLException {
        hibernateUtils.initialize();
        RaspunsRepo raspunsRepo = new RaspunsRepo();

        BrandTelefon telefon = new BrandTelefon("siEmEns");
        BrandMasina masina = new BrandMasina("audi");
        BrandTigari tigari = new BrandTigari("Marlboro");

        assertTrue(raspunsRepo.checkExistence(tigari,masina,telefon));
        assertFalse(raspunsRepo.checkExistence(new BrandTigari("dada"),new BrandMasina("eaa"),new BrandTelefon("dada")));
    }
}