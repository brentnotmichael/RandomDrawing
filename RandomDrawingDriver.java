import java.util.*;

public class RandomDrawingDriver {

	public static final boolean REMOVE_WINNER = true;
	public static final boolean DO_NOT_REMOVE_WINNER = !REMOVE_WINNER;
	public static final boolean ALLOW_DUPLICATES = true;
	public static final boolean NO_DUPLICATES = !ALLOW_DUPLICATES;
	
	public static void main(String[] args) {
		
		System.out.println("\n**************************TESTING GENERIC CLASS**************************");
		RandomDrawing<String> stringDrawingWithDuplicates = new RandomDrawing<String>(ALLOW_DUPLICATES);
		fillRandomStringBox(stringDrawingWithDuplicates);
		testRandomDrawing(stringDrawingWithDuplicates, "String drawing with duplicates allowed");

		RandomDrawing<String> stringDrawingWithNoDuplicates = new RandomDrawing<String>(NO_DUPLICATES);
		fillRandomStringBox(stringDrawingWithNoDuplicates);
		testRandomDrawing(stringDrawingWithNoDuplicates, "String drawing with NO duplicates allowed");

		RandomDrawing<Integer> numberDrawingWithDuplicates = new RandomDrawing<Integer>(ALLOW_DUPLICATES);
		fillRandomIntegerBox(numberDrawingWithDuplicates, 100);
		testRandomDrawing(numberDrawingWithDuplicates, "Integer drawing with duplicates allowed");
		
		RandomDrawing<String> emptyStringDrawing = new RandomDrawing<String>(ALLOW_DUPLICATES);
		testRandomDrawing(emptyStringDrawing, "empty RandomDrawing");
        // IMPORTANT NOTE: Depending on your solution, your program might end here.
        // If it does, you should then comment out this test above so you can test the next section. 


		// SUGGESTION: CREATE A RANDOMBOX THAT HOLDS SOME OTHER TYPE- ANY CLASS YOU'VE GOT!
		// FILL THE BOX AND PASS IT TO THE testRandomDrawing METHOD!
		
		// UNCOMMENT WHEN YOU WRITE YOUR STATIC GENERIC METHOD
		// ALSO UNCOMMENT THE ASSOCIATED TEST METHOD AT THE END OF THIS FILE
		
		System.out.println("\n**************************TESTING GENERIC METHOD**************************");
		stringDrawingWithDuplicates = new RandomDrawing<String>(ALLOW_DUPLICATES);
		fillRandomStringBox(stringDrawingWithDuplicates);
		testUniqueWinnersMethod(stringDrawingWithDuplicates, 5, "String drawing with duplicates");
		
		numberDrawingWithDuplicates = new RandomDrawing<Integer>(ALLOW_DUPLICATES);
		fillRandomIntegerBox(numberDrawingWithDuplicates, 100);
		testUniqueWinnersMethod(numberDrawingWithDuplicates, 3, "Integer drawing with duplicates");

		RandomDrawing<String> smallDrawing = new RandomDrawing<String>(ALLOW_DUPLICATES);
        smallDrawing.addEntry("Entry1");
        smallDrawing.addEntry("Entry2");
        smallDrawing.addEntry("Entry3");
        testUniqueWinnersMethod(smallDrawing, 3, "Small drawing- just enough entries as requested");

		smallDrawing = new RandomDrawing<String>(ALLOW_DUPLICATES);
		smallDrawing.addEntry("Entry1");
        smallDrawing.addEntry("Entry2");
        smallDrawing.addEntry("Entry3");
		Random generator = new Random();
		for(int i=0; i<27; i++) {
			int entryNumber = generator.nextInt(3)+1;
			smallDrawing.addEntry("Entry"+entryNumber);
		}
		testUniqueWinnersMethod(smallDrawing, 3, "Small drawing- just enough *unique* entries as requested, with many duplicates");
		
		System.out.println("\nNot Enough Entries Drawing: Code should take some logical action but should NOT return a list with duplicate winners or enter an infinite loop.");
		RandomDrawing<String> insufficientEntriesDrawing = new RandomDrawing<String>(ALLOW_DUPLICATES);
		insufficientEntriesDrawing.addEntry("Entry1");
		insufficientEntriesDrawing.addEntry("Entry2");
        insufficientEntriesDrawing.addEntry("Entry3");
		List<String> insufficientWinners = selectMultipleUniqueWinners(insufficientEntriesDrawing, 4);
		System.out.println("Winners:\n" + insufficientWinners);
        // IMPORTANT NOTE: Depending on your solution, your program might end here.
        // If it does, you should then comment out this test above so you can test the next section. 

		System.out.println("\nEmpty Drawing: Code should take some logical action but should NOT enter an infinite loop.");
		RandomDrawing<Integer> emptyDrawing = new RandomDrawing<Integer>(ALLOW_DUPLICATES);
		List<Integer> emptyWinners = selectMultipleUniqueWinners(emptyDrawing, 3);
		System.out.println("Winners:\n" + emptyWinners);
	    // IMPORTANT NOTE: Depending on your solution, your program might end here.
		// If it does, and you completed the extra credit, you should 
		// comment out this test above so you can test the next section. 
		System.out.println("\nRegular credit testing complete!");
		
		
		// UNCOMMENT TO TEST YOUR EXTRA CREDIT

		System.out.println("\n**************************TESTING EXTRA CREDIT**************************");

		System.out.println("\nSmall Capped Contest Drawing with Repeats:");
		RandomDrawingInterface<Integer> smallCappedDrawing = new RandomDrawingCapped<Integer>(ALLOW_DUPLICATES, 10);
		generator = new Random();
		boolean allValuesAdded = true;
		for(int i=0; i<10; i++) {
			int entryNumber = generator.nextInt(10);
			boolean valueAdded = smallCappedDrawing.addEntry(entryNumber);
			if(!valueAdded) {
				allValuesAdded = false;
			}
		}
		System.out.println("Adding 10 random (possibly duplicate) values to a drawing capped at 10. All values added?");
		System.out.println("Expected = true");
		System.out.println("  Actual = " + allValuesAdded);
		if(!allValuesAdded) {
			System.out.println("   *****Test Failed: 10 values should have been added.");
		}
		testRandomDrawing(smallCappedDrawing, "Capped RandomDrawing at capacity");
		
		allValuesAdded = true;
		for(int i=0; i<10; i++) {
			int entryNumber = generator.nextInt(10);
			boolean valueAdded = smallCappedDrawing.addEntry(entryNumber);
			if(!valueAdded) {
				allValuesAdded = false;
			}
		}
		System.out.println("\nTrying to add 10 more random (possibly duplicate) values to a drawing capped at 10. All values added?");
		System.out.println("Expected = false");
		System.out.println("  Actual = " + allValuesAdded);
		if(allValuesAdded) {
			System.out.println("   *****Test Failed: more than 10 values allowed in the drawing.");
		}
		testRandomDrawing(smallCappedDrawing, "Capped RandomDrawing at capacity");


		System.out.println("\nLarge Capped Contest Drawing without Repeats:");
		RandomDrawingInterface<Integer> largeCappedDrawing = new RandomDrawingCapped<Integer>(NO_DUPLICATES, 100);
		fillRandomIntegerBox(largeCappedDrawing, 500);
		if(largeCappedDrawing.size()!=100) {
			System.out.println("   *****Test Failed: size should be capped at 100.");
		}
		testRandomDrawing(largeCappedDrawing, "Large capped RandomDrawing at capacity");

	}
	

