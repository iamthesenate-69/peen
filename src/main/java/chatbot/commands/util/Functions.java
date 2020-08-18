package chatbot.commands.util;

import java.util.Random;

import chatbot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Functions {
	public static MessageEmbed build(String[] title, String[] url) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(title[new Random().nextInt(title.length)]);
		eb.setImage(url[new Random().nextInt(url.length)]);
		return eb.build();
	}
	
	public static void setCooldown(MessageReceivedEvent e, int t) {
		Main.Bot.cooldown.add(new Main.Limiter(e.getAuthor(), System.currentTimeMillis()+t));
	}
	
	public static boolean checkPerms(MessageReceivedEvent e, Permission p) {
		if (e.getMember() == null) {
			e.getChannel().sendMessage("This command can only be used in a Server").queue();
			return true;
		}
		
		if (!e.getMember().hasPermission(p)) {
			e.getChannel().sendMessage("You do not have " + p.getName() + " permission").queue();
			return true;
		}
		else if (!e.getGuild().getSelfMember().hasPermission(p)) {
			e.getChannel().sendMessage("I do not have " + p.getName() + " permission").queue();
			return true;
		}
		return false;
	}
}
