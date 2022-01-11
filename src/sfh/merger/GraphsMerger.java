package sfh.merger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GraphsMerger extends Merger {

    private final long[] A = new long[2000];
    private final long[][] B = new long[3][200];
    private final long[][] D = new long[4][100];
    private final long[] x = new long[100];

    @Override
    public void parse(String s) {

        Scanner scanner = new Scanner(s);
        try {
            skipUntilNextArray(scanner);
            addTo1DArray(A, scanner);

            skipUntilNextArray(scanner);
            addTo2DArray(B, scanner);

            skipUntilNextArray(scanner);
            addTo2DArray(D, scanner);

            skipUntilNextArray(scanner);
            for (int idx = 0; idx < x.length; idx++) {
                long newX = scanner.nextLong();
                if (x[idx] > 0L) {
                    if (x[idx] != newX) {
                        System.err.println("X mismatch error? " + x[idx] + " - " + newX);
                    }
                }
                x[idx] = newX;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printToMergedFile(String filename) {
        try {
            BufferedWriter histWriter = new BufferedWriter(new FileWriter(filename));
            histWriter.write("A = [\n");
            write1DArray(A, histWriter);
            histWriter.write("\n];\n");

            histWriter.write("B = [\n");
            write2DArray(B, histWriter);
            histWriter.write("\n];\n");

            histWriter.write("D = [\n");
            write2DArray(D, histWriter);
            histWriter.write("\n];");

            histWriter.write("x = [\n");
            write1DArray(x, histWriter);
            histWriter.write("\n];\n");
            histWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void addTo1DArray(long[] arr, Scanner scanner) {
        for (int idx = 0; idx < arr.length; idx++) {
            arr[idx] += scanner.nextLong();
        }
    }

    private void write1DArray(long[] arr, BufferedWriter histWriter) throws IOException {
        for (long tmp : arr) {
            histWriter.write(tmp + " ");
        }
    }

    private void addTo2DArray(long[][] arr, Scanner scanner) {
        int numRows = arr.length;
        int len = arr[0].length;
        for (int idx = 0; idx < len; idx++) {
            for (int r = 0; r < numRows; r++) {
                try {
                    arr[r][idx] += scanner.nextLong();
                } catch (Exception e) {
                    System.err.println(scanner.next());
                    e.printStackTrace();
                }
            }
        }
    }

    private void write2DArray(long[][] arr, BufferedWriter histWriter) throws IOException {
        int numRows = arr.length;
        int len = arr[0].length;
        for (int idx = 0; idx < len; idx++) {
            String s = "" + arr[0][idx];
            for (int r = 1; r < numRows; r++) {
                s += " " + arr[r][idx];
            }
            histWriter.write(s + "\n");
        }
    }

    private void skipUntilNextArray(Scanner scanner) {
        while (!scanner.next().equals("[")) ;
    }
}
