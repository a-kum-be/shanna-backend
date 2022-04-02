package com.atob.shanna.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class KnowledgeDto {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private List<KnowledgeDto> pointedBy;
}
