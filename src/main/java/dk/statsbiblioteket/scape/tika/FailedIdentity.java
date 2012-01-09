package dk.statsbiblioteket.scape.tika;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: abr
 * Date: 12/16/11
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FailedIdentity extends Identity{
    private Exception exception;

    public FailedIdentity(File file, long time, Exception exception) {
        super(file, "",time);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
