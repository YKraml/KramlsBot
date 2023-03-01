package music.audio;

import java.util.Collections;
import java.util.List;

public class QueueImpl implements Queue {

    private QueueElement currentElement;
    private final List<QueueElement> nextSongs;
    private final List<QueueElement> previousSongs;

    public QueueImpl(List<QueueElement> nextSongs, List<QueueElement> previousSongs) {
        this.nextSongs = Collections.synchronizedList(nextSongs);
        this.previousSongs = Collections.synchronizedList(previousSongs);
    }

    @Override
    public QueueElement getCurrentElement() {
        return currentElement;
    }

    @Override
    public boolean goToNextElement() {
        if (nextSongs.isEmpty()) {
            if (currentElement != null) {
                this.previousSongs.add(currentElement);
            }
            currentElement = null;
            return false;
        } else {
            if (this.currentElement != null) {
                this.previousSongs.add(0, this.currentElement);
            }
            this.currentElement = nextSongs.remove(0);
            return true;
        }
    }

    @Override
    public boolean goToPreviousElement() {
        if (previousSongs.isEmpty()) {
            return false;
        } else {
            if (this.currentElement != null) {
                this.nextSongs.add(0, this.currentElement);
            }
            this.currentElement = previousSongs.remove(0);
            return true;
        }
    }

    @Override
    public void addToStart(QueueElement queueElement) {
        this.nextSongs.add(0, queueElement);
    }

    @Override
    public void addToEnd(QueueElement queueElement) {
        this.nextSongs.add(queueElement);
    }

    @Override
    public List<QueueElement> getNextElements() {
        return Collections.unmodifiableList(this.nextSongs);
    }

    @Override
    public List<QueueElement> getPreviousElements() {
        return Collections.unmodifiableList(this.previousSongs);
    }

    @Override
    public boolean goToNextElement(QueueElement queueElement) {

        if (currentElement == queueElement) {
            return true;
        }

        while (!this.nextSongs.isEmpty()) {
            this.goToNextElement();
            if (currentElement == queueElement) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean goToPreviousElement(QueueElement queueElement) {

        if (currentElement == queueElement) {
            return true;
        }

        while (!this.previousSongs.isEmpty()) {
            this.goToPreviousElement();
            if (currentElement == queueElement) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasNextSongs() {
        return !this.nextSongs.isEmpty();
    }

    @Override
    public boolean hasPreviousSongs() {
        return !this.previousSongs.isEmpty();
    }

    @Override
    public void clear() {
        this.nextSongs.forEach(element -> this.previousSongs.add(0, element));
        this.nextSongs.clear();
    }
}
