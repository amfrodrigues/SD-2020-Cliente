
import java.rmi.Naming;

public class ClienteMain {
    private static final String storage_rmi_id = "2022";
    private static final int master_rmi_id = 2023;
    private static final int mapper1_rmi_id = 2024;
    private static final int mapper2_rmi_id = 2025;
    private static final int reducer1_rmi_id = 2026;
    private static final int reducer2rmi_id = 2027;


    private static final String storage_rmi_address = "rmi://localhost:"+storage_rmi_id+"/storageservice";

    private static final String filename_to_send = "www_nytimes_com_1.har";


    public static void main(String[] args) throws InterruptedException {
        Thread t = (new Thread(){
            public void run(){
                StorageMain.main(new String[]{storage_rmi_id});

               /* MasterMain.main(new String[master_rmi_id]);
                MapperMain.main(new String[mapper1_rmi_id]);
                MapperMain.main(new String[mapper2_rmi_id]);
                ReducerMain.main(new String [reducer1_rmi_id]);
                ReducerMain.main(new String [reducer2rmi_id]);*/
            }
        });
        t.start();
        Thread.sleep(1000);

        StorageServiceInterface storage_rmi = null;
        try{
            storage_rmi = (StorageServiceInterface) Naming.lookup(storage_rmi_address);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            ClienteServiceInterface cliente_rmi = new ClienteService("resources");
            cliente_rmi.sendFile(storage_rmi,filename_to_send);
            System.out.println("File Sent: "+filename_to_send);
        }catch(Exception e){
            e.printStackTrace();
        }







    }

}
