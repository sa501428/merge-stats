package sfh;

import javastraw.reader.Dataset;
import javastraw.tools.HiCFileTools;
import sfh.merger.GraphsMerger;
import sfh.merger.Merger;
import sfh.merger.PairedAlignmentStatsMerger;
import sfh.merger.SingleAlignmentStatsMerger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MergeStats {
    public static void main(String[] args) {
        if (args.length < 2) {
            printUsageAndExit(4);
        }

        boolean isHIC = args[1].toLowerCase().endsWith(".hic");
        boolean isHistogram = args[1].toLowerCase().endsWith("_hists.m");
        boolean isInter = args[1].toLowerCase().endsWith(".txt") && args[1].toLowerCase().contains("inter");


        List<String> statsList = new ArrayList<>();
        List<String> graphsList = new ArrayList<>();
        if (isHIC) {
            for (int i = 1; i < args.length; i++) {
                Dataset ds = HiCFileTools.extractDatasetForCLT(args[i], false, false, false);
                statsList.add(ds.getStatistics());
                graphsList.add(ds.getGraphs());
            }
        } else if (isHistogram) {
            populateList(graphsList, args);
        } else if (isInter) {
            populateList(statsList, args);
        } else {
            printUsageAndExit(6);
        }

        if (statsList.size() > 0) {
            boolean isSingleAlignment = confirmAllSameAlignment(statsList);
            if (isSingleAlignment) {
                StatsUtils.merge(statsList, new SingleAlignmentStatsMerger(), args[0] + ".txt");
            } else {
                StatsUtils.merge(statsList, new PairedAlignmentStatsMerger(), args[0] + ".txt");
            }
        }
        if (graphsList.size() > 0) {
            StatsUtils.merge(graphsList, new GraphsMerger(), args[0] + "_hists.m");
        }
    }

    private static void populateList(List<String> filesList, String[] args) {
        for (int i = 1; i < args.length; i++) {
            try {
                filesList.add(readFileIntoString(args[i]));
            } catch (Exception e) {
                System.err.println("Unable to parse file: " + args[i]);
                e.printStackTrace();
                System.exit(9);
            }
        }
    }

    private static String readFileIntoString(String filepath) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private static void printUsageAndExit(int code) {
        System.err.println("Usage:\n\tjava -jar merge-stats.jar <stem> <inter.hic> [inter2.hic ...]");
        System.err.println("\tjava -jar merge-stats.jar <stem> <inter.txt> [inter2.txt ...]");
        System.err.println("\tjava -jar merge-stats.jar <stem> <inter_hists.m> [inter2_hists.m ...]");
        System.err.println("\tstem: \"inter\" if inputs are mapq1 or \"inter_30\" if inputs are mapq30");
        System.exit(code);
    }

    private static boolean confirmAllSameAlignment(List<String> statsList) {
        boolean hasSingleAlignment = false;
        boolean hasPairedAlignment = false;
        for (String s : statsList) {
            hasSingleAlignment = hasSingleAlignment | Merger.containsIgnoreCase(s, "Read type: Single End");
            hasPairedAlignment = hasPairedAlignment | Merger.containsIgnoreCase(s, "Read type: Paired End");
        }
        if (hasSingleAlignment && hasPairedAlignment) {
            System.err.println("Cannot mix single-end and paired-end files together");
            System.exit(9);
        }
        return hasSingleAlignment;
    }
}
