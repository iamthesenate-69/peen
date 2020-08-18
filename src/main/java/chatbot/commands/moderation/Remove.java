package chatbot.commands.moderation;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class Remove extends Command {
  
	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		
		if (Functions.checkPerms(e, Permission.MANAGE_ROLES))
			return;
		
		else if (args[1].equals("roles") || args[1].equals("role") || args[1].equals("r"))
			removeRole(e);
		else if (args[1].equals("webhooks") || args[1].equals("webhook") || args[1].equals("w"))
			removeWebhooks(e);
	}

	void removeWebhooks(MessageReceivedEvent e) {
		e.getChannel().sendMessage("Removing Webhooks...").queue();
		for (Webhook w: e.getGuild().retrieveWebhooks().complete()) {
			w.delete().queue();
		}
	}

	void removeRole(MessageReceivedEvent e) {
		Member m = e.getMessage().getMentionedMembers().get(0);
		e.getChannel().sendMessage("Removing Roles from " + m.getEffectiveName() + "...").queue();
		for (Role r: m.getRoles()) {
			e.getGuild().removeRoleFromMember(m, r).queue();
		}
	}

	
}
