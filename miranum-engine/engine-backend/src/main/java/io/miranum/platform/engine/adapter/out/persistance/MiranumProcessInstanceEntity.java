package io.miranum.platform.engine.adapter.out.persistance;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
@Entity(name = "MiranumProcessInstance")
@Table(name = "MIRANUM_PROCESS_INSTANCE")
public class MiranumProcessInstanceEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "processname_", nullable = false)
    private String definitionName;

    @Column(name = "processdefinitionkey_", nullable = false)
    private String definitionKey;

    @Column(name = "description_")
    private String description;

    @Column(name = "status_")
    private String status;

    @Column(name = "statuskey_")
    private String statusKey;

    @Column(name = "starttime_")
    private Date startTime;

    @Column(name = "endtime_")
    private Date endTime;

    @Column(name = "removaltime_")
    private Date removalTime;
}
