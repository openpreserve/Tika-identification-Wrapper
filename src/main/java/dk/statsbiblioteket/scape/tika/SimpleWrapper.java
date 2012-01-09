package dk.statsbiblioteket.scape.tika;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 12/16/11
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleWrapper {


    private DefaultDetector detector;

    public SimpleWrapper() {
        detector = new DefaultDetector();
    }


    public String detect(File input) throws IOException {

        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY,input.getAbsolutePath());
        byte[] header = new byte[8*1024];
        InputStream inputstream = new FileInputStream(input);
        int read = inputstream.read(header);
        inputstream.close();
        if (read > 0){
            TikaInputStream stream = TikaInputStream.get(header);
            return detector.detect(stream, metadata).toString();
        }

        return null;
    }

    public List<Identity> detect(List<File> files){
        List<Identity> result = new ArrayList<Identity>();
        for (File file : files) {
            long before = System.currentTimeMillis();
            try {
                String mime = detect(file);
                long after = System.currentTimeMillis();
                result.add(new Identity(file,mime,after-before));
            } catch (Exception e){
                long after = System.currentTimeMillis();
                result.add(new FailedIdentity(file,after-before,e));
            }
        }
        return result;
    }


    public static List<File> listRecursively(File dir){
        List<File> result = new LinkedList<File>();
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file : files) {
                result.addAll(listRecursively(file));
            }
        } else {
            result.add(dir);
        }
        return result;
    }

    public static void main(String... args) throws IOException {
        String inputDir = args[0];
        String outFile = null;
        if (args.length > 1){
            outFile = args[1];
        }
        List<File> files = listRecursively(new File(inputDir));
        SimpleWrapper that = new SimpleWrapper();
        List<Identity> detections = that.detect(files);
        if (outFile == null){
            prettyPrint(detections, System.out);
        } else {
            File outFilefile = new File(outFile);
            outFilefile.getParentFile().mkdirs();
            outFilefile.createNewFile();
            prettyPrint(detections, new FileOutputStream(outFilefile));
        }

    }

    private static void prettyPrint(List<Identity> detections, OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (Identity detection : detections) {
            if (detection instanceof FailedIdentity) {
                FailedIdentity failedIdentity = (FailedIdentity) detection;
                writer.println(detection.getTime()+"    "+detection.getFile());
                failedIdentity.getException().printStackTrace(writer);
                writer.println();
            } else {
                writer.println(detection.getTime()+"    "+detection.getFile()+"    "+detection.getMime());
            }
        }
        writer.flush();
    }

}
