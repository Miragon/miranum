package io.miranum.platform.s3.adapter.out.persistence;

import io.miranum.platform.s3.domain.model.FileData;
import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "File")
@Table(name = "File", indexes = {
        @Index(
                name = "index_path_to_file",
                columnList = "path_to_file"
        ),
        @Index(
                name = "index_end_of_life",
                columnList = "end_of_life"
        )
})
public class File extends BaseEntity {

    @Column(name = "path_to_file", nullable = false, unique = true, length = FileData.LENGTH_PATH_TO_FILE)
    private String pathToFile;

    @Column(name = "end_of_life")
    private LocalDate endOfLife;

}
