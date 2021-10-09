package webserver.request;

import java.util.Arrays;

/**
 * https://gmlwjd9405.github.io/2019/01/28/http-header-types.html
 * 기반으로 구현한 것
 * Note: package com.google.common.net.HttpHeaders 사용해도 됨.
 * com.sun.net.httpserver.Header도 있음.
 */
public enum HttpHeader {
    // HTTP 공통 헤더
    DATE("Date", ""),
    TRAILER("Trailer", ""),
    PRAGMA("Pragma", ""),
    CONNECTION("Connection", "클라이언트와 서버 간 연결에 대한 옵션 설정"),

    // HTTP 엔티티 관련 헤더 (요청&응답 공통)
    CONTENT_TYPE("Content-Type", "해당 개체에 포함되는 미디어 타입 정보"),
    CONTENT_LANGUAGE("Content-Language", "해당 개체와 가장 잘 어울리는 사용자 언어(자연언어)"),
    CONTENT_ENCODING("Content-Encoding", "해당 개체 데이터의 압축 방법"),
    CONTENT_LENGTH("Content-Length", "전달되는 해당 개체의 바이트 길이 또는 크기(10진수)"),
    CONTENT_LOCATION("Content-Location", "해당 개체의 실제 위치"),

    // HTTP 요청 헤더
    HOST("Host", "호스트명 및 포트번호 (필수)"),
    USER_AGENT("User-Agent", "클라이언트 소프트웨어(브라우저, OS) 명칭 및 버전 정보"),
    FROM("From", "클라이언트 사용자 메일 주소"),
    COOKIE("Cookie", "서버에 의해 Set-Cookie로 클라이언트에게 설정된 쿠키 정보"),
    REFERER("Referer", "바로 직전에 머물었던 웹 링크 주소"),
    IF_MODIFIED_SINCE("If-Modified-Since", "제시한 일시 이후로만 변경된 리소스를 취득 요청"),
    ACCEPT("Accept", "클라이언트 자신이 원하는 미디어 타입 및 우선순위를 알림"),
    ACCEPT_CHARSET("Accept-Charset", "클라이언트 자신이 원하는 문자 집합"),
    ACCEPT_ENCODING("Accept-Encoding", "클라이언트 자신이 원하는 문자 인코딩 방식"),
    ACCEPT_LANGUAGE("Accept-Language", "클라이언트 자신이 원하는 가능한 언어"),
    AUTHORIZATION("Authorization", "인증 토큰(JWT/Bearer 토큰)을 서버로 보낼 때 사용하는 헤더"),
    ORIGIN("Origin", "서버로 POST 요청을 보낼 때, 요청이 어느 주소에서 시작되었는지 나타낸다."),

    UPGRADE_INSECURE_REQUEST("Upgrade-Insecure-Requests", ""),
    SEC_FETCH_SITE("Sec-Fetch-Site", ""),
    SEC_FETCH_MODE("Sec-Fetch-Mode", ""),
    SEC_FETCH_USER("Sec-Fetch-User", ""),
    SEC_FETCH_DEST("Sec-Fetch-Dest", ""),
    SEC_CH_UA("sec-ch-ua", ""),
    SEC_CH_MOBILE("sec-ch-ua-mobile", ""),
    SEC_CH_PLATFORM("sec-ch-ua-platform", ""),

    // Caching 관련
    CACHE_CONTROL("Cache-Control", "쿠키/캐시 관련"),

    NOT_FOUND("NOT_FOUND", ""),
    ;
    private final String key;
    private final String desc;

    HttpHeader(final String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static HttpHeader getTitle(final String str) {
        return Arrays.stream(values()).filter(title -> title.key.equals(str)).findFirst().orElse(NOT_FOUND);
    }
}