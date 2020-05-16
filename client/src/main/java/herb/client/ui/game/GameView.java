package herb.client.ui.game;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.TrickBase;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
import herb.client.utils.Datastore;
import herb.client.utils.ServiceLocator;
import herb.client.utils.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.scene.Node;


public class GameView extends View<GameModel> {
	
	private AnchorPane root; 
	private GridPane table, ownCards;
	private VBox leftHandSide, rightHandSide, opposite, bottom, namesBox, pointsBox, winnerBox, lobbyBox, showTrumpBox, chooseTrumpBox;
	private HBox oppositeSide, left, right;
	private TilePane trumpBox;
	private HBox tMain, tRight, tOppo, tLeft, buttons;
	private Label trickLabel, trickLabel2, playerLabel, leftHandLabel, rightHandLabel, oppositeLabel, lobbyLabel;
	private Label pointsLabel, pointsPlayerLabel, pointsPlayerLabel2, playedPointsLabel, playedPointsLabel2, winnerLabel, winnerLabel2; 
	private Label trumpLabel, trumpOrderLabel, startingPlayerLabel, startingPlayerText;
	private Region spacer, spacerTable, spacerTable2, spacerRight, spacerOppo;
	private BorderPane upperPart, pointPane;
	private StackPane tablePart;
	private ArrayList<Rectangle> rects, trickRects, trumpRects;
	private ArrayList<Card> cardAreas;
	private ArrayList<Player> players;
	private MenuBar menuBar;
	private Menu menuLanguage, menuCardSet;
	public MenuItem germanSet, frenchSet;
	private ArrayList<Card> cards, trick;
	private ImageView imview;
	private Button revancheButton, quitButton;
	private final int MAX_PLAYERS = 4; 
	private final int MAX_RECTS = 16;
	private final int MAX_CARDS = 9; 
	int startingPosition;

	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("GameView initialized");
	
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
	
		this.root = new AnchorPane();
		
		// create MenuBar for language and exit
		menuBar = new MenuBar();
		menuLanguage = new Menu();	
		menuCardSet = new Menu();
		frenchSet = new MenuItem();
		germanSet = new MenuItem();
		menuCardSet.getItems().addAll(frenchSet, germanSet);
		menuBar.getMenus().addAll(menuLanguage, menuCardSet);
				
