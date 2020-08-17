package br.com.arcls.routes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Routes {
    private List<String> steps;
    private BigDecimal value;
}
