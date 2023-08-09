package com.example.Library.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDto {
    private Long id;
    private String fio;
    private String email;
    private String username;
    private Boolean isArchived;
}
