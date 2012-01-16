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


    String govDocsData = "/home/abr/Downloads/000/000";


    @org.junit.Test
    public void testAccuracy() throws Exception {
        TestAccuracy.testAccuracy(govDocsData);


    }
}
