package br.com.arcls.routes.usecases;

import br.com.arcls.routes.entities.enums.StatusEnum;
import br.com.arcls.routes.exceptions.FileSystemProviderException;
import br.com.arcls.routes.providers.filesystem.FileSystemProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AddNewRouteUnitTest {

    @InjectMocks
    private AddNewRoute addNewRoute;

    @Mock
    private FileSystemProvider fileSystemProvider;

    private MultipartFile multipartFile;

    @Before
    public void setup(){
        try {
            multipartFile = new MockMultipartFile("input-routes.csv", new FileInputStream(new File("src/test/resources/input-routes.csv")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldWriteSuccess(){
        doNothing().when(fileSystemProvider).writeFile(any(), any());

        final StatusEnum status = addNewRoute.execute("src/test/resources/output-routes.csv", multipartFile);

        assertEquals(StatusEnum.SUCCESS, status);
        verify(fileSystemProvider, times(1)).writeFile(any(), any());
    }

    @Test
    public void shouldWriteError(){
        doThrow(FileSystemProviderException.class).when(fileSystemProvider).writeFile(any(), any());

        final StatusEnum status = addNewRoute.execute("src/test/resources/output-routes.csv", multipartFile);

        assertEquals(StatusEnum.ERROR, status);
        verify(fileSystemProvider, times(1)).writeFile(any(), any());
    }
}
