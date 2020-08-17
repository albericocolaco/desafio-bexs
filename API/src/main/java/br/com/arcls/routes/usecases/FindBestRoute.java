package br.com.arcls.routes.usecases;

import br.com.arcls.routes.entities.Routes;
import br.com.arcls.routes.providers.filesystem.FileSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FindBestRoute {

    private static Logger logger = LoggerFactory.getLogger(FindBestRoute.class);

    @Autowired
    private FileSystemProvider fileSystemProvider;

    public Routes execute(final String filePath, final String from, final String to){
        final List<String> fileData = this.fileSystemProvider.readFile(filePath);
        Routes result = null;
        if(CollectionUtils.isEmpty(fileData)){
            logger.info("File is empty");
        } else {
            final List<Routes> routes = this.makeRoutes(fileData, from, to);
            if(!CollectionUtils.isEmpty(routes)){
                this.orderByDescValue(routes);
                result = routes.get(0);
            }
        }
        return result;
    }

    private void orderByDescValue(final List<Routes> routes){
        Collections.sort(routes, new Comparator<Routes>() {
            @Override
            public int compare(Routes route1, Routes route2){
                return  route1.getValue().compareTo(route2.getValue());
            }
        });
    }

    private List<Routes> makeRoutes(final List<String> rawRoutes, final String from, final String to){
        logger.info("Mapper data file to List Routes");
        final List<Routes> listRoutes = new ArrayList<>();
        rawRoutes.forEach(row ->{
            final Object[] rowDataArray = row.split(",");
            final List<String> steps = new ArrayList<>();
            BigDecimal value = null;
            int position = 0;
            Integer fromPosition = null;
            Integer toPosition = null;
            for (Object rowObject: rowDataArray) {
                try {
                    value = new BigDecimal((String) rowObject);
                } catch (Exception e) {
                    final String step = String.valueOf(rowObject);
                    if(from.equalsIgnoreCase(step)){
                        fromPosition = position;
                    } else if(to.equalsIgnoreCase(step)){
                        toPosition = position;
                    }
                    steps.add(step);
                }
                position++;
            }
            if(this.isValidRoute(fromPosition, toPosition)){
                listRoutes.add(Routes.builder().steps(steps).value(value).build());
            }
        });
        return listRoutes;
    }

    private boolean isValidRoute(final Integer fromPosition, final Integer toPosition){
        if(fromPosition == null || toPosition == null ||
           fromPosition > toPosition || fromPosition == toPosition) {
            return false;
        }
        return true;
    }
}
