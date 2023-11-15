package domain.queue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QueueImpl implements Queue {

  private QueueElement currentElement;
  private final List<QueueElement> nextSongs;
  private final List<QueueElement> previousSongs;

  public QueueImpl(List<QueueElement> nextSongs, List<QueueElement> previousSongs) {
    this.nextSongs = Collections.synchronizedList(nextSongs);
    this.previousSongs = Collections.synchronizedList(previousSongs);
  }

  @Override
  public Optional<QueueElement> getCurrentElement() {
    return Optional.ofNullable(currentElement);
  }

  @Override
  public void goToNextElement() {
    getCurrentElement().ifPresent(queueElement -> previousSongs.add(0, queueElement));
    if (nextSongs.isEmpty()) {
      currentElement = null;
    } else {
      currentElement = nextSongs.remove(0);
    }
  }

  @Override
  public void goToPreviousElement() {
    if (!previousSongs.isEmpty()) {
      getCurrentElement().ifPresent(queueElement -> nextSongs.add(0, queueElement));
      currentElement = previousSongs.remove(0);
    }
  }

  @Override
  public void addToFront(QueueElement queueElement) {
    nextSongs.add(0, queueElement);
  }

  @Override
  public void addToEnd(QueueElement queueElement) {
    nextSongs.add(queueElement);
  }

  @Override
  public List<QueueElement> getNextElements() {
    return Collections.unmodifiableList(this.nextSongs);
  }

}
