package io.miranum.platform.connect.json.registry.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id_", unique = true, length = 36)
    private String id;

    @Column(name = "bundle_")
    private String bundle;

    @Column(name = "ref_")
    private String ref;

    @Column(name = "tag_")
    private String tag;

    @Column(name = "json_node_", columnDefinition = "CLOB")
    private String jsonNode;
}
