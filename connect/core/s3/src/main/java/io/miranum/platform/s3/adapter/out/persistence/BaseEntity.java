/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package io.miranum.platform.s3.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id", length = 36, columnDefinition = "CHAR(36)")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Convert(converter = UuidConverter.class)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @CreatedDate
    @Column(name = "created_time", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdTime;

    @Version
    @Column(name = "version")
    private Long version;

    public Long getEntityVersion() {
        return this.version;
    }

}
