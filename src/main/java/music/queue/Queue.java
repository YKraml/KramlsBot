package music.queue;

import java.util.List;
import java.util.Optional;

public interface Queue {

  Optional<QueueElement> getCurrentElement();

  List<QueueElement> getNextElements();

  void goToNextElement();

  void goToPreviousElement();

  void addToFront(QueueElement queueElement);

  void addToEnd(QueueElement queueElement);


}
