package ServiceInterface;

import Domain.Raspuns;
import Domain.User;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface Services {
    User login(User user, Observer client) throws Exception;
    void logout(User user,Observer client) throws Exception;
    void register(User user) throws Exception;
    void startGame() throws Exception;
    void checkAnswer(Observer o, Raspuns raspuns) throws SQLException, RemoteException;
}
