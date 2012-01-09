package dk.statsbiblioteket.scape.tika;

import dk.statsbiblioteket.scape.govdocs1.GovDocs1;
import dk.statsbiblioteket.scape.govdocs1.GroundTruthBean;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 12/16/11
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleWrapperTest {

    SimpleWrapper sw;

    File testfile1 = new File("/home/abr/Downloads");
    File testfile2 = new File("pom.xml");
    File govDocsData = new File("/home/abr/Downloads/000/000");
    GovDocs1 govDocs1;
    File groundTruthFile = new File("/home/abr/Downloads/groundtruth-fitools/complete.csv");

    @org.junit.Before
    public void setUp() throws Exception {
        sw = new SimpleWrapper();
        govDocs1 = new GovDocs1(govDocsData,groundTruthFile);
    }



    @org.junit.Test
    public void testDetect() throws Exception {

        SimpleWrapper.main(testfile1.getAbsolutePath());


    }

    @org.junit.Test
    public void testAccuracy() throws Exception {
        File[] datafiles = govDocs1.getDatafilesDir().listFiles();

        List<GroundTruthBean> groundTruthList = govDocs1.getGroundTruthBeans();
        Map<String, GroundTruthBean> truths = new HashMap<String, GroundTruthBean>();
        for (GroundTruthBean groundTruthBean : groundTruthList) {
            truths.put(groundTruthBean.getFilename(),groundTruthBean);
        }

        int filesScanned = 0;
        int filesInError = 0;

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
                System.out.println("Detected as: "+detection);
                System.out.println("Groundtruth as "+truth.getMime());
            }

        }
        System.out.println("Files scanned "+filesScanned);
        System.out.println("Files in error "+filesInError);



    }
}
