package chatbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Sans extends Execute {

	public static int sans() {
		return 420;
	}
  
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		e.getChannel().sendMessage("420").queue();
		
	}
}
