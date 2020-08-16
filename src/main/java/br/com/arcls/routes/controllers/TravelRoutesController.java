package br.com.arcls.routes.controllers;

import br.com.arcls.routes.controllers.dto.request.RequestFindBestRouteDTO;
import br.com.arcls.routes.controllers.dto.response.ResponseFindBestRouteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/api/v1/travel-routes")
public class TravelRoutesController {

    @PostMapping
    public ResponseEntity<Void> addRoute(@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<ResponseFindBestRouteDTO> findBestRoute(@RequestBody RequestFindBestRouteDTO requestFindBestRouteDTO){
        return ResponseEntity.ok(new ResponseFindBestRouteDTO("GRU-REC", new BigDecimal(100)));
    }
}
