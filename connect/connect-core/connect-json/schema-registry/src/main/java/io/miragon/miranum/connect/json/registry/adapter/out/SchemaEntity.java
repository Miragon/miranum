/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miragon.miranum.connect.json.registry.adapter.out;

import lombok.*;

import jakarta.persistence.*;

/**
 * Entity representation of a form.
 *
 * @author externer.dl.horn
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "JsonSchema")
@Table(name = "DWF_JSON_SCHEMA", indexes = {@Index(name = "IDX_DWF_SCHEMAKEY", columnList = "key_")})
public class SchemaEntity {

    @Id
    @Column(name = "key_")
    private String key;

    @Column(name = "schema_", columnDefinition = "CLOB")
    private String schema;


}
