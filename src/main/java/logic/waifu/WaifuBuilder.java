package logic.waifu;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetOriginFromCharachter;
import domain.waifu.Rarities;
import domain.waifu.Stats;
import domain.waifu.Waifu;
import java.util.UUID;
import javax.inject.Singleton;
import model.jikan.characters.characterFullById.CharacterFullById;

@Singleton
public final class WaifuBuilder {

  public static final double MIN_STAT_RATIO = 0.2;
  private final JikanFetcher jikanFetcher;

  @Inject
  public WaifuBuilder(JikanFetcher jikanFetcher) {
    this.jikanFetcher = jikanFetcher;
  }


  public synchronized Waifu createRandomWaifu() throws MyOwnException {
    CharacterFullById animeCharacter = jikanFetcher.getRandomTopAnimeCharacter();

    String defaultImageUrl = animeCharacter.getData().getImages().getJpg().getImageUrl();
    String malId = String.valueOf(animeCharacter.getData().getMalId());
    String imageUrl = jikanFetcher.getRandomPictureByMalId(malId).orElse(defaultImageUrl);

    return createWaifu(animeCharacter, imageUrl, 1);
  }

  public synchronized Waifu createBatleWaifu(int level) throws MyOwnException {
    CharacterFullById character = jikanFetcher.getRandomTopAnimeCharacter();
    String imageUrl = character.getData().getImages().getJpg().getImageUrl();
    return createWaifu(character, imageUrl, level);
  }

  private Waifu createWaifu(CharacterFullById animeCharacter, String imageUrl, int level)
      throws MyOwnException {
    return new Waifu(UUID.randomUUID().toString(), animeCharacter.getData().getMalId(),
        animeCharacter.getData().getName(), getOrigin(animeCharacter),
        animeCharacter.getData().getUrl(), imageUrl,
        createStats(level, Rarities.getRandomRarity()));
  }

  private Stats createStats(int level, Rarities rarity) {
    int starLevel = 0;

    int xp = (int) Math.pow(level, 3);

    double rBaseHp = Math.max(Math.random(), MIN_STAT_RATIO);
    double rBaseAtt = Math.max(Math.random(), MIN_STAT_RATIO);
    double rBaseDef = Math.max(Math.random(), MIN_STAT_RATIO);
    double rBaseInit = Math.max(Math.random(), MIN_STAT_RATIO);

    double sum = rBaseHp + rBaseAtt + rBaseDef + rBaseInit;

    rBaseHp = rBaseHp / sum;
    rBaseAtt = rBaseAtt / sum;
    rBaseDef = rBaseDef / sum;
    rBaseInit = rBaseInit / sum;
    int baseHp = (int) (rarity.getStatsSum() * rBaseHp);
    int baseAtt = (int) (rarity.getStatsSum() * rBaseAtt);
    int baseDef = (int) (rarity.getStatsSum() * rBaseDef);
    int baseInit = (int) (rarity.getStatsSum() * rBaseInit);

    return new Stats(rarity, starLevel, xp, baseHp, baseAtt, baseDef, baseInit);
  }

  private String getOrigin(CharacterFullById character) throws MyOwnException {
    String animeName;
    boolean hasAnime = !character.getData().getAnime().isEmpty();
    boolean hasManga = !character.getData().getManga().isEmpty();
    if (hasAnime) {
      animeName = character.getData().getAnime().iterator().next().getAnime().getTitle();
    } else if (hasManga) {
      animeName = character.getData().getManga().iterator().next().getManga().getTitle();
    } else {
      throw new MyOwnException(new CouldNotGetOriginFromCharachter(character.getData().getName()),
          null);
    }
    return animeName;
  }
}
