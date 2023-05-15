package io.miragon.miranum.connect.json.registry.adapter.out;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SchemaEntity")
@Table(name = "MIRANUM_SCHEMA_REGISTRY")
public class SchemaEntity {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "ref_")
    private String ref;

    @Column(name = "version_")
    private Integer version;

    @Lob
    @Column(name = "json_schema_")
    private String jsonSchema;
}
