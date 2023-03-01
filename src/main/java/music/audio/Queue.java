package music.audio;

import java.util.List;

public interface Queue {

  QueueElement getCurrentElement();

  boolean goToNextElement();

  boolean goToPreviousElement();

  void addToStart(QueueElement queueElement);

  void addToEnd(QueueElement queueElement);

  List<QueueElement> getNextElements();

  List<QueueElement> getPreviousElements();

  boolean goToNextElement(QueueElement queueElement);

  boolean goToPreviousElement(QueueElement queueElement);

  boolean hasNextSongs();

  boolean hasPreviousSongs();

  void clear();

}
