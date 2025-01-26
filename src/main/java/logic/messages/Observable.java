package logic.messages;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

  private final List<Observer> observers;

  protected Observable() {
    observers = new ArrayList<>();
  }

  public final void messageObservers() {
    observers.forEach(Observer::update);
  }

  public void addObserver(Observer observer) {
    observers.add(observer);

    if (observers.size() > 5) {
      observers.remove(0);
    }
  }
}
