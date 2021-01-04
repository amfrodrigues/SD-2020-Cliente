import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteServiceInterface extends Remote {

    boolean sendFile(StorageServiceInterface storageInterface, String fileName) throws RemoteException;
}
