# merge-stats

Merging statistics for v9 .hic files

## Usage:

```aidl 
java -jar merge-stats.jar <stem> <inter.hic> [inter2.hic ...]
java -jar merge-stats.jar <stem> <inter.txt> [inter2.txt ...]
java -jar merge-stats.jar <stem> <inter_hists.m> [inter2_hists.m ...]
```

The required arguments are:

* `<stem>` should be `inter` (or `inter_30`) if inputs are mapq1 (or mapq30)
* a list of file paths (minimum 1) for either .hic, .txt, or .m files that are to be merged.
