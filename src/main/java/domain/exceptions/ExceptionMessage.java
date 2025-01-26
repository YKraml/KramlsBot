package domain.exceptions;

public interface ExceptionMessage {

  String getContent();

  default String getClassName() {
    return this.getClass().getSimpleName();
  }
}
