package embeds.guess;

import de.kraml.Main;
import music.guess.GuessingGame;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GuessStartEmbed extends EmbedBuilder {

  public GuessStartEmbed() {

    setTitle("Anime Song Ratespiel");
    setDescription("Ihr koennt jetzt den Namen des Songs erraten.");

    addField("/guess Titel", "Damit koennt ihr den Namen des Songs erraten.");
    addField("/reveal", "Damit wird der Song offengelegt.");

    setColor(Main.COLOR);

  }

}
