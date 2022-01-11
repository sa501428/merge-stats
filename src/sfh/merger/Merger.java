package sfh.merger;

public abstract class Merger {
    public static boolean containsIgnoreCase(String main, String word) {
        return main.toLowerCase().contains(word.toLowerCase());
    }

    public abstract void parse(String s);

    public abstract void printToMergedFile(String filename);
}
