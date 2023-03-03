package waifu.loader;

import exceptions.MyOwnException;
import javax.inject.Inject;
import javax.inject.Singleton;
import waifu.model.Player;
import waifu.model.Rarities;
import waifu.model.Stats;
import waifu.model.Waifu;
import waifu.sql.SQLCommandExecutor;
import waifu.sql.commands.battle_waifu.DeleteBattleWaifu;
import waifu.sql.commands.character.InsertCharacterOrIgnore;
import waifu.sql.commands.character.SelectCharacterByWaifuId;
import waifu.sql.commands.group_waifu.DeleteWaifuFromAllGroups;
import waifu.sql.commands.team_fighter.DeleteTeamFighter;
import waifu.sql.commands.waifu.DeleteWaifu;
import waifu.sql.commands.waifu.InsertWaifuOrUpdate;
import waifu.sql.commands.waifu.SelectWaifuById;
import waifu.sql.commands.waifu.SelectWaifuJoinedCharacter;
import waifu.sql.entry.CharacterEntrySet;
import waifu.sql.entry.WaifuCharacterEntrySet;
import waifu.sql.entry.WaifuEntrySet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class WaifuLoaderSql implements WaifuLoader {

  private final List<Waifu> waifuCache;
  private final SQLCommandExecutor sqlCommandExecutor;

  @Inject
  public WaifuLoaderSql(SQLCommandExecutor sqlCommandExecutor) {
    this.sqlCommandExecutor = sqlCommandExecutor;
    waifuCache = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void saveWaifu(Waifu waifu, Player player) throws MyOwnException {
    sqlCommandExecutor.execute(new InsertCharacterOrIgnore(waifu));
    sqlCommandExecutor.execute(new InsertWaifuOrUpdate(player, waifu));
  }

  @Override
  public List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException {
    List<Waifu> waifus = new ArrayList<>();

    WaifuCharacterEntrySet entries = sqlCommandExecutor.execute(new SelectWaifuJoinedCharacter(
        player.getId()));
    entries.forEach(e -> waifus.add(
        new Waifu(e.getId(), e.getIdMal(), e.getName(), e.getAnimeName(), e.getUrl(),
            e.getImageUrl(),
            new Stats(Rarities.valueOf(e.getRarity().toUpperCase()), e.getLevel(), e.getStarLevel(),
                e.getXp(), e.getBaseHp(), e.getBaseAtt(), e.getBaseDef(), e.getBaseInit()))));

    waifuCache.addAll(waifus);
    return waifus;
  }

  @Override
  public Optional<Waifu> getWaifuById(String id) throws MyOwnException {

    synchronized (this.waifuCache) {
      for (Waifu waifu : this.waifuCache) {
        if (waifu.getId().equals(id)) {
          return Optional.of(waifu);
        }
      }
    }

    Optional<Waifu> waifuOptional = Optional.empty();

    CharacterEntrySet characterEntrySet = sqlCommandExecutor.execute(new SelectCharacterByWaifuId(id));
    Optional<CharacterEntrySet.CharacterEntry> characterEntryOptional = characterEntrySet.getFirst();

    WaifuEntrySet waifuEntrySet = sqlCommandExecutor.execute(new SelectWaifuById(id));
    Optional<WaifuEntrySet.WaifuEntry> waifuEntryOptional = waifuEntrySet.getFirst();

    if (characterEntryOptional.isPresent() && waifuEntryOptional.isPresent()) {
      waifuOptional = Optional.of(
          createWaifu(waifuEntryOptional.get(), characterEntryOptional.get()));
    }

    return waifuOptional;
  }

  @Override
  public void deleteWaifu(Waifu waifu, Player player) throws MyOwnException {

    if (!player.getWaifuList().isEmpty() && player.getBattleWaifu().isPresent()
        && player.getBattleWaifu().get().equals(waifu)) {
      player.setBattleWaifu(player.getWaifuList().get(0));
    }

    this.waifuCache.remove(waifu);
    player.deleteWaifu(waifu);

    sqlCommandExecutor.execute(new DeleteBattleWaifu(waifu));
    sqlCommandExecutor.execute(new DeleteWaifuFromAllGroups(waifu));
    sqlCommandExecutor.execute(new DeleteTeamFighter(waifu));
    sqlCommandExecutor.execute(new DeleteWaifu(waifu));
  }

  private Waifu createWaifu(WaifuEntrySet.WaifuEntry waifuEntry,
      CharacterEntrySet.CharacterEntry characterEntry) {
    Stats stats = new Stats(Rarities.valueOf(waifuEntry.getRarity().toUpperCase()),
        waifuEntry.getLevel(), waifuEntry.getStarLevel(), waifuEntry.getXp(),
        waifuEntry.getBaseHp(), waifuEntry.getBaseAtt(), waifuEntry.getBaseDef(),
        waifuEntry.getBaseInit());
    return new Waifu(waifuEntry.getId(), characterEntry.getIdMal(), characterEntry.getName(),
        characterEntry.getAnimeName(), characterEntry.getUrl(), waifuEntry.getImageUrl(), stats);
  }
}