	// YOUR GENERIC METHOD HERE
	public static <T> List<T> selectMultipleUniqueWinners(RandomDrawingInterface<T> drawingList, int numWinners) {
		List<T> winnersList = new ArrayList<>();
		if (drawingList.size() == 0) {
			// no entries = no winners, return null
			return null;
		} else {
			// pick winners at random
			while (winnersList.size() < numWinners) {
				// pick a temporary winner at random, removing them from the drawing
				T tempWinner = drawingList.selectWinner(true);
				if (!winnersList.contains(tempWinner)) {
					// tempWinner isn't in the winnersList so add them and move on
					winnersList.add(tempWinner);
				} else {
					// tempWinner was in the winnersList (duplicate) so add them back to the drawingList and try again
					drawingList.addEntry(tempWinner);
				}
			}
		return winnersList;
		}
	}

	
	public static void fillRandomStringBox(RandomDrawingInterface<String> wordBox) {
		wordBox.addEntry("Adam Zapel"); wordBox.addEntry("Al Dente"); wordBox.addEntry("Al Fresco"); wordBox.addEntry("Al K. Seltzer"); wordBox.addEntry("Alf A. Romeo"); wordBox.addEntry("Amanda Lynn"); wordBox.addEntry("Anita Job"); wordBox.addEntry("Anna Conda"); wordBox.addEntry("Anna Graham"); wordBox.addEntry("Anna Prentice "); wordBox.addEntry("Anna Sasin"); wordBox.addEntry("Anne Teak"); wordBox.addEntry("B.A. Ware"); wordBox.addEntry("Barb Dwyer"); wordBox.addEntry("Barb E. Dahl"); wordBox.addEntry("Barbara Seville"); wordBox.addEntry("Barry Cade"); wordBox.addEntry("Bea Minor"); wordBox.addEntry("Dee Major"); wordBox.addEntry("Beau Tye"); wordBox.addEntry("Bill Board"); wordBox.addEntry("Cara Van"); wordBox.addEntry("Chris P. Bacon"); wordBox.addEntry("Constance Noring"); wordBox.addEntry("Crystal Ball"); wordBox.addEntry("Crystal Glass"); wordBox.addEntry("Earl Lee Riser"); wordBox.addEntry("Easton West "); wordBox.addEntry("Ferris Wheeler"); wordBox.addEntry("Flint Sparks"); wordBox.addEntry("Franklin Stein "); wordBox.addEntry("Gene Poole"); wordBox.addEntry("Heidi Clare"); wordBox.addEntry("Helen Back"); wordBox.addEntry("Helen Wiells"); wordBox.addEntry("Holly McRell"); wordBox.addEntry("Holly Wood"); wordBox.addEntry("Jack Pott"); wordBox.addEntry("Joe Kerr"); wordBox.addEntry("Joy Rider"); wordBox.addEntry("Justin Case"); wordBox.addEntry("Justin Time"); wordBox.addEntry("Kandi Apple"); wordBox.addEntry("Laura Norder"); wordBox.addEntry("Leigh King "); wordBox.addEntry("Luke Warm"); wordBox.addEntry("Marsha Mellow"); wordBox.addEntry("Marshall Law"); wordBox.addEntry("Marty Graw"); wordBox.addEntry("Olive Branch"); wordBox.addEntry("Paige Turner"); wordBox.addEntry("Pepe Roni"); wordBox.addEntry("Price Wright"); wordBox.addEntry("Rocky Beach"); wordBox.addEntry("Sandy Beach"); wordBox.addEntry("Sal A. Mander"); wordBox.addEntry("Stanley Cupp"); wordBox.addEntry("Tom Morrow"); wordBox.addEntry("Warren Peace"); wordBox.addEntry("Will Power"); wordBox.addEntry("X. Benedict");
		wordBox.addEntry("Pete Repeaty"); wordBox.addEntry("Pete Repeaty"); wordBox.addEntry("Pete Repeaty"); wordBox.addEntry("Pete Repeaty");
	}
	
