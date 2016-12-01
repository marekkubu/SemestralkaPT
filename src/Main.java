import java.io.*;

import static javafx.application.Application.launch;

/**
 *
 * @author Thomas
 */
public class Main {

    /**
     * Metoda Main je spouštěcí metoda, která volá metodu launch 
     * a ta zapříčiní spuštění uživatelského rozhraní
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // write your code here
        launch(UserInterface.class, args);
    }

}
