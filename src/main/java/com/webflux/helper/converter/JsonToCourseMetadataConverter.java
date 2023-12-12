package com.webflux.helper.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.model.metadata.CourseMetadata;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@ReadingConverter
public class JsonToCourseMetadataConverter implements Converter<Json, CourseMetadata> {
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(JsonToCourseMetadataConverter.class);

    public JsonToCourseMetadataConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CourseMetadata convert(Json source) {
        try {
            return mapper.readValue(source.asString(), CourseMetadata.class);
        } catch (IOException e) {
            logger.error("Error while converting Json to CourseMetadata", e);
            throw new RuntimeException(e);
        }
    }
}
