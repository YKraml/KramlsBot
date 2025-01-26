package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import logic.timer.DungeonTickRunnable;
import logic.timer.GivePointsRunnable;
import logic.timer.SpawnWaifuRunnable;
import org.javacord.api.DiscordApi;
import ui.commands.SlashCommandListener;
import ui.join.JoinListener;


public class Main {

  public static void main(String[] args) {
    String token = args[0];
    String ip = args[1];
    String userName = args[2];
    String password = args[3];

    Injector injector = Guice.createInjector(new GuiceModule(userName, password, ip, token));

    SlashCommandListener slashCommandListener = injector.getInstance(SlashCommandListener.class);
    JoinListener joinListener = injector.getInstance(JoinListener.class);

    SpawnWaifuRunnable spawnWaifuRunnable = injector.getInstance(SpawnWaifuRunnable.class);
    GivePointsRunnable givePointsRunnable = injector.getInstance(GivePointsRunnable.class);
    DungeonTickRunnable dungeonTickRunnable = injector.getInstance(DungeonTickRunnable.class);

    DiscordApi discordApi = injector.getInstance(DiscordApi.class);
    discordApi.addServerVoiceChannelMemberJoinListener(joinListener);
    discordApi.addSlashCommandCreateListener(slashCommandListener);

    ScheduledExecutorService scheduler = injector.getInstance(ScheduledExecutorService.class);
    scheduler.schedule(spawnWaifuRunnable, 5, TimeUnit.MINUTES);
    int timeInMinutes = givePointsRunnable.getTimeInMinutes();
    scheduler.scheduleAtFixedRate(givePointsRunnable, 5, timeInMinutes, TimeUnit.MINUTES);
    scheduler.scheduleAtFixedRate(dungeonTickRunnable, 5, 5, TimeUnit.MINUTES);
  }


}
