package ui.commands.utility;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.messages.messages.IpMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class IpAddress extends ACommand {

    private final static String[] URLS = {"http://checkip.amazonaws.com/",
            "https://ipv4.icanhazip.com/", "http://myexternalip.com/raw", "http://ipecho.net/plain",
            "http://www.trackip.net/ip"};

    private final MessageSender messageSender;

    @Inject
    public IpAddress(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public String getName() {
        return "ip";
    }

    @Override
    public String getDescription() {
        return "Jemand hat sich die Ip Adresse angeschaut.";
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return Collections.emptyList();
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        String ip = getIpAddress();
        messageSender.send(new IpMessage(ip), channel);
        return new Answer("Hier die IP Adresse");
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die IP nicht herausfinden.";
    }

    private String getIpAddress() throws MyOwnException {

        for (String urlString : URLS) {
            try {
                return new BufferedReader(new InputStreamReader(new URL(urlString).openStream(),
                        StandardCharsets.UTF_8)).readLine();
            } catch (IOException ignore) {
                //Do nothing
            }

        }
        throw new MyOwnException(() -> "Could not get ip from any provider", null);
    }
}
