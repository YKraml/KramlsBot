package logic.timer;

import domain.exceptions.MyOwnException;
import javax.inject.Inject;
import util.Terminal;

public abstract class SaveRunnable implements Runnable {

  private final Terminal terminal;

  @Inject
  protected SaveRunnable(Terminal terminal) {
    this.terminal = terminal;
  }

  @Override
  public final void run() {
    try {
      runSave();
    } catch (Exception e) {
      terminal.printError(new MyOwnException(() -> "Could not run timer runnable.", e));
    }
  }

  protected abstract void runSave() throws Exception;
}
