package chatbot.commands;

import java.util.Random;

import chatbot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
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
		Main.Bot.Timer.add(new Main.Limiter(e.getMember(), t));
	}
}
