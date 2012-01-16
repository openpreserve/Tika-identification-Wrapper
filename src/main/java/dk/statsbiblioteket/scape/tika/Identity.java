package dk.statsbiblioteket.scape.tika;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 12/16/11
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Identity {

    private String mime;

    private long time;

    public Identity(File file, String mime, long time) {
        //this.file = file;
        this.mime = mime;
        this.time = time;
    }

    public String getMime() {
        return mime;
    }

    public long getTime() {
        return time;
    }

}
