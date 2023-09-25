import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type show whitespaces,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static public int[][] createCards(int card, int type){
        int max_row = (13 - (card - 1)) + (4 - type) * 13;
        int max_col = 2;

        int[][] cards = new int[max_row][max_col]; // Create a 2D array to store the values
        int index = 0;

        if(card > 13 || card <= 0 || type > 4 || type <= 0){
            System.out.println("Sorry this number cards not exist!");
        }else {
            //Insert numbers in array 2D
            for (int i=type; i<=4; i++){
                for (int j=card; j<=13; j++){
                    cards[index][0] = j;
                    cards[index][1] = i;
                    index++;
                }
                card = 1;
            }
        }

        return cards;
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
        for (Integer integer : random_number) {
            if (integer > 10 && integer < 45) {
                index_piocher = integer;
                break;
            }
        }

        return index_piocher;
    }

    public static void runGame(){
        // Get one random number
        int index_piocher = randomIndexForPiocher();

        // Get array cards piocher
        int[][][] k = piocherCards(index_piocher);

        print3D(k);
        
        for(int i=0; i<index_piocher; i++){
            System.out.println(k[0][i][0] +"-"+ k[0][i][1]);
        }

    }

    public static void main(String[] args) {
        runGame();
    }
}