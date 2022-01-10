package sfh;

import javastraw.reader.Dataset;
import javastraw.tools.HiCFileTools;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class DumpStats {
    public static void main(String[] args) {
        Dataset ds = HiCFileTools.extractDatasetForCLT(args[1], false, false);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(args[0]));
            out.write(ds.getStatistics());
            //System.out.println(ds.getGraphs());
            out.close();
        } catch (Exception e) {
            System.err.println("Unable to print stats: " + e.getLocalizedMessage());
            System.err.println("Usage: java -jar stats-from-hic.jar <inter_stem> <in.hic>");
        }
    }
}
