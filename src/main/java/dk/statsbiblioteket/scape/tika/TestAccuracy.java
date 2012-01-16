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
         String inputDir = args[0];
         String outFile = null;
         String govDocsData = null;
         if (args.length == 1) {
             govDocsData = args[1];
         }
         testAccuracy(govDocsData);
     }


    public static void testAccuracy(String govDocsData) throws Exception {

        SimpleWrapper sw = new SimpleWrapper();
        GovDocs1 govDocs1 = new GovDocs1(new File(govDocsData));

        File[] datafiles = govDocs1.getDatafilesDir().listFiles();

        List<GroundTruthBean> groundTruthList = govDocs1.getGroundTruthBeans();
        Map<String, GroundTruthBean> truths = new HashMap<String, GroundTruthBean>();
        for (GroundTruthBean groundTruthBean : groundTruthList) {
            truths.put(groundTruthBean.getFilename(),groundTruthBean);
        }

        int filesScanned = 0;
        int filesInError = 0;
        Report report = new Report();

        for (File file : datafiles) {
            if (!file.isFile()){
                continue;
            }

            String filename = file.getName();
            filename = filename.substring(0,filename.lastIndexOf("."));
            GroundTruthBean truth = truths.get(filename);
            if (truth == null){
                continue;
            }
            String detection = sw.detect(file);
            filesScanned++;
            boolean found = truth.getMimes().contains(detection);
            if (! found){
                filesInError++;
                System.out.println();
                System.out.println(filename);
                report.reportWrong(truth.getMime(), detection);
                System.out.println("Detected as: "+detection);
                System.out.println("Groundtruth as "+truth.getMime());
            } else {
                report.reportRight(truth.getMime());
            }

        }
        System.out.println("Files scanned "+filesScanned);
        System.out.println("Files in error "+filesInError);
        System.out.println(report.toString());


    }
}
