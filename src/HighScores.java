import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class HighScores {
	String fileName;
	ArrayList<String> scores;
	
	private File file;
	private int playerScore;
	private String name;
	
	public HighScores(String name, int playerScore,String fileName){
		this.name = name;
		this.playerScore = playerScore;
		addScores(name, playerScore,fileName);
		this.fileName = fileName;
		scores = new ArrayList<String>();
		
	}
	public void addScores(String name, int playerScore,String fileName){
		this.fileName = fileName;
		try {
			PrintWriter write = new PrintWriter(file);
			write.print(name +","+playerScore);
			write.close();
			
		} catch (IOException e) {
			
		} catch (NullPointerException nPE){
			
		}
	}
	public void readScores(){
		try {
			Scanner sc = new Scanner(fileName);
			while (sc.hasNextLine() && sc.nextLine() != null){
				scores.add(sc.nextLine());
			}
			sc.close();
		} catch(NullPointerException nPE){
			scores.add("File not found! Your scores are: \n"
					+ name + " " + playerScore + "\n"+
					"Congratulations!");
		} catch(NoSuchElementException nSEE){
			scores.add("File not found! Your scores are: \n"
					+ name + " " + playerScore + "\n"+
					"Congratulations!");	
		} catch (Exception e){
			scores.add("Something went wrong! I blame the jar file.");
		}	
	}
	public String convertString(){
		String result = "";
		if (scores.isEmpty()){
			JOptionPane.showMessageDialog(null, "File was empty, no high scores yet!");
		}
		for (int i=0; i<scores.size();i++){
			result += scores.get(i);
		}
		return result;
		
	}
}
