package com.bhs.sssss.vos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientVo {
    public static final String NAME = "clientVo";

    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public final String clientIp;
    public final String clientUa;
    public final String requestUri;

    public ClientVo(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String clientIp;
        if ((clientIp = request.getHeader("CF-Connecting-IP")) == null) { // request.getRemoteAddr()은 클라우드플레어의 엔드포인트 아이피를 돌려줌으로 반드시 CF-Connecting-IP 헤더 값을 참고해야 함.
            clientIp = request.getRemoteAddr();
        }
        this.clientIp = clientIp;
        String clientUa;
        if ((clientUa = request.getHeader("User-Agent")) == null) {
            clientUa = "";
        }
        this.clientUa = clientUa;
        this.requestUri = request.getRequestURI();
    }
}