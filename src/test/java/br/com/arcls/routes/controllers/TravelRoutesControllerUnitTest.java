package br.com.arcls.routes.controllers;

import br.com.arcls.routes.entities.Routes;
import br.com.arcls.routes.entities.enums.StatusEnum;
import br.com.arcls.routes.usecases.AddNewRoute;
import br.com.arcls.routes.usecases.FindBestRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TravelRoutesController.class)
public class TravelRoutesControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FindBestRoute findBestRoute;

    @MockBean
    private AddNewRoute addNewRoute;

    @Test
    public void shouldAddNewRouteSuccess() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("data", "input-routes.csv", "multipart/form-data", "some xml".getBytes());

        given(addNewRoute.execute(any(), any())).willReturn(StatusEnum.SUCCESS);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/travel-routes")
                .file("file", file.getBytes())).andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    public void shouldAddNewRouteError() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("data", "input-routes.csv", "multipart/form-data", "some xml".getBytes());

        given(addNewRoute.execute(any(), any())).willReturn(StatusEnum.ERROR);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/travel-routes")
                .file("file", file.getBytes())).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void shouldFindBestRouteResultNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travel-routes?from=GRU&to=ORL"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldFindBestRouteResultSuccess() throws Exception {
        final Routes routes = Routes.builder().value(BigDecimal.valueOf(40)).steps(Arrays.asList("GRU", "ABC", "ORL")).build();

        given(findBestRoute.execute(any(), any(), any())).willReturn(routes);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travel-routes?from=GRU&to=ORL")).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void shouldFindBestRouteParamsNotInformed() throws Exception {
        final Routes routes = Routes.builder().value(BigDecimal.valueOf(40)).steps(Arrays.asList("GRU", "ABC", "ORL")).build();

        given(findBestRoute.execute(any(), any(), any())).willReturn(routes);

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travel-routes")).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}
