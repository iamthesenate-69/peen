package chatbot.commands.util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {
	public abstract void execute(MessageReceivedEvent e, String[] args);
	
}