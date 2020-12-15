package cliente;


import com.reducer.ReducerMain;

public class ClienteMain {
    public static void main(String[] args){
        preSetup();





    }

    private static void preSetup() {
        com.storage.StorageMain.main(new String [2022]);
        com.master.MasterMain.main(new String [2023]);

        com.mapper.MapperMain.main(new String [2024]);

        ReducerMain.main(new String [2025]);
    }
}
