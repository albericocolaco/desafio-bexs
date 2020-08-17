package br.com.arcls.routes.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class ResponseFindBestRouteDTO {
    private String router;
    private BigDecimal value;
}
