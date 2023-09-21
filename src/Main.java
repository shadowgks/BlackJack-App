import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static public void createCartes(){
        ArrayList<String> cartes = new ArrayList<String>();
        String[] type = {"A","B","C","D"};

        //Set cartes
        for (int i=0; i<4; i++){
            for (int j=1; j<14; j++){
                cartes.add(type[i] +"-"+ String.valueOf(j));
            }
        }

        // Print the contents of cartes
        for (String carte : cartes) {
            System.out.println(carte);
        }
    }

    public static void main(String[] args) {
        createCartes();
    }
}