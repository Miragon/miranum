package io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TaskAuthorities")
@Table(name = "MIRANUM_TASK_AUTHORITIES")
public class TaskAuthorityEntity {

    /**
     * The id of the authority
     */
    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    /**
     * The type of the authority (e.g. user, group)
     */
    @Column(name = "type_", nullable = false)
    private String type;

    /**
     * The value of the authority (e.g. the username, the group name)
     */
    @Column(name = "value_", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskinfo_id")
    private TaskInfoEntity taskInfo;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

}
