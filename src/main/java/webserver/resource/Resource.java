package webserver.resource;

import java.io.IOException;

public interface Resource {
    boolean isValid();
    String getContentType() throws IOException;
}
