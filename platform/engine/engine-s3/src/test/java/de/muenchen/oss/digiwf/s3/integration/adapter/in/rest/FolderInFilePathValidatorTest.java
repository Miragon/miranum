package de.muenchen.oss.digiwf.s3.integration.adapter.in.rest;

import de.muenchen.oss.digiwf.s3.integration.adapter.in.rest.validation.FolderInFilePathValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FolderInFilePathValidatorTest {

    private final FolderInFilePathValidator folderInFilePathValidator = new FolderInFilePathValidator();

    @Test
    void isValid() {
        assertThat(this.folderInFilePathValidator.isValid("folder/file.txt", null)).isTrue();
        assertThat(this.folderInFilePathValidator.isValid("folder/subfolder/file.txt", null)).isTrue();
        assertThat(this.folderInFilePathValidator.isValid("file.txt", null)).isFalse();
        assertThat(this.folderInFilePathValidator.isValid("", null)).isFalse();
        assertThat(this.folderInFilePathValidator.isValid(null, null)).isFalse();
    }
}
