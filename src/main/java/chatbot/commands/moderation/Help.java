package chatbot.commands.moderation;

import chatbot.Main;
import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help extends Command{
	
	@Override
	public String name() {
		return "help";
	}

	@Override
	public String description() {
		return "helps with commands";
	}

	@Override
	public String usage() {
		StringBuilder sb = new StringBuilder(Bot.prefix + "" + name() + " <commands>\nList of current commands:\n");
		Main.commands.forEach((key,value) -> sb.append(value.name()+", "));
		sb.delete(sb.length()-2, sb.length()-1);
		return sb.toString();
	}
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {		
		String command = args[1];
		Command c = Main.commands.get(command);
		e.getChannel().sendMessage(new EmbedBuilder().setTitle(Bot.prefix + c.name()).setDescription(c.description()+"\n\nUsage:\n"+c.usage()).build()).queue();
	}

}
