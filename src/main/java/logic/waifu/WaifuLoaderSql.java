package logic.waifu;

import database.sql.SQLCommandExecutor;
import database.sql.commands.character.InsertCharacterOrIgnore;
import database.sql.commands.character.SelectCharacterByWaifuId;
import database.sql.commands.group_waifu.DeleteWaifuFromAllGroups;
import database.sql.commands.team_fighter.DeleteTeamFighter;
import database.sql.commands.waifu.DeleteWaifu;
import database.sql.commands.waifu.InsertWaifuOrUpdate;
import database.sql.commands.waifu.SelectWaifuById;
import database.sql.commands.waifu.SelectWaifuJoinedCharacter;
import database.sql.entry.CharacterEntrySet;
import database.sql.entry.WaifuCharacterEntrySet;
import database.sql.entry.WaifuCharacterEntrySet.WaifuCharacterEntry;
import database.sql.entry.WaifuEntrySet;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Rarities;
import domain.waifu.Stats;
import domain.waifu.Waifu;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public final class WaifuLoaderSql implements WaifuLoader {

    private final SQLCommandExecutor sqlCommandExecutor;

    @Inject
    public WaifuLoaderSql(SQLCommandExecutor sqlCommandExecutor) {
        this.sqlCommandExecutor = sqlCommandExecutor;
    }

    @Override
    public void saveWaifu(Waifu waifu, Player player) throws MyOwnException {
        sqlCommandExecutor.execute(new InsertCharacterOrIgnore(waifu));
        sqlCommandExecutor.execute(new InsertWaifuOrUpdate(player, waifu));
    }

    @Override
    public List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException {
        List<Waifu> waifus = new ArrayList<>();

        WaifuCharacterEntrySet entries = sqlCommandExecutor.execute(
                new SelectWaifuJoinedCharacter(player.getId()));
        entries.forEach(entry -> waifus.add(createWaifu(entry)));

        return waifus;
    }

    @Override
    public Optional<Waifu> getWaifuById(String id) throws MyOwnException {

        Optional<Waifu> waifuOptional = Optional.empty();

        CharacterEntrySet characterEntrySet = sqlCommandExecutor.execute(
                new SelectCharacterByWaifuId(id));
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
        player.deleteWaifu(waifu);
        sqlCommandExecutor.execute(new DeleteWaifuFromAllGroups(waifu));
        sqlCommandExecutor.execute(new DeleteTeamFighter(waifu));
        sqlCommandExecutor.execute(new DeleteWaifu(waifu));
    }

    private Waifu createWaifu(WaifuEntrySet.WaifuEntry waifuEntry,
                              CharacterEntrySet.CharacterEntry characterEntry) {
        Stats stats = new Stats(Rarities.valueOf(waifuEntry.getRarity().toUpperCase()),
                waifuEntry.getStarLevel(), waifuEntry.getXp(), waifuEntry.getBaseHp(),
                waifuEntry.getBaseAtt(), waifuEntry.getBaseDef(), waifuEntry.getBaseInit());
        return new Waifu(waifuEntry.getId(), characterEntry.getIdMal(), characterEntry.getName(),
                characterEntry.getAnimeName(), characterEntry.getUrl(), waifuEntry.getImageUrl(), stats);
    }

    private Waifu createWaifu(WaifuCharacterEntry entry) {
        return new Waifu(entry.getId(), entry.getIdMal(), entry.getName(), entry.getAnimeName(),
                entry.getUrl(), entry.getImageUrl(),
                new Stats(Rarities.valueOf(entry.getRarity().toUpperCase()), entry.getStarLevel(),
                        entry.getXp(), entry.getBaseHp(), entry.getBaseAtt(), entry.getBaseDef(),
                        entry.getBaseInit()));
    }
}