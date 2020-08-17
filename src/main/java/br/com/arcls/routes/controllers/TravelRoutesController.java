package br.com.arcls.routes.controllers;

import br.com.arcls.routes.controllers.dto.response.ResponseFindBestRouteDTO;
import br.com.arcls.routes.entities.Routes;
import br.com.arcls.routes.entities.enums.StatusEnum;
import br.com.arcls.routes.usecases.AddNewRoute;
import br.com.arcls.routes.usecases.FindBestRoute;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/travel-routes")
public class TravelRoutesController {

    private static Logger logger = LoggerFactory.getLogger(TravelRoutesController.class);

    @Autowired
    private FindBestRoute findBestRoute;
    @Autowired
    private AddNewRoute addNewRoute;

    @Value("${input.data}")
    private String inputRoutesData;
    @Value("${output.data}")
    private String outPutRouteData;

    @PostMapping
    public ResponseEntity<Void> addRoute(@RequestParam("file") MultipartFile file){
        if(file != null && this.addNewRoute.execute(outPutRouteData, file).equals(StatusEnum.SUCCESS)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<ResponseFindBestRouteDTO> findBestRoute(@RequestParam String from, @RequestParam String to){
        logger.info("Init findBestRoute");
        if(from == null || to == null){
            logger.info("Params not informed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            final Routes route = this.findBestRoute.execute(this.inputRoutesData, from, to);

            if(route == null){
                logger.info("Route not found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            logger.info("Route found");
            return ResponseEntity.ok(
                    ResponseFindBestRouteDTO.builder()
                            .router(StringUtils.join(route.getSteps(), '-'))
                            .value(route.getValue())
                            .build()
            );
        }

    }
}
