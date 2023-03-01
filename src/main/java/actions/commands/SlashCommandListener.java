package actions.commands;

import com.google.inject.Inject;
import de.kraml.Terminal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class SlashCommandListener implements SlashCommandCreateListener {

  private final CommandDistributor distributor;

  private final ExecutorService executor;

  @Inject
  public SlashCommandListener(CommandDistributor distributor, ExecutorService executor) {
    this.distributor = distributor;
    this.executor = executor;
  }

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {
    CompletableFuture.supplyAsync(() -> distributor.distributeCommand(event), executor)
        .thenAccept(answer -> Terminal.printLine(answer.getMessage()));

  }
}
