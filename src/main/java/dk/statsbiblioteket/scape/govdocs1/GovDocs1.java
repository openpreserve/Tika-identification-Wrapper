package dk.statsbiblioteket.scape.govdocs1;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 1/4/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class GovDocs1 {


    private File datafilesDir;


    private List<GroundTruthBean> groundTruthBeans;

    public GovDocs1(File datafilesDir) throws IOException, URISyntaxException {
        this.datafilesDir = datafilesDir;
        InputStream truthCSV = Thread.currentThread().getContextClassLoader().getResourceAsStream("complete.csv");
        groundTruthBeans = readGroundTruths(truthCSV);
        truthCSV.close();
    }

    private List<GroundTruthBean> readGroundTruths(InputStream groundTruthCsv) throws IOException {
        CSVReader reader = new CSVReader(new InputStreamReader(groundTruthCsv),'\t');
        ColumnPositionMappingStrategy<GroundTruthBean> strat = new ColumnPositionMappingStrategy<GroundTruthBean>();
        strat.setColumnMapping(new String[]{"accuracy", "mime", "charset", "digest", "extensions", "filename", "id", "kind", "size", "version"});
        strat.setType(GroundTruthBean.class);
        CsvToBean<GroundTruthBean> csv = new CsvToBean<GroundTruthBean>();
        List<GroundTruthBean> truthBeanList = csv.parse(strat, reader);


        return truthBeanList;
    }

    public List<GroundTruthBean> getGroundTruthBeans() {
        return Collections.unmodifiableList(groundTruthBeans);
    }

    public File getDatafilesDir() {
        return datafilesDir;
    }

    public List<File> getDatafiles() {
        return getFiles(datafilesDir);
    }

    private List<File> getFiles(File dir){
        List<File> result = new ArrayList<File>();
        if (!dir.isDirectory()){
            result.add(dir);
            return result;
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            result.addAll(getFiles(file));
        }
        return result;
    }
}
