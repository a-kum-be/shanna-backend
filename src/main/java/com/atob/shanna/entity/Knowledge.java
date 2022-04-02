package com.atob.shanna.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "knowledge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    private Set<Knowledge> relations;
}
