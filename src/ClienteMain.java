
import java.rmi.Naming;
import java.util.LinkedList;

public class ClienteMain {

    private static final String storage_rmi_id = "2022";
    private static final String master_rmi_id = "2023";

    private static final String storage_rmi_address = "rmi://localhost:" + storage_rmi_id + "/storageservice";
    private static final String master_rmi_address = "rmi://localhost:" + master_rmi_id + "/masterservice";

    private static final String filename_to_send = "www_nytimes_com";

    private static final boolean flg_send_files_to_storage = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = (new Thread() {
            public void run() {
                StorageMain.main(new String[]{storage_rmi_id});
                MasterMain.main(new String[]{master_rmi_id});

            }
        });
        t.start();
        Thread.sleep(1000);

        StorageServiceInterface storage_rmi = null;
        try {
            storage_rmi = (StorageServiceInterface) Naming.lookup(storage_rmi_address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ClienteServiceInterface cliente_rmi = new ClienteService("resources");
            if(flg_send_files_to_storage){
                if(setup_send_files_to_storage(cliente_rmi,storage_rmi)){
                    System.out.println("CLIENTE: Ficheiros enviados para o storage com sucesso");
                }else{
                    System.out.println("CLIENTE: Falha a enviar ficheiros para o Storage");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        try {
            storage_rmi.LoadFiles(filename_to_send);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MasterServiceInterface master_rmi = null;
        try{
            master_rmi = (MasterServiceInterface) Naming.lookup(master_rmi_address);
            LinkedList<CombinationProcessingData> combinations =master_rmi.task_combinations(3);
            for(CombinationProcessingData comb : combinations){
                System.out.println("CLIENTE -> Combination :"+comb.combination+" with precentage"+comb.percentage);
            }
            System.out.println("CLIENTE: Combinations Statistics Size = "+storage_rmi.getcombinationsStatisticsize());
        }catch (Exception e){e.printStackTrace();}


    }
    private static boolean setup_send_files_to_storage(ClienteServiceInterface cliente_rmi,StorageServiceInterface storage_rmi){
       try{
           cliente_rmi.sendFile(storage_rmi,filename_to_send);
           for(int i=1;i<77;i++){
               if (cliente_rmi.sendFile(storage_rmi, filename_to_send + "_"+i)) {
                   System.out.println("CLIENTE: "+"File Sent: " + filename_to_send+ "_"+i);
               } else {
                   System.out.println("CLIENTE: "+"File sent failed");
               }
           }
       }catch(Exception e){
           e.printStackTrace();
           return false;
       }
       return true;
    }

}
