package com.webflux.dto;

import java.util.List;

public record StudentRequest(String name, String dateOfBirth, String email, List<String> courses) {
}
