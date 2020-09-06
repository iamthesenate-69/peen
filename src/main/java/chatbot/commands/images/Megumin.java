package chatbot.commands.images;

import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Megumin extends Command {
	
	@Override
	public String name() {
		return "megumin";
	}

	@Override
	public String description() {
		return "EXPLOSION~!";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + this.name();
		return p;
	}
	
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		String[] Title = {"bakuretsu bakuretsu la la la",
				"Waga na wa Megumin",
				"Explosion!"};
		String[] Url = {"https://repository-images.githubusercontent.com/56785718/dae38380-9d87-11e9-855f-02f50aa93278",
				"https://vignette.wikia.nocookie.net/konosuba/images/3/3f/Megumin-anime.png/revision/latest?cb=20180328143334",
				"https://i.ytimg.com/vi/jar1LTxxAeM/maxresdefault.jpg"};
		
		e.getChannel().sendMessage(Functions.build(Title, Url)).queue();
		Functions.setCooldown(e, 2000);
	}

	
}
