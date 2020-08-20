package chatbot.commands.moderation;

import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shutdown extends Command{

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		if (Functions.checkPerms(e, Permission.ADMINISTRATOR))
			return;
		
		e.getChannel().sendMessage("Shutting down...");
		System.exit(1);
	}

}