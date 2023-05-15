/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miragon.miranum.connect.json.registry.adapter.out;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SchemaEntity")
@Table(name = "MIRANUM_JSON_SCHEMA")
public class SchemaEntity {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "key_")
    private String key;

    @Column(name = "version_")
    private Integer version;

    @Lob
    @Column(name = "json_schema_")
    private String jsonSchema;
}
