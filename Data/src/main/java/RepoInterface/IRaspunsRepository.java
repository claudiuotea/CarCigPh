package RepoInterface;

import Domain.BrandTelefon;
import Domain.BrandMasina;
import Domain.BrandTigari;

import java.sql.SQLException;

public interface IRaspunsRepository {
    boolean checkExistenceTigari(BrandTigari brandTigari);
    boolean checkExistenceMasina(BrandMasina brandMasina);
    boolean checkExistenceTelefon(BrandTelefon brandTelefon);
}
