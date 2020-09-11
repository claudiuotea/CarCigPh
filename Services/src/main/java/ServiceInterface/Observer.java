package ServiceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void clearTextbox() throws RemoteException;

    void receiveLetter(char letter) throws RemoteException;
    void receivePunctaj(int punctaj) throws RemoteException;


    //Used to disable the start buton until a new round is ready
    void disableStart() throws RemoteException;
    //used to enable the start buton to everybody when the new round is ready
    void enableStart() throws  RemoteException;
}
