package chatbot;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Events extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentRaw();
		TextChannel channel = event.getMessage().getTextChannel();

		if (msg.equalsIgnoreCase("sans")) {
//			int rand = randomNum(0, 6);
			String[] Titles = {"Sans Undertale"};
			String[] Urls = {"https://upload.wikimedia.org/wikipedia/en/0/01/Sans_undertale.jpg"};
			channel.sendMessage(embed(Titles, Urls).build()).queue();
		}

		else if (msg.equals("zero two")) {

		}
		
		else if (msg.equals("zero two")) {

		}
	}
	
	//random number from a to b
	int randomNum(int a, int b) {
		return new Random().nextInt(b - a) + a;
	}

}
