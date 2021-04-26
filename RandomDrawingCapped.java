public class RandomDrawingCapped <T> extends RandomDrawing <T> {
    private final int maxEntries;

    public RandomDrawingCapped(boolean allowDuplicates, int maxEntries) {
        super(allowDuplicates);
        this.maxEntries = maxEntries;
    }

    public boolean addEntry (T entry) {
        if (this.size() != maxEntries) {
            return super.addEntry(entry);
        } else {
            return false;
        }
    } 
}
