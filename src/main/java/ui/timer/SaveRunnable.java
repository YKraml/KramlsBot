package ui.timer;

import util.Terminal;
import domain.exceptions.MyOwnException;

public abstract class SaveRunnable implements Runnable {

  @Override
  public final void run() {
    try {
      runSave();
    } catch (Exception e) {
      Terminal.printError(new MyOwnException(() -> "Could not run timer runnable.", e));
    }
  }

  protected abstract void runSave() throws Exception;
}
