package waifu;

import caller.Jikan;
import caller.anime.AnimeCaller;
import caller.character.CharacterCaller;
import caller.top.TopCaller;
import enums.AnimeSearchQueryOrderBy;
import enums.SearchQuerySort;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotGetAnimeCharactersFromAnime;
import exceptions.messages.CouldNotGetPictures;
import exceptions.messages.CouldNotGetRandomAnimeOnPage;
import exceptions.messages.CouldNotGetRandomTopAnimeCharacter;
import exceptions.messages.CouldNotGetThemeFromAnime;
import exceptions.messages.CouldNotSearchForAnimeTitles;
import exceptions.messages.CouldNotSearchForCharacter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import model.jikan.anime.animeByIdFull.AnimeFullById;
import model.jikan.anime.animeCharacters.AnimeCharacters;
import model.jikan.anime.animeSearch.AnimeSearch;
import model.jikan.anime.animeThemes.AnimeThemes;
import model.jikan.characters.characterFullById.CharacterFullById;
import model.jikan.characters.characterPictures.CharacterPictures;
import model.jikan.characters.charactersSearch.CharactersSearch;
import model.jikan.top.TopCharacters.TopCharacters;

public class JikanFetcher {

  private final TopCaller topCaller;
  private final AnimeCaller animeCaller;
  private final CharacterCaller characterCaller;

  public JikanFetcher() {
    topCaller = Jikan.top();
    animeCaller = Jikan.anime();
    characterCaller = Jikan.characters();
  }

  public CharacterFullById getRandomTopAnimeCharacter() throws MyOwnException {

    Random random = new Random();

    try {

      int page = random.nextInt(100);
      TopCharacters topCharacter = topCaller.characters().page(page).consume().get();

      int index = random.nextInt(topCharacter.getData().size());
      int malId = topCharacter.getData().get(index).getMalId();
      return characterCaller.fullById(malId).consume().get();

    } catch (ExecutionException | InterruptedException e) {
      throw new MyOwnException(new CouldNotGetRandomTopAnimeCharacter(), e);
    }
  }

  public List<String> searchForAnimeTitles(String query) throws MyOwnException {

    try {
      AnimeSearch animeSearch = animeCaller.search().q(query).consume().get();
      List<String> possibleNames = new ArrayList<>();
      animeSearch.getData().forEach(datum -> {
        possibleNames.add(datum.getTitle());
        possibleNames.add(datum.getTitleJapanese());
        possibleNames.add(datum.getTitleEnglish());
        possibleNames.addAll(datum.getTitleSynonyms());
      });
      return possibleNames;

    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotSearchForAnimeTitles(query), e);
    }
  }

  public AnimeFullById getRandomPopularAnimeFromPage(int page) throws MyOwnException {
    try {
      Random r = new Random();
      AnimeSearch animeSearch = animeCaller.search().sort(SearchQuerySort.desc)
          .orderBy(AnimeSearchQueryOrderBy.members).page(page).consume().get();
      Integer malId = animeSearch.getData().get(r.nextInt(animeSearch.getData().size())).getMalId();

      return animeCaller.fullById(malId).consume().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotGetRandomAnimeOnPage(page), e);
    }
  }


  public String parseOpeningName(String animeName, String songName) {
    return animeName + " song " + songName;
  }

  public AnimeCharacters getAnimeCharacters(AnimeFullById anime) throws MyOwnException {
    try {
      Integer malId = anime.getData().getMalId();
      return animeCaller.characters(malId).consume().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotGetAnimeCharactersFromAnime(anime.getData().getTitle()),
          e);
    }
  }

  public AnimeThemes getAnimeThemes(AnimeFullById anime) throws MyOwnException {
    try {
      Integer malId = anime.getData().getMalId();
      return animeCaller.themes(malId).consume().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotGetThemeFromAnime(anime.getData().getTitleEnglish()), e);
    }
  }

  CharactersSearch getCharacterSearch(String guessedName) throws MyOwnException {
    try {
      return characterCaller.search().query(guessedName).consume().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotSearchForCharacter(guessedName), e);
    }
  }

  public String getRandomPictureFrom(String malId) throws MyOwnException {

    try {
      CharacterPictures characterPictures = characterCaller.pictures(Integer.parseInt(malId))
          .consume().get();
      int index = new Random().nextInt(characterPictures.getData().size());
      return characterPictures.getData().get(index).getJpg().getImageUrl();
    } catch (InterruptedException | ExecutionException e) {
      throw new MyOwnException(new CouldNotGetPictures(malId), e);
    }

  }

}
