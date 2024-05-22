package io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo;

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
@Entity(name = "TaskInfo")
@Table(name = "MIRANUM_TASK_INFO")
public class TaskInfoEntity {

    @Id
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "description_")
    private String description;

    @Column(name = "definitionname_", nullable = false)
    private String definitionName;

    @Column(name = "instanceid_", nullable = false)
    private String instanceId;

    @Column(name = "assignee_")
    private String assignee;

    @Column(name = "candidateusers_")
    private String candidateUsers;

    @Column(name = "candidategroups_")
    private String candidateGroups;

    @Column(name = "form_")
    private String form;

}
