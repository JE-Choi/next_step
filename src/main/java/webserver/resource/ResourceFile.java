package webserver.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceFile implements Resource{
    // 파일 객체는 유효하든 아니든 생성가능.
    private final File file;

    public ResourceFile(final File file) {
        this.file = file;
    }

    @Override
    public boolean isValid() {
        return file.canRead() && !file.isDirectory();
    }

    @Override
    public String getContentType() throws IOException {
        return Files.probeContentType(this.file.toPath());
    }

    public Path toPath(){
        return this.file.toPath();
    }
}
