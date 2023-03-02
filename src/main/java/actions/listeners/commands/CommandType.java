package actions.listeners.commands;

public enum CommandType {

  ANIME("Anime", "Alles was mit MAL zu tun hat.", true),
  DUNGEON("Dungeon", "Alle Befehle zu den Dungeons", true),
  GAMBLING("Glueksspiel", "Wenn du Lust auf Gluecksspiel hast", true),
  GROUP("Gruppen", "Alle Befehle zu Gruppen", true),
  GUESS("Ratespiel", "Wenn du Anime Songs erraten willst.", true),
  MUSIC("Musik", "Wenn du Lust auf Musik hast.", true),
  OPEN_AI("Open AI", "Alles was mit Open Ai zu tun hat.", true),
  PRIVATE("Privat", "Nur für den Admin gedacht", false),
  UTIL("Allgemein", "Mit diesen Befehlen machst du allgemeine Dinge.", true),
  WAIFU("Waifu", "Alles was mit deinen Waifus zu tun hat.", true);

  private final String title;
  private final String description;
  private final boolean isVisible;

  CommandType(String title, String description, boolean isVisible) {
    this.title = title;
    this.description = description;
    this.isVisible = isVisible;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public boolean isVisible() {
    return isVisible;
  }
}