		// link to Locale
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			this.menuLanguage.getItems().add(language);
			language.setOnAction(event -> {
			sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
			sl.setTranslator(new Translator(locale.getLanguage()));
			updateLabels();
			});
		}	
		
		// Roesti - create Panes and Controls 
		upperPart = new BorderPane();
		tablePart = new StackPane();
		chooseTrumpBox = new VBox();
		trumpBox = new TilePane();
		showTrumpBox = new VBox();
		
		// trick
		table = new GridPane();
		spacerTable = new Region();
		spacerTable.setMinWidth(200d);
		spacerTable2 = new Region();
		spacerTable2.setMinWidth(200d);
		tMain = new HBox();
		tRight = new HBox();
		tOppo = new HBox();
		tLeft = new HBox();
		trickLabel = new Label();
		trickLabel.setMinHeight(70);
		trickLabel2 = new Label();
		
		// points - winner
		pointPane = new BorderPane();
		namesBox = new VBox();
		pointsBox = new VBox();	
		winnerBox = new VBox();
		
		// get lobby players
		players = model.getLobbyPlayers();
		
		// other players
		left = new HBox(); 
		leftHandSide = new VBox();
		leftHandLabel = new Label(players.get(3).getUsername());
		right = new HBox();
		rightHandSide = new VBox();
		rightHandLabel = new Label(players.get(1).getUsername());
		spacerRight = new Region();
		opposite = new VBox();
		oppositeSide = new HBox();
		spacerOppo= new Region();
		spacerOppo.setMinWidth(300d);
		oppositeLabel = new Label(players.get(2).getUsername());
		oppositeLabel.setMinHeight(60);

		// Cards of MainPlayer
		bottom = new VBox();
		ownCards = new GridPane();
		ownCards.setMinHeight(300);
		ownCards.setMinWidth(1300);		
		spacer = new Region();
		//change
		spacer.setMinWidth(670d);
		//spacer.setMinWidth(200d);
		playerLabel = new Label(players.get(0).getUsername());
		playerLabel.setMinHeight(20);
		
		// display trump
		trumpLabel = new Label();
		imview = new ImageView();
		
		// display lobby and starting player (currentPlayer only temporary)
		lobbyBox = new VBox();
		lobbyLabel = new Label(model.getLobbyName());
	    startingPlayerText = new Label();
	    startingPlayerLabel = new Label();
	   	lobbyBox.getChildren().addAll(lobbyLabel);

				
		// Cards and Trump images
	    rects = new ArrayList();
	    trickRects = new ArrayList();
	    trumpRects = new ArrayList();
	    trumpOrderLabel = new Label();
		
		setMyCards();	
		setTrumpInfo();
		// nodes in StackPane - trump=2, table and points 0 or 1 
		setTrick();
		setPointPane();
		setTrumpOptions();
			
		setOtherPlayers();
       		
		//BorderPane other players and table
		upperPart.setCenter(tablePart);
		upperPart.setTop(opposite);
		upperPart.setMinWidth(400d);
		upperPart.setLeft(left);
		upperPart.setRight(right);
		
		root.getChildren().addAll(upperPart, bottom, menuBar, lobbyBox, showTrumpBox);

		root.setLeftAnchor(menuBar, 0d);
		root.setTopAnchor(menuBar, 0d);
		root.setRightAnchor(menuBar, 0d);

		root.setId("background");
		root.setTopAnchor(upperPart, -40d);
		root.setLeftAnchor(upperPart, 0d);
		root.setRightAnchor(upperPart, 0d);
		
		root.setBottomAnchor(bottom, 10d);
		root.setLeftAnchor(bottom, 10d);
		root.setRightAnchor(bottom, 10d);
		
		root.setTopAnchor(showTrumpBox, 90d);
		root.setRightAnchor(showTrumpBox, 50d);
	
		root.setTopAnchor(lobbyBox, 50d);
		root.setLeftAnchor(lobbyBox, 10d);
		
		updateLabels();
		Scene scene = new Scene(root, 1200, 1000);
		return scene;
	}
	
