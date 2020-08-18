package chatbot.commands.moderation;

import chatbot.commands.Command;
import chatbot.commands.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ban extends Command {

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {

		if (Functions.checkPerms(e, Permission.BAN_MEMBERS))
			return;


		if (e.getMessage().getMentionedMembers().size() == 0) {
			e.getChannel().sendMessage("You need to mention a member").queue();
			return;
		}

		Member member = e.getMessage().getMentionedMembers().get(0);

		//gets the ban message
		String message = "";
		for (int i = 2; i < args.length; i++) 
			message = message + " " + args[i];
		

		e.getGuild().ban(member, 0, message).queue(success -> {	//catch errors
			e.getChannel().sendMessage("Banned " + member.getEffectiveName()).queue();
		}, error ->{
			e.getChannel().sendMessage("Unable to ban " + member.getEffectiveName()).queue();
		});


	}

}
