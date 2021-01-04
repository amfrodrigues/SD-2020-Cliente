import java.rmi.Remote;
import java.rmi.RemoteException;


public interface StorageServiceInterface extends Remote {

    void LoadFiles(String filename) throws RemoteException;

    boolean sendData(String filename, byte[] data, int len) throws RemoteException;
}
