package sfh;

import javastraw.reader.Dataset;
import javastraw.tools.HiCFileTools;

public class DumpStats {
    public static void main(String[] args) {
        Dataset ds = HiCFileTools.extractDatasetForCLT(args[0], false, false);
        System.out.println(ds.getStatistics());
    }
}
