package chatbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

abstract class Execute {
	public abstract void execute(MessageReceivedEvent e, String[] args);
	
}
