package dk.statsbiblioteket.scape.govdocs1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 1/4/12
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroundTruthBean {

    private String accuracy, mime, charset, digest, extensions, filename, id, kind, size, version;

    private Set<String> mimes;


    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        mime = mime.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("'", "");
        String[] splits = mime.split(", ");

        mimes = new HashSet<String>(splits.length);
        for (String split : splits) {
            mimes.add(split.intern());
        }
        mimes = Collections.unmodifiableSet(mimes);
        this.mime = mime;
    }

    public boolean isOfMime(String mime){
        return mimes.contains(mime);
    }
    public Set<String> getMimes() {
        return mimes;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename.intern();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version.intern();
    }
}
