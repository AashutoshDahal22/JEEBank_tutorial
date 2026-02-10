package org.example.web.filter;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonObjectMapperProvider() {
        mapper = new ObjectMapper();
        // Register JavaTimeModule to handle LocalDate, LocalDateTime, etc.
        mapper.registerModule(new JavaTimeModule());
        // Prevent serialization of dates as timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
