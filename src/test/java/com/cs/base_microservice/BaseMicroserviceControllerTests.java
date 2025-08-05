package com.cs.base_microservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cs.base_microservice.service.ServiceInfoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BaseMicroserviceConfigurationController.class)
@TestPropertySource(properties = {
    "spring.cloud.config.enabled=false",
    "spring.cloud.discovery.enabled=false"
})
class BaseMicroserviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceInfoService serviceInfoService;

    @Test
    void testServiceInfoEndpoint() throws Exception {
        mockMvc.perform(get("/service-info"))
                .andExpect(status().isOk());
    }
}
