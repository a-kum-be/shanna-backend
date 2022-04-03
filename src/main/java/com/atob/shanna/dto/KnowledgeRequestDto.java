package com.atob.shanna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeRequestDto {
    @Size(max = 30000)
    private String text;
}
