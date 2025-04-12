import java.util.ArrayList;
import java.util.Scanner;

public class StatesAndCapitals {
    public static void main(String[] args) {
        // Define the states and capitals in an ArrayList
        ArrayList<String[]> statesAndCapitals = new ArrayList<>();

        statesAndCapitals.add(new String[]{"Alabama", "Montgomery"});
        statesAndCapitals.add(new String[]{"Alaska", "Juneau"});
        statesAndCapitals.add(new String[]{"Arizona", "Phoenix"});
        statesAndCapitals.add(new String[]{"Arkansas", "Little Rock"});
        statesAndCapitals.add(new String[]{"California", "Sacramento"});
        statesAndCapitals.add(new String[]{"Colorado", "Denver"});
        statesAndCapitals.add(new String[]{"Connecticut", "Hartford"});
        statesAndCapitals.add(new String[]{"Delaware", "Dover"});
        statesAndCapitals.add(new String[]{"Florida", "Tallahassee"});
        statesAndCapitals.add(new String[]{"Georgia", "Atlanta"});
        statesAndCapitals.add(new String[]{"Hawaii", "Honolulu"});
        statesAndCapitals.add(new String[]{"Idaho", "Boise"});
        statesAndCapitals.add(new String[]{"Illinois", "Springfield"});
        statesAndCapitals.add(new String[]{"Indiana", "Indianapolis"});
        statesAndCapitals.add(new String[]{"Iowa", "Des Moines"});
        statesAndCapitals.add(new String[]{"Kansas", "Topeka"});
        statesAndCapitals.add(new String[]{"Kentucky", "Frankfort"});
        statesAndCapitals.add(new String[]{"Louisiana", "Baton Rouge"});
        statesAndCapitals.add(new String[]{"Maine", "Augusta"});
        statesAndCapitals.add(new String[]{"Maryland", "Annapolis"});
        statesAndCapitals.add(new String[]{"Massachusetts", "Boston"});
        statesAndCapitals.add(new String[]{"Michigan", "Lansing"});
        statesAndCapitals.add(new String[]{"Minnesota", "Saint Paul"});
        statesAndCapitals.add(new String[]{"Mississippi", "Jackson"});
        statesAndCapitals.add(new String[]{"Missouri", "Jefferson City"});
        statesAndCapitals.add(new String[]{"Montana", "Helena"});
        statesAndCapitals.add(new String[]{"Nebraska", "Lincoln"});
        statesAndCapitals.add(new String[]{"Nevada", "Carson City"});
        statesAndCapitals.add(new String[]{"New Hampshire", "Concord"});
        statesAndCapitals.add(new String[]{"New Jersey", "Trenton"});
        statesAndCapitals.add(new String[]{"New Mexico", "Santa Fe"});
        statesAndCapitals.add(new String[]{"New York", "Albany"});
        statesAndCapitals.add(new String[]{"North Carolina", "Raleigh"});
        statesAndCapitals.add(new String[]{"North Dakota", "Bismarck"});
        statesAndCapitals.add(new String[]{"Ohio", "Columbus"});
        statesAndCapitals.add(new String[]{"Oklahoma", "Oklahoma City"});
        statesAndCapitals.add(new String[]{"Oregon", "Salem"});
        statesAndCapitals.add(new String[]{"Pennsylvania", "Harrisburg"});
        statesAndCapitals.add(new String[]{"Rhode Island", "Providence"});
        statesAndCapitals.add(new String[]{"South Carolina", "Columbia"});
        statesAndCapitals.add(new String[]{"South Dakota", "Pierre"});
        statesAndCapitals.add(new String[]{"Tennessee", "Nashville"});
        statesAndCapitals.add(new String[]{"Texas", "Austin"});
        statesAndCapitals.add(new String[]{"Utah", "Salt Lake City"});
        statesAndCapitals.add(new String[]{"Vermont", "Montpelier"});
        statesAndCapitals.add(new String[]{"Virginia", "Richmond"});
        statesAndCapitals.add(new String[]{"Washington", "Olympia"});
        statesAndCapitals.add(new String[]{"West Virginia", "Charleston"});
        statesAndCapitals.add(new String[]{"Wisconsin", "Madison"});
        statesAndCapitals.add(new String[]{"Wyoming", "Cheyenne"});

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int correct = 0;

        while (!statesAndCapitals.isEmpty()) {
            // Randomly select a state and its capital
            int randomIndex = (int) (Math.random() * statesAndCapitals.size());
            String[] stateCapitalPair = statesAndCapitals.get(randomIndex);

            // Ask the user to guess the capital
            System.out.println("What is the capital of " + stateCapitalPair[0] + "?");
            String userAnswer = scanner.nextLine();

            // Check the user's answer
            if (userAnswer.equalsIgnoreCase(stateCapitalPair[1])) {
                System.out.println("Your answer is correct!");
                correct++;
            } else {
                System.out.println("The correct answer should be " + stateCapitalPair[1] + ".");
            }

            // Remove the state and capital from the list
            statesAndCapitals.remove(randomIndex);
        }

        // Display the user's score
        System.out.println("The correct count is " + correct);
        scanner.close();
    }
}