package br.com.arcls.routes.usecases;

import br.com.arcls.routes.entities.CSVInputRoutes;
import br.com.arcls.routes.entities.enums.StatusEnum;
import br.com.arcls.routes.exceptions.FileSystemProviderException;
import br.com.arcls.routes.providers.filesystem.FileSystemProvider;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddNewRoute {

    @Autowired
    private FileSystemProvider fileSystemProvider;

    public StatusEnum execute(final String outPutRouteData, final MultipartFile file){
        try {
            final List<CSVInputRoutes> inputLines = this.csvInputRoutes(file.getInputStream());
            this.fileSystemProvider.writeFile(outPutRouteData, this.lines(inputLines));
        } catch (IOException | FileSystemProviderException e) {
            return StatusEnum.ERROR;
        }
        return StatusEnum.SUCCESS;
    }

    private List<CSVInputRoutes> csvInputRoutes(final InputStream is) {
        try {
            final BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            final CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            final List<CSVInputRoutes> inputLines = new ArrayList<>();
            final Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            csvRecords.forEach(csvRecord -> {
                inputLines.add(CSVInputRoutes.builder().routers(csvRecord.get("Router")).value(csvRecord.get("Value")).build());
            });

            return inputLines;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private List<String> lines(final List<CSVInputRoutes> csvInputRoutes){
        final List<String> lines = new ArrayList<>();
        csvInputRoutes.forEach(line->{
            final String routes = line.getRouters().replaceAll("-",",");
            lines.add(routes+","+line.getValue());
        });
        return lines;
    }

}
