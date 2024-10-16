import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GuessTheWordPanel extends VBox {

	private Button newGame;
	private Button newWord;
	private TextField inputWord;
	private Label labelPoint;
	private Label labelInstructions;
	private Label labelDefinition;
	private Label labelSolution;
	private Label labelPassedUserAbilities;
	private GuessTheWordUser user;
	private ArrayList<String> solutionList;
	private ArrayList<String> definitionList;
	private ArrayList<String> usedDefinition;
	private ArrayList<Integer> passedGames; 
	private Random r;
	private int i;
	private String currentSol;
	private String currentDef;
	private String maskedSol;
	private String passGames;
	private ChoiceBox<String> diffChoiceBox;

	
	
	
	public GuessTheWordPanel () {
		
		
		r = new Random();
		user = new GuessTheWordUser();
		passedGames = new ArrayList<>();
		String[] difficulties = {"standard", "mid", "max"};
		
		diffChoiceBox = new ChoiceBox<String>();
		diffChoiceBox.getItems().addAll(difficulties);
		diffChoiceBox.getSelectionModel().selectFirst();
			
		labelInstructions = new Label ("Instruction: To start the game click the button \"New game\". You have to guess the right word. If you guess the right ansewer you will get a certain number of poit which are equals to the number of not showed characters \"?\", then click the button \"New word\". Else if you write the wrong word it will appire a letter instead of a character \"?\" to help you. That will happend until the answer is complete visible. At this point you can get another word to guess clicking the button \"New Word\". When you will achive 12 point the game is goint to end and you will be able to see your user ability (less is better). Also you can choose the difficult through the choice box below(standard, mid or max) that is equal to the length's words (5, 6 or 7). You can set it at any point of the game. If the words of difficulty x are finished change difficult to go on. Good Luck!");
		labelInstructions.setWrapText(true);
		
		newGame = new Button("New Game");
		newGame.setPrefWidth(100);
		newGame.setAlignment(Pos.CENTER);
		newGame.setOnAction(this::newGame);
		
		newWord = new Button("New Word");
		newWord.setPrefWidth(100);
		newWord.setAlignment(Pos.CENTER);
		newWord.setOnAction(this::newWord);

		labelDefinition = new Label("");
		labelDefinition .setAlignment(Pos.CENTER);
		labelDefinition .setPrefWidth(300);
		labelDefinition .setWrapText(true);
		
		labelSolution = new Label("");
		labelSolution.setAlignment(Pos.CENTER);
		labelSolution.setPrefWidth(300);
		labelSolution.setWrapText(true);
			
		inputWord = new TextField("");
		inputWord.setPrefWidth(200);
		inputWord.setAlignment(Pos.CENTER);
		inputWord.setOnAction(this::checkGuess);
		
		labelPoint = new Label("");
		labelPoint.setAlignment(Pos.CENTER);
		labelPoint.setPrefWidth(100);
		labelPoint.setWrapText(true);
		
		labelPassedUserAbilities = new Label("");
		labelPassedUserAbilities.setAlignment(Pos.CENTER);
		labelPassedUserAbilities.setPrefWidth(300);
		labelPassedUserAbilities.setWrapText(true);
		
		HBox diffBox = new HBox(diffChoiceBox);
		diffBox.setAlignment(Pos.CENTER);
		diffBox.setSpacing(15);
		
		HBox inst = new HBox(labelInstructions);
		inst.setAlignment(Pos.CENTER);
		inst.setSpacing(15);
		
		HBox defBox = new HBox(labelDefinition);
		defBox.setAlignment(Pos.CENTER);
		defBox.setSpacing(15);
		
		HBox solBox = new HBox(labelSolution);
		solBox.setAlignment(Pos.CENTER);
		solBox.setSpacing(15);
		
		HBox inputBox = new HBox(inputWord);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.setSpacing(15);
		
		HBox buttonBox = new HBox(newGame, newWord);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(15);
		
		HBox pointBox = new HBox(labelPoint);
		pointBox.setAlignment(Pos.CENTER);
		pointBox.setSpacing(15);
		
		HBox passedUserAbilitiesBox = new HBox(labelPassedUserAbilities);
		passedUserAbilitiesBox.setAlignment(Pos.CENTER);
		passedUserAbilitiesBox.setSpacing(15);

		setSpacing(20);
		setAlignment(Pos.CENTER);
		getChildren().addAll(inst, defBox, solBox, inputBox, buttonBox, diffBox, pointBox, passedUserAbilitiesBox);
		
	}

	public void newGame(ActionEvent event) {
			
		try {
			Scanner fileScan = new Scanner(new File("words-and-defs.txt"));	
			String strSol;
			String strDef;
			
			solutionList = new ArrayList<>();
			definitionList = new ArrayList<>();
			usedDefinition = new ArrayList<>();
			
			while (fileScan.hasNext()) {
				strSol = fileScan.nextLine();
				solutionList.add(strSol);
				strDef = fileScan.nextLine();
				definitionList.add(strDef);	
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		setI();
		
		if (user.getUserAbility() > 0) {
			if (user.getPointObtained() >= user.getMaxPoint()) {  
				addPassedGames();
				if (passedGames.size() > 0) {
					showPassedGames();
				}
			}
			user.resetUserAbility();
			user.resetPointObtained();
		}
		
		labelDefinition.setText(currentDef);
		labelSolution.setText(maskedSol);
		labelInstructions.setText("");
		labelPoint.setText(user.toStringPointObtained());
	
	}
	
	public void setI() {
		
		i = r.nextInt(150);
		diffChoiceBox.getValue();
		user.setDiffLevel(diffChoiceBox.getValue());
		
		if (user.getDiffLevel() == solutionList.get(i).length()) {
			currentDef = definitionList.get(i);
			currentSol = solutionList.get(i);
			usedDefinition.add(definitionList.get(i));
			maskSolution();
		}
		else {
			setI();
		}
		
	}

	
	public void showPassedGames() {
											
		if (passedGames.size() > 0) { 		
			passGames = "Your passed user abilities: ";
			if(user.getPointObtained() >= user.getMaxPoint() || user.getPointObtained() >= 0) //aggiunto or
				for (int z = 0; z < passedGames.size(); z++) {
					passGames += passedGames.get(z) + ", ";
				}
			labelPassedUserAbilities.setText(passGames);
		}
	
	}
	
	
	public void checkUsedDef() {
		
		for (int b = 0; b < usedDefinition.size(); b++) {
			if (usedDefinition.get(b).equalsIgnoreCase(definitionList.get(i))){
				definitionList.remove(i);
				solutionList.remove(i);	
				if (b == usedDefinition.size()){
					setI();
				}
			}
			else	{
				currentDef = definitionList.get(i);
				currentSol = solutionList.get(i);
				maskSolution();
				labelDefinition.setText(definitionList.get(i));
				labelSolution.setText(maskedSol);
				labelInstructions.setText("");
				labelPoint.setText(user.toStringPointObtained());
			}
		}
	}
	
	public void newWord(ActionEvent event) {
		
		setI();
		checkUsedDef();
	
	}
	
	public void maskSolution() {
		
		maskedSol = currentSol;
		String subMaskedSol = currentSol.substring(1, currentSol.length() - 1);
		String subStart = currentSol.substring(0, 1);	
		String subEnd = currentSol.substring(currentSol.length() - 1);
		
		for (int o = 0; o < subMaskedSol.length(); o++) {  
			subMaskedSol = subMaskedSol.replace(subMaskedSol.charAt(o), '?');	
		} 
		maskedSol = subStart + subMaskedSol + subEnd;
		
	}
	
	
	public void parzialSolution() {
		
		int j = r.nextInt(maskedSol.length());		
		String parzMaskSol;
		parzMaskSol = maskedSol; 					
		StringBuilder string = new StringBuilder(parzMaskSol);
		
		if (parzMaskSol.charAt(j) == '?' && j < currentSol.length() - 1) {
			string.setCharAt(j, currentSol.charAt(j));		
			maskedSol = string.toString();
			labelSolution.setText(maskedSol);
			
		}
		else 
			parzialSolution();

				
	}
	
	
	
	public void checkGuess(ActionEvent event) {
		
		String str = inputWord.getText();
		
		if (currentSol.equalsIgnoreCase(str) && user.getPointObtained() < user.getMaxPoint()) { 
			user.setPointObtained(countScore());
			if(user.getPointObtained() < user.getMaxPoint()) {
				user.setUserAbility(1);
				labelPoint.setText(user.toStringPointObtained());
				labelDefinition.setText("You are right!");
				labelSolution.setText(currentSol);
				
				
			}
			else if (user.getPointObtained() >= user.getMaxPoint()) {
				user.setUserAbility(1);
				labelDefinition.setText("Your game is finished");
				labelSolution.setText("Your user abilitiy is: " + user.getUserAbility());
				labelPoint.setText("");
			}
		}
		else 
			{
			parzialSolution();
			}


	}
	
	

	public int countScore() {
		
		int count = 0;
		
		for (int j = 0; j < currentSol.length() - 1; j++ ) {
			if (maskedSol.charAt(j) == '?') {
				if (inputWord.getText().equalsIgnoreCase(labelSolution.getText())) { //agg
					return count;
				}
				else { 
					count++;
				}
			}
		}
		return count;
	}
	
	public void addPassedGames() {
		
		passedGames.add(user.getUserAbility());
		
	}
	

}
