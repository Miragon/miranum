package io.miranum.integration.s3.adapter.in.rest.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FolderInFilePathValidator implements ConstraintValidator<FolderInFilePath, String> {

  public static final String SEPARATOR = "/";

  /**
   * Checks if the file path contains a folder.
   *
   * @param pathToFile the file path.
   * @param context    context in which the constraint is evaluated
   * @return false if the filepath contains no folder, <code>true</code> otherwise.
   */
  public boolean isValid(final String pathToFile, final ConstraintValidatorContext context) {
    final String pathToFolder = StringUtils.substringBeforeLast(pathToFile, SEPARATOR);
    return StringUtils.contains(pathToFile, SEPARATOR) && StringUtils.isNotEmpty(pathToFolder);
  }

}
