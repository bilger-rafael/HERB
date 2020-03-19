package herb.server.ressources;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import herb.server.ressources.core.CardBase;
import herb.server.ressources.core.PlayerBase;
import herb.server.ressources.core.RoundBase;
import herb.server.ressources.core.TrickBase;
import herb.server.ressources.core.Trump;

//Etter
public class Round extends RoundBase{
	private Trump currentTrump;
	private DeckOfCards deck;
	protected PlayerBase[] players = new PlayerBase[4];
	private Trick currentTrick;
	private PlayerBase startingPlayer;
	private Map<PlayerBase,Integer> actualScores;

	
	public Round(PlayerBase[] players) {
		super(players);
		genTrump();
		this.deck = new DeckOfCards(this.currentTrump);
		
		//Hände leeren
		for (int i = 0; i<this.players.length; i++) {
			this.players[i].clearHand();
		}
		
		//Karten verteilen
		while (this.deck.getCardsRemaining()>0) {	
		}
		for (int i = 0; i<this.players.length; i++) {
			this.players[i].addCardtoHand(this.deck.dealCard());
		}
		
		//Hände sortieren
		for (int i =0; i<this.players.length;i++) {
			this.players[i].SortMyCards();
		}
		
		//TODO Wer soll starten?
		this.startingPlayer=players[0];
		
		//Tricks spielen
		while(!this.players[0].PlayerNoCards()) {
			PlayerBase winner;
			createNewTrick();
			//Spielen
			winner = this.currentTrick.playTrick();
			//Punkte auswerten
			addTrickScore(winner);
			//neuer Startspieler setzen
			this.startingPlayer=winner;
			
		}
		endRound();
	}

	private void endRound() {
		getScoreTable();
		
	}

	@Override
	public TrickBase getCurrentTrick() {
		
		return this.currentTrick;
	}
	
	private void createNewTrick() {
		Trick trick = new Trick(this.players, this.startingPlayer);
		this.currentTrick=trick;
	}
	
	@Override
	protected void genTrump() {
		Random rand = new Random();
		int i = rand.nextInt((6));
		currentTrump = Trump.values()[i];
	}
	
	@Override
	public Trump getCurrentTrump() {
		return currentTrump;
	}

	@Override
	protected void addTrickScore(PlayerBase winner) {
		Integer additionalScore = this.currentTrick.getTrickPoints();
		Integer oldScore = actualScores.get(winner);
		actualScores.put(winner, (additionalScore+oldScore));
	}

	@Override
	public Map<PlayerBase, Integer> getScoreTable() {
		Map<PlayerBase, Integer> sortedTable = new TreeMap<>();
				actualScores.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEachOrdered(x-> sortedTable.put(x.getKey(), x.getValue()));
				
		return sortedTable;
	
		
	}

}
