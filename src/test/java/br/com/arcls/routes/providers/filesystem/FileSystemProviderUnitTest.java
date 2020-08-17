package br.com.arcls.routes.providers.filesystem;

import br.com.arcls.routes.exceptions.FileSystemProviderException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileSystemProviderUnitTest {

    @InjectMocks
    private FileSystemProvider fileSystemProvider;

    private String outputFile = "src/test/resources/output-routes.csv";

    private File file;

    @Before
    public void init() throws IOException {
        this.file = File.createTempFile("FileWritingTest", "tmp");
        file.deleteOnExit();
    }

    @After
    public void clean() throws IOException {
        file.delete();
    }

    @Test
    public void shouldReadSuccess(){
        final List<String> result = this.fileSystemProvider.readFile(this.outputFile);

        assertNotNull(result);
        assertEquals(12, result.size());
        assertEquals("GRU,BRC,10", result.get(0));
    }

    @Test(expected = FileSystemProviderException.class)
    public void shouldReadErrorFileNotFound(){
        this.fileSystemProvider.readFile("src/test/resources/notfound.csv");
    }

    @Test
    public void shouldWriteSuccess() throws IOException {
        final String content = "test";
        this.fileSystemProvider.writeFile(this.file.getPath(), Arrays.asList(content));

        final List<String> result = Files.readAllLines(file.toPath());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(content, result.get(0));
    }
}
