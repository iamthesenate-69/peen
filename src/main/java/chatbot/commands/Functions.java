package chatbot.commands;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class Functions {
	public static MessageEmbed build(String[] title, String[] url) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(title[new Random().nextInt(title.length)]);
		eb.setImage(url[new Random().nextInt(url.length)]);
		return eb.build();
	}
}
