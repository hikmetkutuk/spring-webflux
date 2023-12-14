package com.webflux.model.metadata;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class EnglishCourseMetadata extends CourseMetadata {

    private String level;
    private List<String> books;
}
