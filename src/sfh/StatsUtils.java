package sfh;

import sfh.merger.Merger;

import java.util.List;

public class StatsUtils {
    public static void merge(List<String> statsList, Merger merger, String filename) {
        for (String s : statsList) {
            merger.parse(s);
        }
        merger.printToMergedFile(filename);
    }
}
