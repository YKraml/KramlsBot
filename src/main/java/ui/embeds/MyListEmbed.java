package ui.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import util.Emojis;
import util.Main;

import java.util.List;

public abstract class MyListEmbed<Type extends DisplayableElement> extends EmbedBuilder {

    private final static int ELEMENTS_PER_PAGE = 10;

    public MyListEmbed(String title, List<Type> displayableElements, int page, boolean useInlineFields) {

        int pagesNo = displayableElements.size() / ELEMENTS_PER_PAGE;
        if (displayableElements.size() % ELEMENTS_PER_PAGE != 0) {
            pagesNo++;
        }

        this.setTitle(title);
        this.setDescription("Seite: " + (page + 1) + " / " + pagesNo);
        this.setColor(Main.COLOR);
        if (!displayableElements.isEmpty()) {
            this.setThumbnail(displayableElements.get(0).getDisplayImageUrl());
        }


        if (pagesNo == 0) {
            this.addField("Leer", "Leer");
            return;
        }

        int correctedPage = (page + pagesNo) % pagesNo;

        for (int i = ELEMENTS_PER_PAGE * correctedPage; i < ELEMENTS_PER_PAGE * correctedPage + ELEMENTS_PER_PAGE; i++) {

            try {
                DisplayableElement displayableElement = displayableElements.get(i);

                String rawTitle = displayableElement.getDisplayTitle();
                String fieldTitle = Emojis.getCountEmojis()[i % ELEMENTS_PER_PAGE] + " " + rawTitle;
                String fieldBody = "Nr. %d | %s".formatted(i, displayableElement.getDisplayBody());

                if (useInlineFields) {
                    this.addInlineField(fieldTitle, fieldBody);
                } else {
                    this.addField(fieldTitle, fieldBody);
                }


            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
    }
}
