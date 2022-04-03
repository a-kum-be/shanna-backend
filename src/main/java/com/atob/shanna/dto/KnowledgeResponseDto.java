package com.atob.shanna.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class KnowledgeResponseDto {

    private Long id;
    @NonNull
    public String name;
    @NonNull
    private String description;
    @NonNull
    public List<KnowledgeResponseDto> pointedBy;
}
