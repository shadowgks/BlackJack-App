import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type show whitespaces,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static public int[][] createCards(int card, int type) {
        int[][] cards = new int[0][];

        if (card > 13 || card <= 0 || type > 4 || type <= 0) {
            System.out.println("Sorry this number cards not exist!");
        } else {
            int max_row = 14 - (card) + (4 - type) * 13;
            int max_col = 2;

            cards = new int[max_row][max_col]; // Create a 2D array for stock values
            int index = 0;

            //Insert numbers in array 2D
            for (int i = type; i <= 4; i++) {
                for (int j = card; j <= 13; j++) {
                    cards[index][0] = j;
                    cards[index][1] = i;
                    index++;
                }
                card = 1;
            }
        }

        return cards;
    }

    public static int[][] extractCard(int random_index, int next, int[][] new_array_cards_random) {
        // Get list Cards
        int[][] get_cards_list = createCards(1, 1);

        // Copy value get_cards_list to new_array_cards_random
        new_array_cards_random[next] = get_cards_list[random_index];

        return new_array_cards_random;
    }

    public static int[][] mixCards() {
        // Array Numbers Random
        Integer[] random_number = randomNumbers();

        // New Array for initialized cards random
        int[][] new_array_cards_random = new int[random_number.length][2];
        // New Array for copy cards random to array mixed complete
        int[][] array_mixed_complete = new int[random_number.length][2];

        // Loop Random Numbers
        for (int i = 0; i < random_number.length; i++) {
            int number_random = random_number[i];
            array_mixed_complete = extractCard(number_random, i, new_array_cards_random);
        }

        return array_mixed_complete;
    }

    public static int[][][] piocherCards(int index_piocher) {
        // Get Array Mix cards
        int[][] mix_cards = mixCards();

        // Create 2 Arrays For split array "Mix cards"
        int[][] split_1 = new int[index_piocher][2];
        int[][] split_2 = new int[mix_cards.length - index_piocher][2];

        for (int i = 0; i < index_piocher; i++) {
            split_1[i] = mix_cards[i];
        }

        for (int j = 0; j < mix_cards.length - index_piocher; j++) {
            split_2[j] = mix_cards[j + index_piocher];
        }

        // Concat array split1 & split2 2D to array 3D and return
        return new int[][][]{
                split_1,
                split_2
        };
    }

    public static Integer[] randomNumbers() {
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

    public static int randomIndexForPiocher() {
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

    public static void runGame() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String input;

        // Get one random number
        int index_piocher = randomIndexForPiocher();

        // Get array cards piocher
        int[][][] cards_piocher = piocherCards(index_piocher);
        int[][] cards_piocher_split_1 = new int[index_piocher][2];

        // Stock card split 1 in array
        for (int i = 0; i < index_piocher; i++) {
            cards_piocher_split_1[i] = cards_piocher[0][i];
        }

        int card = 2;

        // I create arrays for stock cards each player and dealer
        int[][] card_player = new int[0][0];
        int[][] card_dealer = new int[0][0];

        // Get 2 Cards dealer and player
        int[][][] cards_play_dealer = pullCard(cards_piocher_split_1, card, card_dealer);
        int[][][] cards_play_player = pullCard(cards_play_dealer[1], card, card_player);

        // Create var for get score
        int score_palyer;
        int score_dealer;

        // Score Player
        score_palyer = score(cards_play_player[0], "player");
        score_dealer = score(cards_play_dealer[0], "dealer");

        // Show cards player and dealer
        System.out.println("Cards Dealer: " + Arrays.deepToString(cards_play_dealer[0]) + " Score: " + score_dealer);
        System.out.println("Cards Player: " + Arrays.deepToString(cards_play_player[0]) + " Score: " + score_palyer);

        // Entre input value
        System.out.println("Hit | Stand h/s? ");
        input = myObj.nextLine();

        while (input.equals("h")) {
            // Get current cards
            cards_play_player = hitP_D(cards_play_player, "player");
            score_palyer = score(cards_play_player[0], "player");
            System.out.println("Sum of cards player: " + score_palyer);
            System.out.println(Arrays.deepToString(cards_play_player[0]));

            // Check score player
            if (score_palyer > 21) {
                System.out.println("Player Lost");
                break;
            }

            // Assign cards remaining
            cards_play_dealer[1] = cards_play_player[1];

            // Entre input value
            System.out.println("Hit | Stand h/s? ");
            input = myObj.nextLine();
        }

        while (input.equals("s")) {
            // Get current cards
            if (score_dealer >= 17) {
                if (score_dealer > score_palyer) {
                    System.out.println("Dealer Winner");
                } else {
                    System.out.println("Player Winner");
                }
                break;
            } else {
                cards_play_dealer = hitP_D(cards_play_dealer, "dealer");
                score_dealer = score(cards_play_dealer[0], "dealer");
                System.out.println("Sum of cards dealer: " + score_dealer);
                System.out.println(Arrays.deepToString(cards_play_dealer[0]));
            }

            // Check score player
            if (score_dealer > 21) {
                System.out.println("Dealer Lost");
                break;
            }
        }

        System.out.println(Arrays.deepToString(cards_piocher_split_1));

    }

    public static int[][][] hitP_D(int[][][] cards_play, String condition) {
        int[][] cards_used = cards_play[0];
        if(condition.equals("dealer")){
            cards_play = pullCard(cards_play[1], 1, cards_used);
        }else if (condition.equals("player")){
            cards_play = pullCard(cards_play[1], 1, cards_used);
        }
        return cards_play;
    }

//    public static int[][][] hitPlayer(int[][][] cards_play_player) {
//
//        cards_play_player = pullCard(cards_play_player[1], 1, card_player);
//        return cards_play_player;
//    }

    public static int score(int[][] arr, String condition) {
        int score = 0;
        int check_condition = condition.equals("player") ? 21 : condition.equals("dealer") ? 17 : null;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == 1) {
                if (score + 11 > check_condition) {
                    score += 1;
                } else {
                    score += 11;
                }
            } else if (arr[i][0] == 11 || arr[i][0] == 12 || arr[i][0] == 13) {
                score += 10;
            } else {
                score += arr[i][0];
            }
        }
        return score;
    }


    public static int[][][] pullCard(int[][] piocher_cards, int number, int[][] cards_used) {
        int[][] cards_remaining = new int[piocher_cards.length - number][2];
        int[][] cards_used_plus = new int[cards_used.length + number][2];

//        if(cards_used.length > 0){
//            for (int i = 0; i < cards_used.length; i++) {
//                cards_used_plus[i] = cards_used[i];
//            }
//        }
        // Copy
        System.arraycopy(cards_used, 0, cards_used_plus, 0, cards_used.length);

        int index_card = 0;
        for (int i = 0, k = 0; i < piocher_cards.length; i++) {
            if (index_card < number) {
                cards_used_plus[i + cards_used.length] = piocher_cards[i];
                index_card++;
                continue;
            }

            // The removal element index
            cards_remaining[k++] = piocher_cards[i];
        }

        return new int[][][]{
                cards_used_plus,
                cards_remaining
        };
    }

    public static void main(String[] args) {
        runGame();
    }
}