package actions.commands;

/**
 * Die Klasse {@link Answer} stellt eine Antwort eines Befehls dar.
 * Jede {@link Answer} hat einen Typ und eine Nachricht, die dem Nutzer präsentiert werden soll.
 *
 * @author Yannick Kraml
 * @version 1.0
 */
public final class Answer {

    private final String message;

    /**
     * Konstruktor der Klasse {@link Answer}.
     *
     * @param message Nachricht der Antwort.
     */
    public Answer(String message) {
        this.message = message;
    }

    /**
     * @return Nachricht der Antwort.
     */
    public String getMessage() {
        return message;
    }
}
