package com.damselfly.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;

/**
 * Created by  moxz
 * User: 224911261@qq.com
 * 2015/9/19-21:27
 * damselfly.com.damselfly.common.util
 */
public class RestJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public RestJackson2HttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public RestJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, new MediaType("application", "json", DEFAULT_CHARSET),
                new MediaType("application", "*+json", DEFAULT_CHARSET));
    }

    @Override
    protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
        String prefix = "{\"apistatus\":1,\"result\":";
        generator.writeRaw(prefix);
        String jsonpFunction =
                (object instanceof MappingJacksonValue ? ((MappingJacksonValue) object).getJsonpFunction() : null);
        if (jsonpFunction != null) {
            generator.writeRaw(jsonpFunction + "(");
        }
    }

    @Override
    protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
        String jsonpFunction =
                (object instanceof MappingJacksonValue ? ((MappingJacksonValue) object).getJsonpFunction() : null);
        if (jsonpFunction != null) {
            generator.writeRaw(");");
        }
        String suffix = "}";
        generator.writeRaw(suffix);
    }
}
