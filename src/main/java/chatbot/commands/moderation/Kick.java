package chatbot.commands.moderation;

import chatbot.commands.util.Functions;
import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Kick extends Command {

	@Override
	public String name() {
		return "kick";
	}

	@Override
	public String description() {
		return "kicks mentioned member (must have kick members perms)";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + this.name();
		return p + " <mention>";
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {

		if (Functions.checkPerms(e, Permission.KICK_MEMBERS))
			return;

		Member member = e.getMessage().getMentionedMembers().get(0);
		
		if (!e.getMessage().getMentionedMembers().get(0).getAsMention().equals("<@"+args[1].substring(3))) {
			Functions.printError(e, this);
			return;
		}
		
		try {
			e.getGuild().kick(member).queue(success -> {	//catch errors
				e.getChannel().sendMessage("Kicked " + member.getEffectiveName()).queue();
			}, error ->{
				e.getChannel().sendMessage("Unable to kick " + member.getEffectiveName()).queue();
			});
		}
		catch (HierarchyException ex) {
			e.getChannel().sendMessage("Unable to kick " + member.getEffectiveName()).queue();
		}

	}

}