	public static void fillRandomIntegerBox(RandomDrawingInterface<Integer> numbersBox, int numberOfNumbers) {
		Random generator = new Random();
		int maxNumber = numberOfNumbers * 10;
		for(int i=0; i<numberOfNumbers; i++) {
			numbersBox.addEntry(generator.nextInt(maxNumber));
		}
	}
	
	
	/*
	 * The methods below are designed to help support the tests cases run from main. You don't
	 * need to use, modify, or understand these methods. You can safely ignore them. :) 
	 */
	public static <T> void testRandomDrawing(RandomDrawingInterface<T> randomDrawing, String testDescription) {
		System.out.println("\n*****Testing random drawing: " + testDescription);
		System.out.print("Entries: ");
		randomDrawing.displayEntries();
		int beforeSize = randomDrawing.size();
		int expectedSizeAfterRemove = beforeSize-1;
		if(expectedSizeAfterRemove<0) {
			expectedSizeAfterRemove = 0;
		}
		System.out.println("Before draw, size is " + beforeSize);
		T winner = randomDrawing.selectWinner(REMOVE_WINNER);
		System.out.println("Drawing, with removal. Winner = " + winner);			
		System.out.println("   Expected size after draw = " + expectedSizeAfterRemove);
		System.out.println("     Actual size after draw = " + randomDrawing.size());
		if(randomDrawing.size()!=expectedSizeAfterRemove) {
			System.out.println("   *****Test Failed: incorrect drawing size after removal of winner.");
		}
		winner = randomDrawing.selectWinner(DO_NOT_REMOVE_WINNER);
		System.out.println("Drawing, without removal. Winner = " + winner);			
		System.out.println("   Expected size after draw = " + expectedSizeAfterRemove);
		System.out.println("     Actual size after draw = " + randomDrawing.size());
		if(randomDrawing.size()!=expectedSizeAfterRemove) {
			System.out.println("   *****Test Failed: incorrect drawing size after drawing of winner without removal.");
		}
	}
	// UN-COMMENT THIS PART OUT WHEN YOU ARE READY TO TEST YOUR GENERIC METHOD
	
	public static <T extends Comparable<? super T>> void testUniqueWinnersMethod(RandomDrawing<T> randomDrawing, int numWinners, String testDescription) {
		System.out.println("\n*****Testing static unique winners method: " + testDescription);
		int expectedSize = randomDrawing.size() - numWinners;
		List<T> winners = selectMultipleUniqueWinners(randomDrawing, numWinners);
		Collections.sort(winners); // for display purposes only
		System.out.println("Expected size after draw = " + expectedSize);
		System.out.println("  Actual size after draw = " + randomDrawing.size());
		if(expectedSize!=randomDrawing.size()) {			
			System.out.println("   *****Test Failed: the winners were not removed from the RandomDrawing object.");
		}
		System.out.println("List of winners should contain " + numWinners + " unique winners: ");
		System.out.println(winners);
		if(winners.stream().distinct().count()!=numWinners) {
			System.out.println("   *****Test Failed: not all winners are unique.");
		}		
	}
	
}
