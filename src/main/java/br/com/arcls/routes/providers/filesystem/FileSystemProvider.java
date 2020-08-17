package br.com.arcls.routes.providers.filesystem;

import br.com.arcls.routes.exceptions.FileSystemProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemProvider {

    private static Logger logger = LoggerFactory.getLogger(FileSystemProvider.class);

    public List<String> readFile(final String filePath) {
        BufferedReader bufferedReader = null;
        final ArrayList<String> fileData = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
        } catch (IOException e) {
            logger.error("Error to read file", e);
            throw new FileSystemProviderException();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("Error to close bufferReader", e);
                    throw new FileSystemProviderException();
                }
            }
        }
        return fileData;
    }

    public void writeFile(final String filePath, final List<String> lines){
        try {
            final String brakeLine = "\n";
            final FileWriter fw = new FileWriter(filePath,true);
            lines.forEach(line -> {
                try {
                    fw.write(String.format("%s%s", line, brakeLine));
                } catch (IOException e) {
                    logger.error("Error to write line in file", e);
                    throw new FileSystemProviderException();
                }
            });
            fw.close();
        } catch(IOException e) {
            logger.error("Error to write lines in file", e);
            throw new FileSystemProviderException();
        }
    }
}
