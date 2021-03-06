package dev.westernpine.ticketbot.command.commands;

import java.awt.Color;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.common.uid.UID;
import dev.westernpine.ticketbot.authenticator.Authenticator;
import dev.westernpine.ticketbot.command.Command;
import dev.westernpine.ticketbot.supports.Ticket;
import dev.westernpine.ticketbot.util.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Close implements Command {
    
    @Override
    public boolean permissible() {
        return false;
    }

    @Override
    public boolean useRole() {
        return false;
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public Permission getPermission() {
        return null;
    }

    @Override
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        
        Ticket ticket = Ticket.from(guild, UID.from(ch.getName()));
        if(ticket == null) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be in a ticket channel to use this command.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        if(!Authenticator.isSupport(guild, user) && !ticket.getOwner().equals(user.getId())) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be the ticket owner, or part of the support staff, to close this ticket.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        ticket.close();
        
    }

}
