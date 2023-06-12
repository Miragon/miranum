package io.miragon.miranum.integrations.example.adapter.out;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "data_object")
public class DataContainer {

    @Id
    private Long id;

    @Column(name = "name_")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_")
    private List<DataObject> dataObjects;

}