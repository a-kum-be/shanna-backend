package com.atob.shanna.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class KnowledgeDto {

    private Long id;
    @NonNull
    public String name;
    @NonNull
    private String description;
    @NonNull
    public List<KnowledgeDto> pointedBy;
}
