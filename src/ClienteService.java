import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteService extends UnicastRemoteObject implements ClienteServiceInterface {
    private String path_to_file = ""; // path to the file

    public ClienteService(String path_to_file) throws RemoteException {
        setPath_to_file(path_to_file);
    }

    public void setPath_to_file(String path_to_file) {
        this.path_to_file = ".\\" + path_to_file + "\\";
    }

    //interface function
    @Override
    public boolean sendFile(StorageServiceInterface storageInterface, String fileName) throws RemoteException {
        Boolean data_send = false;
        try {

            File f1 = new File(path_to_file + fileName+".har");
            FileInputStream in = new FileInputStream(f1);
            byte[] mydata = new byte[1024 * 1024];
            int mylen = in.read(mydata);
            while (mylen > 0) {
                data_send = storageInterface.sendData(fileName, mydata, mylen);
                mylen = in.read(mydata);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return data_send;
    }

}
