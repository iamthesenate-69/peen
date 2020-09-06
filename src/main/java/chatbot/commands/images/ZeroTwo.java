package chatbot.commands.images;

import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ZeroTwo extends Command {

	@Override
	public String name() {
		return "zerotwo";
	}

	@Override
	public String description() {
		return "best girl in evangelion";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + this.name();
		return p;
	}
	
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		String[] Title = {"Darling",
				"You wanna ride with me, huh",
				"Zero Two"};
		String[] Url = {"https://i.ytimg.com/vi/0sLaYGjGIDo/maxresdefault.jpg",
				"https://vignette.wikia.nocookie.net/darling-in-the-franxx/images/d/d5/02Enraged.png/revision/latest?cb=20191214040153",
				"https://cdn130.picsart.com/293065007004201.jpg?type=webp&to=min&r=640"};
		
		e.getChannel().sendMessage(Functions.build(Title, Url)).queue();
		Functions.setCooldown(e, 2000);
	}

	
}
