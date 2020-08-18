package chatbot.commands.images;

import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Sans extends Command {
  
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		String[] Title = {"Sans Undertale",
				"Nothin' Personal Kid",
				"Bad Time",
				"welp"};
		String[] Url = {"https://upload.wikimedia.org/wikipedia/en/0/01/Sans_undertale.jpg",
				"https://images.nintendolife.com/2e8fef0221f89/sans-undertale.original.jpg",
				"https://i.ytimg.com/vi/Ltwc9FVlnng/maxresdefault.jpg",
				"https://thumbs.gfycat.com/BitesizedDeafeningFlounder-small.gif",
				"https://thumbs.gfycat.com/EnchantedWelcomeBear-size_restricted.gif"};
		
		e.getChannel().sendMessage(Functions.build(Title, Url)).queue();
		Functions.setCooldown(e, 2000);
	}

	
}
