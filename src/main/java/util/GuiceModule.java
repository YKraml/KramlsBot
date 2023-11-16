package util;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import database.sql.ConnectionPool;
import logic.routines.RoutineRunner;
import logic.waifu.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import ui.commands.ACommand;
import ui.commands.dungeon.CreateDungeon;
import ui.commands.dungeon.DeleteDungeons;
import ui.commands.gambling.DoubleOrNothing;
import ui.commands.gambling.DoubleOrNothingWithParameter;
import ui.commands.group.*;
import ui.commands.guess.Guess;
import ui.commands.guess.Reveal;
import ui.commands.guess.StartGuessingGame;
import ui.commands.music.Play;
import ui.commands.music.ShowQueue;
import ui.commands.music.Songs;
import ui.commands.music.Stop;
import ui.commands.teams.*;
import ui.commands.utility.Disconnect;
import ui.commands.utility.IpAddress;
import ui.commands.utility.Stats;
import ui.commands.waifu.*;
import ui.messages.MessageSender;
import ui.messages.MessageSenderImpl;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerLoader.class).to(PlayerLoaderCached.class);
        bind(WaifuLoader.class).to(WaifuLoaderCached.class);
        bind(DungeonLoader.class).to(DungeonLoaderSql.class);
        bind(TeamLoader.class).to(TeamLoaderSql.class);
        bind(MessageSender.class).to(MessageSenderImpl.class);
    }

    @Provides
    @Singleton
    DiscordApi provideDiscordApi() {
        DiscordApi discordApi = new DiscordApiBuilder().setToken(Main.getToken()).login().join();
        discordApi.updateActivity("Jetzt mit Slash-commands!");
        return discordApi;
    }

    @Provides
    @Singleton
    ExecutorService provideExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    ScheduledExecutorService provideScheduledExecutorService() {
        return Executors.newScheduledThreadPool(4);
    }

    @Provides
    @Singleton
    ConnectionPool provideConnectionPool() {
        String url = "jdbc:mysql://%s/KRAMLSBOT?serverTimezone=Europe/Berlin&useSSL=false&allowPublicKeyRetrieval=true".formatted(
                Main.getIp());
        String userName = Main.getUserName();
        String password = Main.getPassword();
        return new ConnectionPool(url, userName, password);
    }

    @Provides
    @Singleton
    List<ACommand> provideACommands(RoutineRunner routineRunner, Disconnect disconnect,
                                    DoubleOrNothing doubleOrNothing, DoubleOrNothingWithParameter doubleOrNothingWithParameter,
                                    StartGuessingGame startGuessingGame, Guess guess, Reveal reveal, Stats stats, Daily daily,
                                    Spawn spawn, ShowWaifuList showWaifuList, Claim claim, GiveWaifu giveWaifu, Merge merge,
                                    Expand expand, DeleteFromAnime deleteFromAnime, DeleteFromTo deleteFromTo,
                                    GiveMoney giveMoney, Delete delete, GroupCreate groupCreate, GroupAddWaifu groupAddWaifu,
                                    GroupShowList groupShowList, GroupRemoveWaifu groupRemoveWaifu, GroupDelete groupDelete,
                                    TeamAdd teamAdd, TeamRemove teamRemove, TeamCreate teamCreate, TeamExpand teamExpand,
                                    TeamsList teamsList, TeamRename teamRename, CreateDungeon createDungeon,
                                    DeleteDungeons deleteDungeons, Play play, Stop stop, ShowQueue showQueue, Songs songs,
                                    Fight fight, IpAddress ip) {

        List<ACommand> commands = new ArrayList<>(
                List.of(disconnect, doubleOrNothing, doubleOrNothingWithParameter, startGuessingGame, guess,
                        reveal, stats, daily, spawn, showWaifuList, claim, giveWaifu, merge, expand,
                        deleteFromAnime, deleteFromTo, giveMoney, delete, groupCreate, groupAddWaifu,
                        groupShowList, groupRemoveWaifu, groupDelete, teamAdd, teamRemove, teamCreate,
                        teamExpand, teamsList, teamRename, createDungeon, deleteDungeons, play, stop, showQueue,
                        songs, fight, ip));

        commands.forEach(abstractCommand -> abstractCommand.setRoutineRunner(routineRunner));

        return commands;
    }
}
