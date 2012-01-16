package dk.statsbiblioteket.scape.tika;

import dk.statsbiblioteket.scape.govdocs1.GovDocs1;
import dk.statsbiblioteket.scape.govdocs1.GroundTruthBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 1/16/12
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestAccuracy {

    public static void main(String... args) throws Exception {
        String govDocsData = null;
        if (args.length == 1) {
            govDocsData = args[0];
        }
        testAccuracy(govDocsData);
    }


    public static void testAccuracy(String govDocsData) throws Exception {
        Report report = new Report();
        int filesScanned = 0;
        int filesInError = 0;

        try {
            SimpleWrapper sw = new SimpleWrapper();
            GovDocs1 govDocs1 = new GovDocs1(new File(govDocsData));

            List<File> datafiles = govDocs1.getDatafiles();



            for (File file : datafiles) {
                if (!file.isFile()){
                    continue;
                }

                String filename = file.getName();
                int pos = filename.lastIndexOf(".");
                if (pos < 0){
                    continue;
                }
                filename = filename.substring(0,pos).intern();
                GroundTruthBean truth = govDocs1.getGroundTruthBean(filename);
                if (truth == null){
                    continue;
                }

                Identity detection = sw.detect(file);

                filesScanned++;
                boolean found = truth.isOfMime(detection.getMime());
                report.reportTime(detection.getTime());
                if (! found){
                    filesInError++;
                    report.reportWrong(truth.getMime(), detection.getMime());

                    System.out.println();
                    System.out.println(filename);
                    System.out.println("Detected as: "+detection.getMime());
                    System.out.println("Groundtruth as "+truth.getMime());
                } else {
                    report.reportRight(truth.getMime());
                }

            }
        } finally {
            System.out.println("Files scanned "+filesScanned);
            System.out.println("Files in error "+filesInError);
            System.out.println(report.toString());

        }


    }
}
