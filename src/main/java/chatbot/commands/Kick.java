package chatbot.commands;


import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Kick extends Execute {

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {

		List<Member> members = e.getMessage().getMentionedMembers();

		//if user is not a mod
		if (!e.getMember().getPermissions().contains(Permission.KICK_MEMBERS)) {
			e.getChannel().sendMessage("You do not have BAN_MEMBERS permission").queue();
			return;
		}

		//if bot has no perms
		else if (!e.getGuild().getSelfMember().getPermissions().contains(Permission.KICK_MEMBERS)) {
			e.getChannel().sendMessage("I do not have BAN_MEMBERS permission").queue();
			return;
		}

		//if message did not mention
		else if (members.size() == 0) {
			e.getChannel().sendMessage("You need to mention a member").queue();
			return;
		}

		e.getGuild().kick(members.get(0)).queue(success -> {	//catch errors
			e.getChannel().sendMessage("Kicked " + members.get(0).getEffectiveName()).queue();
		}, error ->{
			e.getChannel().sendMessage("Unable to kick " + members.get(0).getEffectiveName()).queue();
		});

	}

}
