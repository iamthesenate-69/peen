package chatbot.commands.moderation;

import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

public class Ban extends Command {

	@Override
	public String name() {
		return "ban";
	}

	@Override
	public String description() {
		return "bans mentioned member (must have ban members perms)";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + this.name();
		return p + " <mention> [reason]";
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {

		if (Functions.checkPerms(e, Permission.BAN_MEMBERS))
			return;

		Member member = e.getMessage().getMentionedMembers().get(0);
		
		if (!e.getMessage().getMentionedMembers().get(0).getAsMention().equals("<@"+args[1].substring(3))) {
			Functions.printError(e, this);
			return;
		}
		
		//gets the ban message
		String message = "";
		for (int i = 2; i < args.length; i++) 
			message = message + " " + args[i];

		try {
			e.getGuild().ban(member, 0, message).queue(success -> {	//catch errors
				e.getChannel().sendMessage("Banned " + member.getEffectiveName()).queue();
			}, error ->{
				e.getChannel().sendMessage("Unable to ban " + member.getEffectiveName()).queue();
			});
		}catch (HierarchyException ex) {
			e.getChannel().sendMessage("Unable to ban " + member.getEffectiveName()).queue();
		}


	}


}