///////////////////////////////////////////////////////////////
	
	
	// Roesti - create hand of MainPlayer: ownCards GridPane
	private void setMyCards() {
		cards = model.getMyCards();
		
		for (int i=0; i<MAX_RECTS+1; i++) {

		    // rectangle with ImagePattern (imageview cannot be fanned out)
		    Rectangle rectangleCard = new Rectangle();
		    rectangleCard.setHeight(514/2);
		    rectangleCard.setWidth(322/2);		
		    rectangleCard.setArcHeight(20);
		    rectangleCard.setArcWidth(20);
		    rectangleCard.setStroke(Color.BLACK);	    		    

		      //  fan out cards
		    Rotate rotate = new Rotate();
		    rotate.setAngle(-40+5*i); 
		    rotate.setPivotX(322/2/2); 	
		    rotate.setPivotY(257*2.2);
		    rectangleCard.getTransforms().addAll(rotate);   
		    ownCards.add(rectangleCard, i+1, 1);	
		    rects.add(rectangleCard); 
		}			
		// center fanned out Cards
		updateImagePatterns();

		ownCards.setMinHeight(250);
		ownCards.setHgap(-150);
		ownCards.add(spacer, 0, 0);
		
		bottom.getChildren().add(trickLabel2);
		trickLabel2.setVisible(false);
		bottom.getChildren().add(playerLabel);
		bottom.getChildren().add(ownCards);
		bottom.setAlignment(Pos.CENTER);
		//ownCards.add(spacer, 18, 1);
				
	}
	
	public void updatePlayables(Player curr) {
		if(curr.equals(model.getPlayers().get(0))) {
		cards = model.getMyCards();
		
	    for(int j= 0; j<cards.size();j++) {    	
	   		if (cards.get(j).isPlayable()) {
	   			int indexforRects = startingPosition + 2*cards.indexOf(cards.get(j));
	   
	   			this.rects.get(indexforRects).setStroke(Color.GOLD);
	   			this.rects.get(indexforRects).setStyle("-fx-stroke-width: 5");
	    	}
	    }
	    updateLabels();
	    }
	}
	
	// Roesti - GridPane with trick rectangles
	private void setTrick() {
		table.add(trickLabel, 1, 0, 1, 1);
	    table.add(spacerTable, 0, 0, 1, 1);
	    table.add(spacerTable2, 3, 0, 1, 1);
	    tOppo.setPrefSize(322/3,  514/3);
	    tLeft.setPrefSize(322/3,  514/3);
	    tMain.setPrefSize(322/3,  514/3);
	    tRight.setPrefSize(322/3,  514/3);
	    table.add(tOppo, 2,  0, 1, 2);
	    table.add(tLeft,  1,  1, 1, 2);
	    table.add(tMain, 2,  2, 1, 2);
	    table.add(tRight,  3,  1, 1, 2);
	    
	    for (int i=0; i<MAX_PLAYERS; i++) {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(514/3);
		rectangle.setWidth(322/3);		
		rectangle.setArcHeight(20);
		rectangle.setArcWidth(20);
		rectangle.setStroke(Color.TRANSPARENT);
	    // Roesti - implement rotation  TODO implement Random()
		//change
		Rotate rotate = new Rotate();  
		rotate.setAngle(((10+i) % 2)+183-2*i); 
		rotate.setPivotX(30); 
		rotate.setPivotY(322/3+30); 
	    rectangle.getTransforms().addAll(rotate); 
		trickRects.add(rectangle);
	    }
	    tMain.getChildren().add(trickRects.get(0));
		tRight.getChildren().add(trickRects.get(1));
		tOppo.getChildren().add(trickRects.get(2));
		tLeft.getChildren().add(trickRects.get(3));
		table.setHgap(10);
		table.setVgap(10);
	//	table.setStyle("-fx-alignement: center");
		table.setAlignment(Pos.CENTER);
		tablePart.getChildren().add(table);
	}
	
	// Roesti - update ImagePatterns (getMyCards from Server)
	public void updateImagePatterns() {		
		removeTurn();
		for (int d = 0; d < MAX_RECTS+1; d++)   {		
			rects.get(d).setFill(null);
			rects.get(d).setStroke(Color.TRANSPARENT);
			rects.get(d).setStrokeWidth(1);
		}
		cards = model.getMyCards();
		
		if(!cards.isEmpty()) {
		startingPosition = MAX_CARDS - cards.size();
		System.out.println(startingPosition);
		System.out.println(MAX_CARDS);
		System.out.println(cards.size());
		
		int arrayIndex = 0;
		boolean endeDerFahnenstange = false;
		for(int j=startingPosition; !endeDerFahnenstange; j += 2) {
			String rank; 
			String suit;
			if(model.getCardSet().equals("De")){		
				rank = cards.get(arrayIndex).getRank().toStringDe();
				suit = cards.get(arrayIndex).getSuit().toStringDe();
			}else {		
				rank = cards.get(arrayIndex).getRank().toStringFr();
				suit = cards.get(arrayIndex).getSuit().toStringFr();
			}

			String filename = suit + "_" + rank + ".jpg";
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/"+ model.getCardSet() +"/" + filename));		
			ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
			this.rects.get(j).setFill(pattern);
			this.rects.get(j).setStroke(Color.BLACK);
			arrayIndex++;
	
			if(arrayIndex == cards.size())
				endeDerFahnenstange = true;
		}
		}
	}
	
	// Roesti - update trick ImagePatterns
	public void updateTrick(ArrayList<Card> cardSet) {
		for (int d = 0; d<MAX_PLAYERS; d++)   {		
			trickRects.get(d).setFill(null);
			trickRects.get(d).setStroke(Color.TRANSPARENT);
		}
		
		if(!cardSet.isEmpty()) {
		trick = new ArrayList();
		trick = cardSet;
		// update played cards
		for (int i = 0; i< trick.size(); i++) {	
			String r1; 
			String s1;
			if (model.getCardSet().equals("De")){
				r1 = trick.get(i).getRank().toStringDe();
				s1 = trick.get(i).getSuit().toStringDe();
			}else {
				r1 = trick.get(i).getRank().toStringFr();
				s1 = trick.get(i).getSuit().toStringFr();
			}
		String filename = s1 + "_" + r1 + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() +"/" + filename));
		ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/3, 514/3, false);
		
		// goal: put the played card next to its player		
		Player s = model.getStartingPlayer();
	//	startingPlayerLabel.setText(s.getUsername().toString());
		
		Player c = model.getCurrentPlayer();
		if(c.equals(model.getPlayers().get(0))) {
			trickLabel2.setVisible(true);

		}
		
		if (s.equals(model.getPlayers().get(0))) {
			trickRects.get(i).setFill(pattern);
		}
		
		if (s.equals(model.getPlayers().get(1))) {
			if(i == 0) {
				trickRects.get(1).setFill(pattern);
			}
			if (i == 1) {
				trickRects.get(2).setFill(pattern);
			}
			if (i == 2) {
				trickRects.get(3).setFill(pattern);
			}
			if (i == 3) {
				trickRects.get(0).setFill(pattern);
			}
		}
		if (s.equals(model.getPlayers().get(2))) {
			if(i == 0) {
				trickRects.get(2).setFill(pattern);
			}
			if (i == 1) {
				trickRects.get(3).setFill(pattern);
			}
			if (i == 2) {
				trickRects.get(0).setFill(pattern);
			}
			if (i == 3) {
				trickRects.get(1).setFill(pattern);
			}
		}
		if (s.equals(model.getPlayers().get(3))) {
			if(i == 0) {
				trickRects.get(3).setFill(pattern);
			}
			if (i == 1) {
				trickRects.get(0).setFill(pattern);
			}
			if (i == 2) {
				trickRects.get(1).setFill(pattern);
			}
			if (i == 3) {
				trickRects.get(2).setFill(pattern);
			}
		}
		}           
		}
	}
	
	public void setOtherPlayers() {
		// rightHandSide - cards in VBox, together with name in HBox

		for (int i= 0; i< MAX_CARDS; i++) {
		Rotate rotateRight = new Rotate();
		Rectangle rectangleRight = new Rectangle();
		rectangleRight.setHeight(322/4);
		rectangleRight.setWidth(514/4);		
		rectangleRight.setArcHeight(20);
		rectangleRight.setArcWidth(20);
		String filenameRight = "Rückseite.jpg";
		Image imageRight = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() + "/"  + filenameRight));
        ImagePattern patternRight = new ImagePattern(imageRight, 0, 0, 514/4, 322/4, false);
        rectangleRight.setFill(patternRight);
        rectangleRight.setStroke(Color.BLACK);
        
	    rotateRight.setAngle(-5*i); 
	    rotateRight.setPivotX(322/4/2); 	
	    rotateRight.setPivotY(-514/4); 
	    rectangleRight.getTransforms().addAll(rotateRight);      
        rightHandSide.getChildren().add(rectangleRight);     
		}
		
        rightHandSide.setSpacing(-40.0);
		rightHandSide.setMinWidth(100);
		rightHandSide.setMaxHeight(500);
		right.getChildren().add(rightHandLabel);
		right.getChildren().add(rightHandSide);
		right.setSpacing(10.0);
		right.setPadding(new Insets(10, 10, 10, 10));
		right.setAlignment(Pos.CENTER_RIGHT);
		
		//change
		for (int i= 0; i< MAX_CARDS; i++) {
		Rotate rotateLeft = new Rotate();
		Rectangle rectangleLeft = new Rectangle();
		rectangleLeft.setHeight(322/4);
		rectangleLeft.setWidth(514/4);
        rectangleLeft.setArcHeight(20);
        rectangleLeft.setArcWidth(20);
        String filenameLeft = "Rückseite.jpg";
		Image imageLeft = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() + "/" + filenameLeft));
        ImagePattern patternLeft = new ImagePattern(imageLeft, 0, 0, 514/4,322/4, false);
        rectangleLeft.setFill(patternLeft);
	    
	    rotateLeft.setAngle(5*i); 
	    rotateLeft.setPivotX(322/4/2); 	
	    rotateLeft.setPivotY(514/4); 
	    rectangleLeft.getTransforms().addAll(rotateLeft);      
        leftHandSide.getChildren().add(rectangleLeft); 
		}
		
        leftHandSide.setSpacing(-40.0);
		leftHandSide.setMinWidth(100);
		leftHandSide.setMaxHeight(400);
		left.getChildren().add(leftHandSide);
		left.getChildren().add(leftHandLabel);
	    left.setSpacing(10.0);
		left.setAlignment(Pos.CENTER_RIGHT);
		
		// oppositeHandSide - cards in VBox, with name in HBox
	//	oppositeSide.getChildren().add(spacerOppo); 
		for (int i = 0; i<MAX_CARDS; i++) {
	        Rotate rotateOppo = new Rotate();
	        Rectangle rectangleOppo = new Rectangle();
			rectangleOppo.setHeight(514/4);
			rectangleOppo.setWidth(322/4);		
			rectangleOppo.setArcHeight(20);
			rectangleOppo.setArcWidth(20);
	        
			String filenameOppo = "Rückseite.jpg";
			Image imageOppo = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() + "/" + filenameOppo));
	        ImagePattern patternOppo = new ImagePattern(imageOppo, 0, 0, 322/4, 514/4, false);
	        rectangleOppo.setFill(patternOppo);
		    rotateOppo.setAngle(20-5*i); 
		    rotateOppo.setPivotX(322/4/2); 	
		    rotateOppo.setPivotY(-514/4); 
		    rectangleOppo.getTransforms().addAll(rotateOppo);      
	        oppositeSide.getChildren().add(rectangleOppo); 
	    
		}
		
		oppositeSide.setSpacing(-70.0);
		oppositeSide.setMinHeight(100);
		oppositeSide.setAlignment(Pos.CENTER);
		opposite.getChildren().add(oppositeSide);
		opposite.getChildren().add(oppositeLabel);	
		opposite.setAlignment(Pos.CENTER);
		
	}
	
	public void updateLeftPlayer() {
		if((MAX_CARDS - model.getTrickNumber()) <leftHandSide.getChildren().size())
        leftHandSide.getChildren().remove(0);
	}
	
	public void updateRightPlayer() {
		if((MAX_CARDS - model.getTrickNumber()) <rightHandSide.getChildren().size())
		rightHandSide.getChildren().remove(0);
	}
	
	public void updateOppoPlayer() {
		if((MAX_CARDS - model.getTrickNumber()-1) <oppositeSide.getChildren().size())
		oppositeSide.getChildren().remove(0);
	}
	
	public void setTrumpOptions() {
		// show all trump options in StackPane window in Front
		
		for (int i = 0; i< 6; i++) {
			Rectangle rectangleTO = new Rectangle();
			rectangleTO.setHeight(100);
			rectangleTO.setWidth(100);
			rectangleTO.setArcHeight(40);
			rectangleTO.setArcWidth(40);
	        trumpBox.getChildren().add(rectangleTO);  
	        trumpRects.add(rectangleTO);
		}
		
		updateTrumpOptions();
		chooseTrumpBox.getChildren().addAll(trumpOrderLabel, trumpBox);
		chooseTrumpBox.setAlignment(Pos.CENTER);
		tablePart.getChildren().add(chooseTrumpBox);
		trumpBox.setAlignment(Pos.CENTER);
        trumpBox.setVisible(true);
        trumpBox.setHgap(30);
        trumpBox.setVgap(30);
        chooseTrumpBox.setStyle("-fx-background-color: saddlebrown");
        trumpBox.setPadding(new Insets(20, 20, 20, 20));
		
		//check if this player is the starting player and if trump is not yet set
		Player x = Datastore.getInstance().getMainPlayer();
		if (!x.getRound().getCurrentStartingPlayer().equals(x)) {
			changeTopOfStackPane();
		}
	}
	
	public void updateTrumpOptions() {
	
		for (int i = 0; i< 6; i++) {
	        Trump trump;
	        trump = Trump.values()[i];
	        String t = trump.toString(); 
			String filenameTrump = t + ".png";
	        
			Image images = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() + "/" + filenameTrump));
	        ImagePattern patterns = new ImagePattern(images, 0, 0, 100, 100, false);
	        trumpRects.get(i).setFill(patterns);  
		}	
	}
	
	public void changeTopOfStackPane() {		
		ObservableList<Node> tablePanes = this.tablePart.getChildren();
			if (tablePanes.size() > 1) {
				Node topNode = tablePanes.get(tablePanes.size()-1);
		        
				// This node will be brought to the front
				Node newTopNode = tablePanes.get(tablePanes.size()-2);
				
				topNode.setVisible(false);
				topNode.toBack();	
				newTopNode.setVisible(true);
				}
			}
	
	public void setTrumpInfo() {
		imview.setFitWidth(90);
        imview.setPreserveRatio(true);
        imview.setSmooth(true);
        imview.setCache(true);        
        showTrumpBox.getChildren().add(trumpLabel);
		showTrumpBox.getChildren().add(imview);	
		showTrumpBox.setAlignment(Pos.CENTER);
	}
	
	public void updateTrumpInfo(Trump t) {
		String trumpFilename = t.toString() + ".png";
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/" + model.getCardSet() + "/" + trumpFilename));
		imview.setImage(image);	
	}
	
    private void setPointPane() {
    	pointsLabel = new Label();
    	pointsPlayerLabel = new Label();
    	pointsPlayerLabel2 = new Label();
    	playedPointsLabel = new Label();
    	playedPointsLabel2 = new Label();
    	winnerLabel = new Label();
    	winnerLabel2 = new Label();
    	revancheButton = new Button();
    	quitButton = new Button();
    	buttons = new HBox();
    	buttons.getChildren().addAll(revancheButton, quitButton);
    	buttons.setSpacing(40);
		winnerBox.getChildren().addAll(pointsLabel, winnerLabel2, winnerLabel);

    	
    	String label = "";
    	for(int i = 0; i<4; i++) {
    		label += players.get(i).getUsername().toString();
    		label += "\n";
    	}
    	pointsPlayerLabel.setText(label);
		namesBox.getChildren().addAll(pointsPlayerLabel2, pointsPlayerLabel);
		namesBox.setAlignment(Pos.CENTER_LEFT);
	   
		pointsBox.getChildren().addAll(playedPointsLabel2, playedPointsLabel);
		pointsBox.setAlignment(Pos.CENTER_RIGHT);
		
		winnerBox.setAlignment(Pos.TOP_CENTER);
		winnerBox.setStyle("-fx-background-color: WHITE");
		
		pointPane.setId("tafel");
		pointPane.setTop(winnerBox);
		pointPane.setLeft(namesBox);
		pointPane.setCenter(pointsBox);
		pointPane.setBottom(buttons);	
		pointPane.setPadding(new Insets(40, 40, 40, 40));
		pointPane.setMaxSize(600,  400);
		tablePart.getChildren().add(pointPane);
			
		pointPane.setVisible(false);
		changeTopOfStackPane();
		pointPane.toBack();
    }	
	
	public void updatePointPane(ArrayList<Integer> scores) {
		changeTopOfStackPane();
		pointPane.setVisible(true);
		chooseTrumpBox.setVisible(false);
		
		String labela = "";
    	for(int i = 0; i<4; i++) {
    		labela += scores.get(i).toString();
    		labela += "\n";
    	}
		playedPointsLabel.setText(labela);
		
		
		ArrayList<Player> winners = new ArrayList<>();
		winners.add(model.getPlayers().get(0));
		Integer highestScore = scores.get(0);
		for(int i = 1; i<4; i++) {
		int result = highestScore.compareTo(scores.get(i)); 
		 
		if(result == 0) {
		winners.add(model.getPlayers().get(i));
		}
		if(result < 0) {
		winners.remove(winners.size()-1);
		winners.add(model.getPlayers().get(i));
		highestScore = scores.get(i);
		}
		}
		String labelW = "";
		for (int i = 0; i < winners.size(); i++) {
		labelW += winners.get(i).getUsername().toString();
		}
		winnerLabel.setText(labelW);
	}
	
	public void setTurn() {
		trickLabel2.setVisible(true);
	}
	
	public void removeTurn() {
		trickLabel2.setVisible(false);
	}
	
	public void removeTrumpPane() {
		trumpBox.setVisible(false);
	}
	
	public void setStartingPlayer() {
		
		try {
		Player s = model.getStartingPlayer();
		//startingPlayerLabel.setText(s.getUsername().toString());
		// changed
		int index = model.getPlayers().indexOf(s);
		trickRects.get(index).setStroke(Color.BLUE);
		
		} catch(Exception e) {
			startingPlayerLabel.setText("noch nicht festgelegt - oder kein Trick?");
		}
	}
	
	// Roesti 
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		// language settings
		menuLanguage.setText(t.getString("program.game.menuLanguage"));
		// screen labels
		menuCardSet.setText(t.getString("program.game.menuCardSet"));
		trickLabel2.setText(t.getString("program.game.order"));
		pointsLabel.setText(t.getString("program.game.pointsLabel"));
		trumpLabel.setText(t.getString("program.game.trump"));
		startingPlayerText.setText(t.getString("program.game.startingPlayer"));
		revancheButton.setText(t.getString("program.game.revancheButton"));
		quitButton.setText(t.getString("program.game.quitButton"));
		frenchSet.setText(t.getString("program.game.frenchSet"));
		germanSet.setText(t.getString("program.game.germanSet"));
		
		pointsPlayerLabel2.setText(t.getString("program.game.pointsPlayerLabel"));
		playedPointsLabel2.setText(t.getString("program.game.playedPointsLabel"));
		winnerLabel2.setText(t.getString("program.game.winnerLabel"));
		trumpOrderLabel.setText(t.getString("program.game.trumpOrder"));
	}
	
	public ArrayList<Card> getCardAreas(){
		return cardAreas;
	}
	
	// current cardSet
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	// Roesti - chosen card disposable for controller
	public ArrayList<Rectangle> getRects() {
		return this.rects;
	}
	
	public ArrayList<Rectangle> getTrumpChoice() {
	return this.trumpRects;
	}
	
	public Button getRevancheButton() {
	return this.revancheButton;
	}
	
	public Button getQuitButton() {
	return this.quitButton;
	}
	
	public MenuItem getFrenchOption() {
		return frenchSet;
	}
	
	public MenuItem getGermanOption() {
		return germanSet;
	}
	
	public int getStartingPosition() {
		return startingPosition;
	}
}