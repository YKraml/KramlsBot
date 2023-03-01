package waifu.loader;

import exceptions.MyOwnException;
import waifu.model.Player;
import waifu.model.Rarities;
import waifu.model.Stats;
import waifu.model.Waifu;
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

public final class WaifuLoaderSql implements WaifuLoader {

  private final List<Waifu> waifuCache;

  public WaifuLoaderSql() {
    waifuCache = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void saveWaifu(Waifu waifu, Player player) throws MyOwnException {
    new InsertCharacterOrIgnore(waifu).executeCommand();
    new InsertWaifuOrUpdate(player, waifu).executeCommand();
  }

  @Override
  public List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException {
    List<Waifu> waifus = new ArrayList<>();

    WaifuCharacterEntrySet entries = new SelectWaifuJoinedCharacter(
        player.getId()).executeCommand();
    entries.forEach(e -> waifus.add(new Waifu(e.getId(), e.getIdMal(), e.getName(), e.getAnimeName(), e.getUrl(),
        e.getImageUrl(), new Stats(Rarities.valueOf(e.getRarity().toUpperCase()), e.getLevel(), e.getStarLevel(), e.getXp(), e.getBaseHp(), e.getBaseAtt(), e.getBaseDef(), e.getBaseInit()))));

    /*
    WaifuEntrySet waifuEntrySet = new SelectWaifusByOwnerId(player).executeCommand();
    for (WaifuEntrySet.WaifuEntry waifuEntry : waifuEntrySet) {
      Optional<CharacterEntrySet.CharacterEntry> characterEntryOptional = new SelectCharacterByWaifuId(
          waifuEntry.getId()).executeCommand().getFirst();
      characterEntryOptional.ifPresent(
          characterEntry -> waifus.add(createWaifu(waifuEntry, characterEntry)));
    }*/

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

    CharacterEntrySet characterEntrySet = new SelectCharacterByWaifuId(id).executeCommand();
    Optional<CharacterEntrySet.CharacterEntry> characterEntryOptional = characterEntrySet.getFirst();

    WaifuEntrySet waifuEntrySet = new SelectWaifuById(id).executeCommand();
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

    new DeleteBattleWaifu(waifu).executeCommand();
    new DeleteWaifuFromAllGroups(waifu).executeCommand();
    new DeleteTeamFighter(waifu).executeCommand();
    new DeleteWaifu(waifu).executeCommand();
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