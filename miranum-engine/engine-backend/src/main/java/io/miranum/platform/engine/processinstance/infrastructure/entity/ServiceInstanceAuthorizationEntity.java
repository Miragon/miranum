package io.miranum.platform.engine.processinstance.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
@Entity(name = "ProcessInstanceAuth")
@Table(name = "DWF_PROCESS_INSTANCE_AUTH", indexes = {@Index(name = "IDX_DWF_PROCAUTH_USERID", columnList = "userid_")})
public class ServiceInstanceAuthorizationEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "processinstanceid_", nullable = false)
    private String processInstanceId;

    @Column(name = "userid_", nullable = false)
    private String userId;
}
