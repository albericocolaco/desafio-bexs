package br.com.arcls.routes.controllers.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResponseFindBestRouteDTO implements Serializable {

    private String router;
    private BigDecimal value;

    public ResponseFindBestRouteDTO() {
    }

    public ResponseFindBestRouteDTO(String router, BigDecimal value) {
        this.router = router;
        this.value = value;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
