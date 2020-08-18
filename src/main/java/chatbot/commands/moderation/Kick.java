package chatbot.commands.moderation;

import chatbot.commands.Command;
import chatbot.commands.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Kick extends Command {

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		if (Functions.checkPerms(e, Permission.KICK_MEMBERS))
			return;
		
		if (e.getMessage().getMentionedMembers().size() == 0) {
			e.getChannel().sendMessage("You need to mention a member").queue();
			return;
		}

		Member member = e.getMessage().getMentionedMembers().get(0);

		e.getGuild().kick(member).queue(success -> {	//catch errors
			e.getChannel().sendMessage("Kicked " + member.getEffectiveName()).queue();
		}, error ->{
			e.getChannel().sendMessage("Unable to kick " + member.getEffectiveName()).queue();
		});

	}

}
