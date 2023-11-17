package ui.embeds;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.Emojis;
import util.Main;

public class ExceptionEmbed extends EmbedBuilder {

    public ExceptionEmbed(MyOwnException e) {

        this.setTitle(Emojis.X.getEmoji() + " Error");
        this.setColor(Main.COLOR);

        MyOwnException current = e;
        int counter = 1;
        this.addField(counter + " | " + e.getExceptionMessage().getClassName(),
                current.getExceptionMessage().getContent());

        while (current.getInnerException().isPresent() && current.getInnerException()
                .get() instanceof MyOwnException) {
            current = (MyOwnException) current.getInnerException().get();

            String name = "%d | %s".formatted(++counter, current.getExceptionMessage().getClassName());
            String value = current.getExceptionMessage().getContent();
            this.addField(name, value);

        }

        if (current.getInnerException().isPresent()) {
            Exception exception = current.getInnerException().get();
            String name = "%d | %s".formatted(++counter, exception.getClass().getSimpleName());
            String value = exception.getMessage();
            ExceptionEmbed.this.addField(name, value);
        }


    }
}
