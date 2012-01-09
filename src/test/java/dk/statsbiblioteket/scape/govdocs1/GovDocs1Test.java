package dk.statsbiblioteket.scape.govdocs1;

import dk.statsbiblioteket.scape.tika.SimpleWrapper;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 1/4/12
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class GovDocs1Test {

       @org.junit.Before
    public void setUp() throws Exception {

       }


    @org.junit.Test
    public void testLoad() throws Exception {
        GovDocs1 govdocs1 = new GovDocs1(null, new File("/home/abr/Downloads/groundtruth-fitools/complete.csv"));

    }
}
