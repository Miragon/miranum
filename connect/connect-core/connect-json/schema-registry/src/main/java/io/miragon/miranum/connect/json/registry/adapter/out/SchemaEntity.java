package io.miragon.miranum.connect.json.registry.adapter.out;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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

    @Lob
    @Column(name = "json_node_")
    private String jsonNode;
}
