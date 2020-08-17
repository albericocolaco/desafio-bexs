package br.com.arcls.routes.controllers.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFindBestRouteDTO{
    private String from;
    private String to;
}
