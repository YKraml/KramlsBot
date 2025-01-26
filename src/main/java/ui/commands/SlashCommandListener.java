package ui.commands;

import com.google.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import util.Terminal;

public class SlashCommandListener implements SlashCommandCreateListener {

  private final CommandDistributor distributor;

  private final ExecutorService executor;
  private final Terminal terminal;

  @Inject
  public SlashCommandListener(CommandDistributor distributor, ExecutorService executor,
      Terminal terminal) {
    this.distributor = distributor;
    this.executor = executor;
    this.terminal = terminal;
  }

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {
    CompletableFuture.supplyAsync(() -> distributor.distributeCommand(event), executor)
        .thenAccept(answer -> terminal.printLine(answer.getMessage()));

  }
}
