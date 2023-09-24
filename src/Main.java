import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type show whitespaces,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static public int[][] createCards(int cart, int type){
        int max_row = (13 - (cart - 1)) + (4 - type) * 13;
        int max_col = 2;

        int[][] cartes = new int[max_row][max_col]; // Create a 2D array to store the values
        int index = 0;

        if(cart > 13 || cart <= 0 || type > 4 || type <= 0){
            System.out.println("Sorry this number cardes not exist!");
        }else {
            //Insert numbers in array 2D
            for (int i=type; i<=4; i++){
                for (int j=cart; j<=13; j++){
                    cartes[index][0] = j;
                    cartes[index][1] = i;
                    index++;
                }
                cart = 1;
            }
        }

        return cartes;
    }

    public static int[][] extractCard(int random_index, int next, int[][]new_array_cards_random){
        int[][] get_cards_list = createCards(1,1);

        new_array_cards_random[next][0] = get_cards_list[random_index][0];
        new_array_cards_random[next][1] = get_cards_list[random_index][1];

        return new_array_cards_random;
    }

    public static int[][] mixCards(){
        // Array Numbers Random
        Integer[] random_number = randomNumbers();

        // New Array for initialized cards random
        int[][] new_array_cards_random = new int[random_number.length][2];
        // New Array for copy cards random to array mixed complete
        int[][] array_mixed_complete = new int[random_number.length][2];

        // Loop Random Numbers
        for (int i=0; i<random_number.length; i++) {
            int number = random_number[i];
            array_mixed_complete = extractCard(number, i, new_array_cards_random);
        }

        return array_mixed_complete;
    }

    public static int[][][] piocherCards(int index_piocher){
        // Get Array Mix cards
        int[][] mix_cards = mixCards();

        // Create 2 Arrays For split array "Mix cards"
        int[][] split1 = new int[index_piocher][2];
        int[][] split2 = new int[mix_cards.length-index_piocher][2];

        for (int i=0; i<index_piocher; i++){
            split1[i][0] = mix_cards[i][0];
            split1[i][1] = mix_cards[i][1];
        }

        for(int j=0; j<mix_cards.length - index_piocher; j++){
            split2[j][0] = mix_cards[j + index_piocher][0];
            split2[j][1] = mix_cards[j + index_piocher][1];
        }

        // Concat array split1 & split2 2D to array 3D and return
        return new int[][][]{
                split1,
                split2
        };
    }

    public static Integer[] randomNumbers(){
        int min = 0;
        int max = 51;
        int numberOfRandomNumbers = 52; // You can change this to the number of unique numbers you need

        Set<Integer> generatedNumbers = new HashSet<>();
        Random random = new Random();

        while (generatedNumbers.size() < numberOfRandomNumbers) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            generatedNumbers.add(randomNumber);
        }

        // Convert the set to an array if needed
        Integer[] uniqueNumbers = generatedNumbers.toArray(new Integer[0]);

        // Shuffle the array if you want the numbers to be in a random order
        Collections.shuffle(Arrays.asList(uniqueNumbers));

        return uniqueNumbers;
    }

    public static void print3D(int[][][] cards)
    {
        System.out.println(Arrays.deepToString(cards));
    }
    public static void print2D(int[][] cards)
    {
        System.out.println(Arrays.deepToString(cards));
    }

    public static int randomIndexForPiocher(){
        Integer[] random_number = randomNumbers();

        int index_piocher = 0;
        for (int i=0; i<random_number.length; i++){
            if (random_number[i] > 10 && random_number[i] < 45){
                index_piocher = random_number[i];
                break;
            }
        }

        return index_piocher;
    }
    public static void main(String[] args) {
        int index_piocher = randomIndexForPiocher();

        int[][][] k = piocherCards(index_piocher);
        print3D(k);
    }
}