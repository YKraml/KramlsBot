package de.kraml;


import actions.commands.ACommand;
import actions.commands.dungeon.CreateDungeon;
import actions.commands.dungeon.DeleteDungeons;
import actions.commands.dungeon.TeamAdd;
import actions.commands.dungeon.TeamCreate;
import actions.commands.dungeon.TeamExpand;
import actions.commands.dungeon.TeamRemove;
import actions.commands.dungeon.TeamRename;
import actions.commands.dungeon.TeamsList;
import actions.commands.gambling.DoubleOrNothing;
import actions.commands.gambling.DoubleOrNothingWithParameter;
import actions.commands.group.GroupAddWaifu;
import actions.commands.group.GroupCreate;
import actions.commands.group.GroupDelete;
import actions.commands.group.GroupRemoveWaifu;
import actions.commands.group.GroupShowList;
import actions.commands.guess.Guess;
import actions.commands.guess.Reveal;
import actions.commands.guess.StartGuessingGame;
import actions.commands.music.Play;
import actions.commands.music.ShowQueue;
import actions.commands.music.Songs;
import actions.commands.music.Stop;
import actions.commands.utility.Disconnect;
import actions.commands.utility.Stats;
import actions.commands.waifu.Claim;
import actions.commands.waifu.Daily;
import actions.commands.waifu.Delete;
import actions.commands.waifu.DeleteFromAnime;
import actions.commands.waifu.DeleteFromTo;
import actions.commands.waifu.Expand;
import actions.commands.waifu.GiveMoney;
import actions.commands.waifu.GiveWaifu;
import actions.commands.waifu.Merge;
import actions.commands.waifu.ShowWaifuList;
import actions.commands.waifu.Spawn;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;
import messages.MessageSender;
import messages.MessageSenderImpl;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import routines.RoutineRunner;
import waifu.loader.DungeonLoader;
import waifu.loader.DungeonLoaderSql;
import waifu.loader.PlayerLoader;
import waifu.loader.PlayerLoaderCached;
import waifu.loader.TeamLoader;
import waifu.loader.TeamLoaderSql;
import waifu.loader.WaifuLoader;
import waifu.loader.WaifuLoaderSql;

public class MyModule extends AbstractModule {


  @Override
  protected void configure() {
    bind(PlayerLoader.class).to(PlayerLoaderCached.class);
    bind(WaifuLoader.class).to(WaifuLoaderSql.class);
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
    return Executors.newFixedThreadPool(16);
  }

  @Provides
  @Singleton
  ScheduledExecutorService provideScheduledExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Provides
  List<ACommand> provideACommands(RoutineRunner routineRunner, Disconnect disconnect,
      DoubleOrNothing doubleOrNothing, DoubleOrNothingWithParameter doubleOrNothingWithParameter,
      StartGuessingGame startGuessingGame, Guess guess, Reveal reveal, Stats stats, Daily daily,
      Spawn spawn, ShowWaifuList showWaifuList, Claim claim, GiveWaifu giveWaifu, Merge merge,
      Expand expand, DeleteFromAnime deleteFromAnime, DeleteFromTo deleteFromTo,
      GiveMoney giveMoney, Delete delete, GroupCreate groupCreate, GroupAddWaifu groupAddWaifu,
      GroupShowList groupShowList, GroupRemoveWaifu groupRemoveWaifu, GroupDelete groupDelete,
      TeamAdd teamAdd, TeamRemove teamRemove, TeamCreate teamCreate, TeamExpand teamExpand,
      TeamsList teamsList, TeamRename teamRename, CreateDungeon createDungeon,
      DeleteDungeons deleteDungeons, Play play, Stop stop, ShowQueue showQueue, Songs songs) {

    List<ACommand> commands = new ArrayList<>();
    commands.add(disconnect);
    commands.add(doubleOrNothing);
    commands.add(doubleOrNothingWithParameter);
    commands.add(startGuessingGame);
    commands.add(guess);
    commands.add(reveal);
    commands.add(stats);
    commands.add(daily);
    commands.add(spawn);
    commands.add(showWaifuList);
    commands.add(claim);
    commands.add(giveWaifu);
    commands.add(merge);
    commands.add(expand);
    commands.add(deleteFromAnime);
    commands.add(deleteFromTo);
    commands.add(giveMoney);
    commands.add(delete);
    commands.add(groupCreate);
    commands.add(groupAddWaifu);
    commands.add(groupShowList);
    commands.add(groupRemoveWaifu);
    commands.add(groupDelete);
    commands.add(teamAdd);
    commands.add(teamRemove);
    commands.add(teamCreate);
    commands.add(teamExpand);
    commands.add(teamsList);
    commands.add(teamRename);
    commands.add(createDungeon);
    commands.add(deleteDungeons);
    commands.add(play);
    commands.add(stop);
    commands.add(showQueue);
    commands.add(songs);

    commands.forEach(abstractCommand -> abstractCommand.setRoutineRunner(routineRunner));

    return commands;
  }
}
