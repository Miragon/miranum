package io.miranum.platform.engine.adapter.out.persistance.startcontext;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity representation of a Start Context.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StartContext")
@Table(name = "MIRANUM_START_CONTEXT", indexes = {@Index(name = "IDX_DWF_CONTEXT_USER", columnList = "userid_, definitionkey_")})
public class StartContextEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "userid_", nullable = false)
    private String userId;
    @Column(name = "definitionkey_", nullable = false)
    private String definitionKey;

}
