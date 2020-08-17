package br.com.arcls.routes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class CSVInputRoutes {
    private String routers;
    private String value;
}
