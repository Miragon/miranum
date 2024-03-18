package io.miranum.platform.engine.adapter.out.persistance.process;

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
@Entity(name = "MiranumInstanceAuth")
@Table(name = "MIRANUM_PROCESS_INSTANCE_AUTH", indexes = {@Index(name = "IDX_MIRANUM_PROCAUTH_USERID", columnList = "userid_")})
public class ProcessInstanceAuthorizationEntity {

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
