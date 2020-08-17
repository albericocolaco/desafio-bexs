package br.com.arcls.routes.usecases;

import br.com.arcls.routes.entities.Routes;
import br.com.arcls.routes.providers.filesystem.FileSystemProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class FindBestRouteUnitTest {

    @InjectMocks
    private FindBestRoute findBestRoute;

    @Mock
    private FileSystemProvider fileSystemProvider;

    @Value("${output.data}")
    private String outPutRouteData;

    private List<String> outPutList1;
    private List<String> outPutList2;

    @Before
    public void setup(){
        outPutList1 = Arrays.asList(
                "GRU,BRC,10", "BRC,SCL,5", "GRU,CDG,75",
                "GRU,SCL,20", "GRU,ORL,56", "ORL,CDG,5",
                "SCL,ORL,20", "GRU,BRC,SCL,ORL,CDG,40",
                "GRU,ORL,CGD,64", "GRU,CDG,75",
                "GRU,SCL,ORL,CDG,48", "GRU,BRC,CDG,45"
        );

        outPutList2 = Arrays.asList(
                "GRU,BRC,10", "BRC,SCL,5", "CDG,GRU,75",
                "GRU,SCL,20", "GRU,ORL,56", "ORL,CDG,5",
                "SCL,ORL,20", "CDG,BRC,SCL,ORL,GRU,40",
                "CDG,ORL,GRU,64", "CDG,GRU,75",
                "CDG,SCL,ORL,GRU,48", "CDG,BRC,GRU,45"
        );
    }

    @Test
    public void shouldFoundRouteSuccess(){
        final String from = "GRU";
        final String to = "CDG";

        when(fileSystemProvider.readFile(any())).thenReturn(outPutList1);

        final Routes routes = findBestRoute.execute(outPutRouteData, from, to);

        assertNotNull(routes);
        assertEquals(BigDecimal.valueOf(40), routes.getValue());
        assertEquals(from, routes.getSteps().get(0));
        assertEquals(to, routes.getSteps().get(routes.getSteps().size()-1));
        verify(fileSystemProvider, times(1)).readFile(any());
    }

    @Test
    public void shouldNotFoundRoute(){
        final String from = "GRU";
        final String to = "CDG";

        when(fileSystemProvider.readFile(any())).thenReturn(outPutList2);

        final Routes routes = findBestRoute.execute(outPutRouteData, from, to);

        assertNull(routes);
        verify(fileSystemProvider, times(1)).readFile(any());
    }

    @Test
    public void shouldNotFoundRouteReadFileEmpty(){
        final String from = "GRU";
        final String to = "CDG";

        when(fileSystemProvider.readFile(any())).thenReturn(new ArrayList<String>());

        final Routes routes = findBestRoute.execute(outPutRouteData, from, to);

        assertNull(routes);
        verify(fileSystemProvider, times(1)).readFile(any());
    }

    @Test
    public void shouldNotFoundRouteReadFileNull(){
        final String from = "GRU";
        final String to = "CDG";

        when(fileSystemProvider.readFile(any())).thenReturn(null);

        final Routes routes = findBestRoute.execute(outPutRouteData, from, to);

        assertNull(routes);
        verify(fileSystemProvider, times(1)).readFile(any());
    }


}
