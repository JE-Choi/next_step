package webserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceViewResolver extends ViewResolverDefault {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceViewResolver.class);
    private final static String INDEX_RESOURCE = "/index.html";
    private final static String ERROR_RESOURCE = "/error.html";
    private final File responseFile;
    private final String prefix = "./webapp";


    public ResourceViewResolver(final HttpRequest request) {
        super(request);
        this.responseFile = loadFile(super.request.getRequestLine().getUrl());
    }

    private File loadFile(final String url) {
        String testUrl = prefix + "/index.html";
        // 파일 객체는 유효하든 아니든 생성가능.
        final File file = new File(prefix + url);
        if (file.canRead()) {
            return file;
        } else {
            /**
             * Question: 왜 스타일이 깨질까?
             * Note: 이렇게 에러났을때, index.html로 넣어주면
             * 문제생긴 요청 url앞부분이 접두어로 붙어서 리소스 로드됨 (접두어 안 붙어도)=> 스타일깨짐. => Note: 리다이렉트로 수정해야 할듯.
             * (이렇게 읽힘)HttpHead{method='GET', url='/user/js/bootstrap.min.js'
             */
            final File indexPage = new File(prefix + INDEX_RESOURCE);
            final File errorPage = new File(prefix + ERROR_RESOURCE);
            return errorPage;
//            return indexPage.canRead() ? indexPage : errorPage;
        }
    }

    @Override
    public String getContentType() {
        try {
            if (this.responseFile.canRead()) {
                return Files.probeContentType(this.responseFile.toPath());
            }
        } catch (IOException e) {
        }
        return super.getContentType();
    }

    @Override
    public byte[] getBodyBytes() {
        if (this.responseFile.canRead()) {
            try {
                List<String> lines = Files.readAllLines(this.responseFile.toPath(), this.getCharset()); // readAllBytes은 한글 인코딩 문제 가능성있음.
                String result = lines.stream().collect(Collectors.joining());
                LOGGER.info("로드된 파일명: " + this.responseFile.toPath());
                return result.getBytes();
            } catch (IOException e) {
            }
        }
        return super.getBodyBytes();
    }
}
