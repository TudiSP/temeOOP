package actions;

import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;

public interface Action {
    /**
     * method to be implemented by other classes
     * @param filewriter
     * @param arrayResult
     * @throws IOException
     */
    void execute(Writer filewriter, JSONArray arrayResult) throws IOException;
}
