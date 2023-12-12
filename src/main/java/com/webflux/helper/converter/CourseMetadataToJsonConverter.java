package com.webflux.helper.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.model.metadata.CourseMetadata;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.io.IOException;

@WritingConverter
public class CourseMetadataToJsonConverter implements Converter<CourseMetadata, Json> {
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(CourseMetadataToJsonConverter.class);


    public CourseMetadataToJsonConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Json convert(CourseMetadata source) {
        try {
            return Json.of(mapper.writeValueAsBytes(source));
        } catch (IOException e) {
            logger.error("Error while converting CourseMetadata to Json", e);
            throw new RuntimeException(e);
        }
    }
}
