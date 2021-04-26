/*
 * @author Brent Michael Gannetta WA0000487
 * CS 211S: M9 Generics
 * RandomDrawing.java
 */

import java.util.ArrayList;
import java.util.List;

public class RandomDrawing <T> implements RandomDrawingInterface <T> {
    private final List<T> entriesList;
    private int numEntries;
    private final boolean allowDuplicates;

    /**
     * RandomDrawing constructor
     * @param allowDuplicates boolean sets whether to allow duplicates, sets list type
     */
    public RandomDrawing(boolean allowDuplicates) {
        this.allowDuplicates = allowDuplicates;
        this.entriesList = new ArrayList<>();
        this.numEntries = 0;
    }

    @Override
    public boolean addEntry (T entry) {
        if (!allowDuplicates && entriesList.size() > 0) {
            // drawing does not allow duplicate entries, check for entry in list before adding
            for (T e : entriesList) {
                if (e.equals(entry)) {
                    // found entry in entriesList, return false and exit
                    return false;
                }
            }
        } // no logic needed due to return in for each loop above
        entriesList.add(entry);
        numEntries++;
        return true;
    }


    @Override
    public T selectWinner(boolean removeWinner) {
        if (numEntries == 0) {
            // no entries = no winner. return null
            return null;
        } else {
            int winnerIndex = (int) (Math.random() * numEntries);
            T winner = entriesList.get(winnerIndex);
            if (removeWinner) {
                entriesList.remove(winnerIndex);
                numEntries--;
            }
            return winner;
        }
    }

    @Override
    public int size() {
        return numEntries;
    }

    @Override
    public void displayEntries() {
        if (numEntries > 0) {
            for (T e : entriesList) {
                System.out.println(e);
            }
        }
    }
}
