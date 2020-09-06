package chatbot.commands.moderation;

import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shutdown extends Command{
	
	@Override
	public String name() {
		return "shutdown";
	}

	@Override
	public String description() {
		return "terminates the bot (must be owner of the guild)";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + this.name();
		return p;
	}
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		if (!e.getMember().isOwner()) {
			e.getChannel().sendMessage("You must be owner to do that").queue();
			return;
		}
		e.getChannel().sendMessage("Shutting down...").queue();
		System.exit(1);
	}

}
