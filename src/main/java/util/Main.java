package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import logic.timer.DungeonTickRunnable;
import logic.timer.GivePointsRunnable;
import logic.timer.SpawnWaifuRunnable;
import org.javacord.api.DiscordApi;
import ui.commands.SlashCommandListener;
import ui.join.JoinListener;

import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    public static final Color COLOR = Color.ORANGE;
    private static DiscordApi discordApi;
    private static String ip;
    private static String userName;
    private static String password;
    private static String token;

    public static void main(String[] args) {

        token = args[0];
        ip = args[1];
        userName = args[2];
        password = args[3];

        Injector injector = Guice.createInjector(new GuiceModule());

        SlashCommandListener slashCommandListener = injector.getInstance(SlashCommandListener.class);
        JoinListener joinListener = injector.getInstance(JoinListener.class);

        SpawnWaifuRunnable spawnWaifuRunnable = injector.getInstance(SpawnWaifuRunnable.class);
        GivePointsRunnable givePointsRunnable = injector.getInstance(GivePointsRunnable.class);
        DungeonTickRunnable dungeonTickRunnable = injector.getInstance(DungeonTickRunnable.class);

        discordApi = injector.getInstance(DiscordApi.class);
        discordApi.addServerVoiceChannelMemberJoinListener(joinListener);
        discordApi.addSlashCommandCreateListener(slashCommandListener);

        ScheduledExecutorService scheduler = injector.getInstance(ScheduledExecutorService.class);
        scheduler.schedule(spawnWaifuRunnable, 5, TimeUnit.MINUTES);
        int timeInMinutes = givePointsRunnable.getTimeInMinutes();
        scheduler.scheduleAtFixedRate(givePointsRunnable, 5, timeInMinutes, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(dungeonTickRunnable, 5, 5, TimeUnit.MINUTES);
    }

    public static DiscordApi getDiscordApi() {
        return discordApi;
    }

    public static String getIp() {
        return ip;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getToken() {
        return token;
    }
}
