package actions;

import fileio.Writer;
import org.json.simple.JSONArray;
import users.User;

import java.io.IOException;

public interface Action {
    void execute(Writer filewriter, JSONArray arrayResult) throws IOException;
}
