package br.com.arcls.routes.controllers.dto.request;

import java.io.Serializable;

public class RequestFindBestRouteDTO implements Serializable {

    private String from;
    private String to;

    public RequestFindBestRouteDTO() {
    }

    public RequestFindBestRouteDTO(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
