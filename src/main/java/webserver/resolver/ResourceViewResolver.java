package webserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.resource.Resource;
import webserver.resource.ResourceFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceViewResolver extends ViewResolverDefault {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceViewResolver.class);
    private final static String INDEX_RESOURCE = "/index.html";
    private final static String ERROR_RESOURCE = "/error.html";
    private final static String PREFIX = "./webapp";
    private final Resource responseFile;
    private final String body;


    public ResourceViewResolver(final HttpRequest request) {
        super(request);
        this.responseFile = loadFile(super.request.getRequestLine().getUri().getRequestUri());
        this.body = createBody();
    }

    private Resource loadFile(final String uri) {
        final Resource resource = new ResourceFile(new File(PREFIX + uri));

        if (resource.isValid()) {
            return resource;
        } else {
            final Resource index = new ResourceFile(new File(PREFIX + INDEX_RESOURCE));
            final Resource error = new ResourceFile(new File(PREFIX + ERROR_RESOURCE));
            return index.isValid() ? index : error;
        }
    }

    /**
     * Todo: Response Header(Content-Type: text/css;charset=UTF-8) 이렇게 데이터 세팅되도록 객체화 필요
     */
    @Override
    public String getContentType() {
        try {
            return this.responseFile.getContentType();
        } catch (IOException e) {
        }
        return super.getContentType();
    }

    @Override
    public byte[] getBodyBytes() {
        return this.body.getBytes();
    }

    private String createBody() {
        if (this.responseFile.isValid()) {
            try {
                ResourceFile responseFile = (ResourceFile) this.responseFile;
                // readAllBytes은 한글 인코딩 문제 가능성있음.
                List<String> lines = Files.readAllLines(responseFile.toPath(), this.getCharset());
                String result = lines.stream().collect(Collectors.joining());
                LOGGER.info("로드된 파일명: " + responseFile.toPath());
                return result;
            } catch (IOException e) {
            }
        }
        return super.getBody();
    }

    @Override
    public String getBody() {
        return this.body;
    }
}
