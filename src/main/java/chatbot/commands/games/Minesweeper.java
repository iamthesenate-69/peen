package chatbot.commands.games;

import java.util.LinkedList;
import java.util.Random;

import chatbot.Main.Bot;
import chatbot.commands.util.Command;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Minesweeper extends Command{

	@Override
	public String name() {
		return "ms";
	}

	@Override
	public String description() {
		return "minesweeper game in discord";
	}

	@Override
	public String usage() {
		String p = Bot.prefix + "" + this.name();
		return p+" play <difficulty (0-2 OR easy, medium, hard)> <starting position x> <starting position y>\n" +
		p+" <dig/flag> <pos x> <pos y>\n" +
		p+" quit";
	}

	@Override
	public void execute(MessageReceivedEvent e, String[] args) {
		User u = e.getAuthor();
		MessageChannel c = e.getChannel();


		if (args[1].equals("play") || args[1].equals("p")) {
			if (getIndex(e.getAuthor()) == -1) {

				Bot.minesweeperData.add(new MinesweeperData(createGrid(args[2], args[4], args[3]), u));

				dig(args[4], args[3], e);
				c.sendMessage("Started new Minesweeper game").queue();
			}else
				c.sendMessage("You are already in a game").queue();
		}

		else if (args[1].equals("quit") || args[1].equals("q")) {
			if (getIndex(e.getAuthor()) != -1) {
				Bot.minesweeperData.remove(getIndex(e.getAuthor()));
				c.sendMessage("Quitted Minesweeper game").queue();
			}else
				c.sendMessage("You are not in a game").queue();
		}

		else if (args[1].equals("dig") || args[1].equals("d")) {
			if (getIndex(e.getAuthor()) != -1) 
				dig(args[3], args[2], e);
			else
				c.sendMessage("You are not in a game").queue();
		}

		else if (args[1].equals("flag") || args[1].equals("f")) {
			if (getIndex(e.getAuthor()) != -1) 
				flag(args[3], args[2], e);
			else
				c.sendMessage("You are not in a game").queue();
		}else 
			Functions.printError(e, this);
		

	}





	void flag(String string, String string2, MessageReceivedEvent e) {
		int x = Integer.parseInt(string);
		int y = Integer.parseInt(string2);

		int index = getIndex(e.getAuthor());

		char[][] frontend = Bot.minesweeperData.get(index).frontend;

		if (frontend[x][y] != '.') {
			print(e, frontend, "You can only flag undug tiles");
		}

		if (frontend[x][y] == '.') {
			frontend[x][y] = 'F';
			print(e, frontend, "Flagged (" + y + ", " + x + ")");
		}else if (frontend[x][y] == 'F') {
			frontend[x][y] = convert(Bot.minesweeperData.get(index).grid[x][y]);
			print(e, frontend, "Unflagged (" + y + ", " + x + ")");
		}
	}



	void dig(String X, String Y, MessageReceivedEvent e) {
		int startX = Integer.parseInt(X);
		int startY = Integer.parseInt(Y);

		int index = getIndex(e.getAuthor());

		int[][] grid = Bot.minesweeperData.get(index).grid;
		char[][] frontend = Bot.minesweeperData.get(index).frontend;


		//update tile if not flagged
		if (frontend[startX][startY] == 'F') {
			print(e, frontend, "Tile is flagged");
			return;
		}

		frontend[startX][startY] = convert(grid[startX][startY]);

		if (frontend[startX][startY] == '*') {
			endScreen(e, grid, frontend);
			Bot.minesweeperData.remove(index);
			return;
		}

		else if (grid[startX][startY] > 0) {
			frontend[startX][startY] = convert(grid[startX][startY]);
			Bot.minesweeperData.get(index).update(frontend);
			print(e, frontend, "Dug (" + startY + ", " + startX + ")");
		}

		else 
			print(e, bfs(startX, startY, grid, frontend, index), "Dug (" + startY + ", " + startX + ")");


		//check win
		if (checkWin(grid, frontend)) {
			e.getChannel().sendMessage(new EmbedBuilder().setImage("https://images7.alphacoders.com/658/658706.jpg").setTitle("You win").build()).queue();
			Bot.minesweeperData.remove(index);
		}

	}



	boolean checkWin(int[][] grid, char[][] frontend) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if ((frontend[i][j] == '.' || frontend[i][j] == 'F') && grid[i][j] != -1) {
					return false;
				}
			}
		}
		return true;

	}



	void endScreen(MessageReceivedEvent e, int[][] grid, char[][] frontend) {

		//reveal bombs
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == -1) {
					frontend[i][j] = '*';
				}
			}
		}

		print(e, frontend, "You died");

	}



	char[][] bfs(int startX, int startY, int[][] grid, char[][] frontend, int index) {
		LinkedList<Integer> queueX = new LinkedList<Integer>();
		LinkedList<Integer> queueY = new LinkedList<Integer>();

		queueX.add(startX);
		queueY.add(startY);

		while(!queueX.isEmpty()) {
			int x = queueX.pop();
			int y = queueY.pop();

			for (int i = Math.max(x-1, 0); i < Math.min(x+2, grid.length); i++) {
				for (int j = Math.max(y-1, 0); j < Math.min(y+2, grid.length); j++) {
					if (frontend[i][j] == '.') {
						frontend[i][j] = convert(grid[i][j]);
						if (grid[i][j] == 0) {
							queueX.add(i);
							queueY.add(j);
						}
					}
				}
			}
		}
		Bot.minesweeperData.get(index).update(frontend);
		return frontend;
	}



	char convert(int i) {
		if (i == 0)
			return ' ';
		else if (i == -1)
			return '*';

		return (char)(i+'0');
	}



	void print(MessageReceivedEvent e, char[][] grid, String message) {

		String S = "   ";

		for (int i = 0; i < grid.length; i++) {
			if (i > 9) S += i;
			else S += i + " ";
		}

		S += "\n";

		for (int i = 0; i < grid.length; i++) {
			if (i > 9) S += i;
			else S += i + "  ";
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == -1) S += "* ";
				else if (grid[i][j] == 0) S += " ";
				else S += grid[i][j] + " ";
			}
			S += "\n";
		}
		e.getChannel().sendMessage(new EmbedBuilder().setDescription("```"+S+"```").setTitle(message).build()).queue();

	}

	int[][] createGrid(String size, String X, String Y) {
		int n = 24;
		int b = 99;
		if (size.equals("e") || size.equals("easy") || size.equals("0")) {
			n = 9;
			b = 10;
		}
		else if (size.equals("m") || size.equals("medium") || size.equals("1")) {
			n = 16;
			b = 40;
		}

		int startX = Integer.parseInt(X);
		int startY = Integer.parseInt(Y);

		//check bounds
		if (startX < 0 || startX >= n || startY < 0 || startY >= n)
			throw new NumberFormatException();

		//place mines
		int[][] grid = placeMines(n, b, startX, startY);

		//numbers
		grid = addNumbers(grid);

		return grid;
	}



	int[][] placeMines(int n, int b, int startX, int startY) {
		int[][] grid = new int[n][n];
		int c = 0;
		Random r = new Random();
		while(c < b) {
			int x = r.nextInt(n);
			int y = r.nextInt(n);

			if (grid[x][y] == 0 && isEmptyNearby(startX, startY, x, y)) {
				grid[x][y] = -1;
				c++;
			}
		}
		return grid;
	}



	boolean isEmptyNearby(int startX, int startY, int x, int y) {
		for (int i = x-1; i < x+2; i++) 
			for (int j = y-1; j < y+2; j++) 
				if (i == startX && j == startY)
					return false;
		return true;
	}



	int[][] addNumbers(int[][] grid) {
		for (int i = 0; i < grid.length; i++) 
			for (int j = 0; j < grid.length; j++) 
				if (grid[i][j] != -1) 
					grid[i][j] = getAdjMines(i, j, grid);
		return grid;
	}



	int getAdjMines(int x, int y, int[][] grid) {
		int mines = 0;
		for (int i = Math.max(x-1, 0); i < Math.min(x+2, grid.length); i++) 
			for (int j = Math.max(y-1, 0); j < Math.min(y+2, grid.length); j++) 
				if (grid[i][j] == -1) 
					mines++;
		return mines;
	}



	int getIndex(User user) {
		for (int i = 0; i < Bot.minesweeperData.size(); i++) {
			if (Bot.minesweeperData.get(i).containUser(user)) {
				return i;
			}
		}
		return -1;
	}



	public class MinesweeperData {
		int[][] grid;
		char[][] frontend;
		User u;
		MinesweeperData(int[][] _grid, User _u) {
			grid = _grid;
			u = _u;
			Initialize();
		}

		void Initialize() {
			frontend = new char[grid.length][grid.length];
			for (int i = 0; i < frontend.length; i++) {
				for (int j = 0; j < frontend.length; j++) {
					frontend[i][j] = '.';
				}
			}
		}

		public boolean containUser(User user) {
			if (user.equals(u)) 
				return true;

			return false;
		}

		public void update(char[][] newfrontend) {
			frontend = newfrontend;
		}
	}







}
