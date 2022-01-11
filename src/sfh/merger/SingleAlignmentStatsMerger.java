package sfh.merger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SingleAlignmentStatsMerger extends StatsMerger {
    @Override
    public void printToMergedFile(String filename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write("Read type: Single End\n");
            write(out, "Sequenced Reads: ", STATS_LABEL.TOTAL_SEQ, -1, -1);
            long denom1 = getStatistic(STATS_LABEL.TOTAL_SEQ);
            write(out, "No chimera found: ", STATS_LABEL.NO_CHIMERA, denom1, -1);
            write(out, "0 alignments: ", STATS_LABEL.UNMAPPED, denom1, -1);
            write(out, "1 alignment: ", STATS_LABEL.ONE_ALIGN, denom1, -1);
            long denom1A = getStatistic(STATS_LABEL.ONE_ALIGN);
            write(out, "\t1 alignment unique: ", STATS_LABEL.ONE_UNIQUE, denom1, denom1A);
            write(out, "\t1 alignment duplicates: ", STATS_LABEL.ONE_DUPS, denom1, denom1A);
            write(out, "2 alignments: ", STATS_LABEL.TWO_ALIGN, denom1, -1);
            long denom2A = getStatistic(STATS_LABEL.TWO_ALIGN);
            write(out, "\t2 alignment unique: ", STATS_LABEL.TWO_UNIQUE, denom1, denom2A);
            write(out, "\t2 alignment duplicates: ", STATS_LABEL.TWO_DUPS, denom1, denom2A);
            write(out, "3 or more alignments: ", STATS_LABEL.THREE_PLUS, denom1, -1);
            write(out, "Ligation Motif Present: ", STATS_LABEL.LIGATION_MOTIF, denom1, -1);
            long denomAlignable = denom1A + denom2A;
            long denom2AUniq = getStatistic(STATS_LABEL.TWO_UNIQUE);
            write(out, "Total Unique: ", STATS_LABEL.TOTAL_UNIQUE, denom1, denomAlignable);
            write(out, "Total Duplicates: ", STATS_LABEL.TOTAL_DUPS, denom1, denomAlignable);
            write(out, "Below MAPQ Threshold: ", STATS_LABEL.BELOW_MAPQ, denom1, denom2AUniq);
            write(out, "Hi-C Contacts: ", STATS_LABEL.HIC_CONTACTS, denom1, denom2AUniq);
            write(out, "\t3' Bias (Long Range): ", STATS_LABEL.THREE_BIAS, denom1, denom2AUniq);
            if (getStatistic(STATS_LABEL.PAIR_PERCENTS) == 25L) {
                out.write("\tPair Type %(L-I-O-R): 25% - 25% - 25% - 25%\n");
                write(out, "\tL-I-O-R Convergence: ", STATS_LABEL.CONVERGENCE, -1, -1);
            }
            write(out, "Inter-chromosomal: ", STATS_LABEL.INTER, denom1, denom2AUniq);
            write(out, "Intra-chromosomal: ", STATS_LABEL.INTRA, denom1, denom2AUniq);
            out.write("Short Range (<20Kb):\n");
            write(out, "\t<500BP: ", STATS_LABEL.FIVE_HUNDRED_BP, denom1, denom2AUniq);
            write(out, "\t500BP-5kB: ", STATS_LABEL.FIVE_KB, denom1, denom2AUniq);
            write(out, "\t5kB-20kB: ", STATS_LABEL.TWENTY_KB, denom1, denom2AUniq);
            write(out, "Long Range (>20Kb): ", STATS_LABEL.LONG_RANGE, denom1, denom2AUniq);
            out.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    protected STATS_LABEL parseLabel(String s) {
        if (containsIgnoreCase(s, "Sequenced Reads:")) return STATS_LABEL.TOTAL_SEQ;
        if (containsIgnoreCase(s, "0 alignments:")) return STATS_LABEL.UNMAPPED;
        if (containsIgnoreCase(s, "1 alignment:")) return STATS_LABEL.ONE_ALIGN;
        if (containsIgnoreCase(s, "2 alignments:")) return STATS_LABEL.TWO_ALIGN;
        if (containsIgnoreCase(s, "1 alignment unique")) return STATS_LABEL.ONE_UNIQUE;
        if (containsIgnoreCase(s, "1 alignment duplicates")) return STATS_LABEL.ONE_DUPS;
        if (containsIgnoreCase(s, "2 alignment unique")) return STATS_LABEL.TWO_UNIQUE;
        if (containsIgnoreCase(s, "2 alignment duplicates")) return STATS_LABEL.TWO_DUPS;
        return super.parseLabel(s);
    }
}
