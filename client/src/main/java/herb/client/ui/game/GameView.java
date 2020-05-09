package herb.client.ui.game;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Locale;
import herb.client.ressources.Card;
import herb.client.ressources.Lobby;
import herb.client.ressources.Player;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.TrickBase;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
import herb.client.ui.lobby.LobbyModel;
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
	private VBox leftHandSide, rightHandSide, opposite, bottom, names, points, lobbyBox, showTrumpBox;
	private HBox oppositeSide, left, right;
	private TilePane trumpBox;
	private HBox tMain, tRight, tOppo, tLeft, buttons;
	private Label trickLabel, trickLabel2, playerLabel, leftHandLabel, rightHandLabel, oppositeLabel, pointsPlayerLabel, lobbyLabel;
	private Label pointsLabel, playedPointsLabel, trumpLabel, currentPlayerLabel, startingPlayerLabel, startingPlayerText;
	private Region spacer, spacerTable, spacerTable2, spacerRight, spacerOppo;
	private BorderPane upperPart, pointPane;
	private StackPane tablePart;
	private ArrayList<Rectangle> rects, trickRects, trumpRects;
	private ArrayList<Card> cardAreas;
	private ArrayList<Player> players;
	private MenuBar menuBar;
	private Menu menuLanguage;
	private ArrayList<Card> cards, trick;
	private ImageView imview;
	private Button revancheButton, quitButton;


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
		Menu menuExit = new Menu();
		MenuItem back = new MenuItem();
		MenuItem logout = new MenuItem();
		menuExit.getItems().addAll(back, logout);
		menuBar.getMenus().addAll(menuLanguage, menuExit);
				
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
		names = new VBox();
		points = new VBox();	
		
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
//		spacer.setMinWidth(670d);
		spacer.setMinWidth(200d);
		playerLabel = new Label(players.get(0).getUsername());
		playerLabel.setMinHeight(20);
		
		// display trump
		trumpLabel = new Label();
		imview = new ImageView();
		
		// display lobby and starting player (currentPlayer only temporary) TODO
		lobbyBox = new VBox();
		// somehow get LobbyLabel TODO
	    // need the lobby name: Datastore.getInstance().getMainPlayer().getRound()???
	    Lobby l = new Lobby();
		lobbyLabel = new Label(l.getName().toString());
		currentPlayerLabel = new Label("next ---");
	    startingPlayerText = new Label();
	    startingPlayerLabel = new Label("is defined later");
	   	lobbyBox.getChildren().addAll(lobbyLabel, startingPlayerText, startingPlayerLabel);
	   	lobbyBox.getChildren().add(currentPlayerLabel);
				
		// Cards and Trump images
	    rects = new ArrayList();
	    trickRects = new ArrayList();
	    trumpRects = new ArrayList();
		
		setMyCards();
		
		setTrumpInfo();
		
		// nodes in StackPane - trump=2, table and points 0 or 1 
		setTrick();
		setPointPane();
		setTrumpOptions();
			
		updateLeftPlayer();
		updateRightPlayer();
        updateOppoPlayer();
       		
		//BorderPane opposite Player and Table
		upperPart.setCenter(tablePart);
		upperPart.setTop(opposite);
		upperPart.setMinWidth(400d);
		upperPart.setLeft(left);
		upperPart.setRight(right);
		
		//root.getChildren().addAll(upperPart, bottom, left, right, menuBar, lobbyBox, showTrumpBox);
		
		root.getChildren().addAll(upperPart, bottom, menuBar, lobbyBox, showTrumpBox);

		root.setLeftAnchor(menuBar, 0d);
		root.setTopAnchor(menuBar, 0d);
		root.setRightAnchor(menuBar, 0d);
		
//		root.setLeftAnchor(left, -10d);
//		root.setTopAnchor(left, 70d);
//		root.setBottomAnchor(left, 200d);
//		root.setRightAnchor(right, -10d);
//		root.setTopAnchor(right, 70d);
//		root.setBottomAnchor(right, 200d);

		root.setId("background");
		root.setTopAnchor(upperPart, -40d);
		root.setLeftAnchor(upperPart, 0d);
		root.setRightAnchor(upperPart, 0d);
		
		root.setBottomAnchor(bottom, 10d);
		root.setLeftAnchor(bottom, 10d);
		root.setRightAnchor(bottom, 10d);
		
		root.setTopAnchor(showTrumpBox, 90d);
		root.setRightAnchor(showTrumpBox, 50d);
	
		root.setTopAnchor(lobbyBox, 90d);
		root.setLeftAnchor(lobbyBox, 50d);
		
		updateLabels();
		Scene scene = new Scene(root, 1000, 900);
		return scene;
	}
	
///////////////////////////////////////////////////////////////
	
	
	// Roesti - create hand of MainPlayer: ownCards GridPane
	private void setMyCards() {
		cards = model.getMyCards();
		
		for (int i=0; i<9; i++) {

		    // rectangle with ImagePattern (imageview cannot be fanned out)
		    Rectangle rectangleCard = new Rectangle();
		    rectangleCard.setHeight(514/2);
		    rectangleCard.setWidth(322/2);		
		    rectangleCard.setArcHeight(20);
		    rectangleCard.setArcWidth(20);
		    rectangleCard.setStroke(Color.BLACK);	    		    

		      //  fan out cards
//		    Rotate rotate = new Rotate();
//		    rotate.setAngle(-40+10*i); 
//		    rotate.setPivotX(322/2/2); 	
//		    rotate.setPivotY(257*2.2);
//		    rectangleCard.getTransforms().addAll(rotate);   
		    ownCards.add(rectangleCard, i+1, 1);	
		    rects.add(rectangleCard); 
		}			
		// center fanned out Cards
		updateImagePatterns();
		ownCards.add(spacer, 0, 0);
		ownCards.setMinHeight(250);
		ownCards.setHgap(-50);
		
		//TODO show trickLabel2 "it's your turn" only when true
		bottom.getChildren().add(trickLabel2);
		trickLabel2.setVisible(false);
		bottom.getChildren().add(playerLabel);
		bottom.getChildren().add(ownCards);
		bottom.setAlignment(Pos.CENTER);
	}
	
	public void updatePlayables(Player curr) {
		if(curr.equals(model.getPlayers().get(0))) {
		cards = model.getMyCards();
		
	    for(int j= 0; j<cards.size();j++) {    	
	   		if (cards.get(j).isPlayable()) {
	    	this.rects.get(j).setStroke(Color.GOLD);
	    	this.rects.get(j).setStyle("-fx-stroke-width: 5");
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
	    
	    for (int i=0; i<4; i++) {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(514/3);
		rectangle.setWidth(322/3);		
		rectangle.setArcHeight(20);
		rectangle.setArcWidth(20);
		rectangle.setStroke(Color.BLACK);
	    // Roesti - implement rotation  TODO implement Random()
//		Rotate rotate = new Rotate();  
//		rotate.setAngle(((10+i) % 2)+183-2*i); 
//		rotate.setPivotX(30); 
//		rotate.setPivotY(322/3+30); 
//	    rectangle.getTransforms().addAll(rotate); 
		trickRects.add(rectangle);
	    }
	    tMain.getChildren().add(trickRects.get(0));
		tRight.getChildren().add(trickRects.get(1));
		tOppo.getChildren().add(trickRects.get(2));
		tLeft.getChildren().add(trickRects.get(3));
		table.setHgap(10);
		table.setVgap(10);
		table.setStyle("-fx-alignement: center");
		tablePart.getChildren().add(table);
	}
	
	// Roesti - update ImagePatterns (getMyCards from Server)
	// TODO card images from herb / client / ui / images / fr OR de 	
	public void updateImagePatterns() {		
		for (int d = 0; d<9; d++)   {		
			rects.get(d).setFill(null);
			rects.get(d).setStroke(Color.TRANSPARENT);
		}
		cards = model.getMyCards();
		
		for(int j=0; j<cards.size(); j++) {
		String rank = cards.get(j).getRank().toStringDE();
		String suit = cards.get(j).getSuit().toStringFr();
	    String filename = suit + "_" + rank + ".jpg";
	    Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));		
	    ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
	    this.rects.get(j).setFill(pattern);
	    this.rects.get(j).setStroke(Color.BLACK);
		}
		removeTurn();
	}
	
	// Roesti - update trick ImagePatterns
	// TODO card images from herb / client / ui / images / fr OR de
	public void updateTrick(ArrayList<Card> cardSet) {
		for (int d = 0; d<4; d++)   {		
			trickRects.get(d).setFill(null);
		}
		trick = new ArrayList();
		trick = cardSet;
		// update played cards
		for (int i = 0; i< trick.size(); i++) {
		String r1 = trick.get(i).getRank().toStringDE();
		String s1 = trick.get(i).getSuit().toStringFr();
		String filename = s1 + "_" + r1 + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
		ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/3, 514/3, false);
		
		// goal: put the played card next to its player		
		Player s = model.getStartingPlayer();
		startingPlayerLabel.setText(s.getUsername().toString());
		
		Player c = model.getCurrentPlayer();
		currentPlayerLabel.setText(c.getUsername().toString());
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
	
	private void updateLeftPlayer() {
		//TODO reduce cards
		// leftHandSide TODO magic number!
		for (int i= 0; i< 9; i++) {
		Rotate rotateLeft = new Rotate();
		Rectangle rectangleLeft = new Rectangle();
		rectangleLeft.setHeight(322/4);
		rectangleLeft.setWidth(514/4);
        rectangleLeft.setArcHeight(20);
        rectangleLeft.setArcWidth(20);
        String filenameLeft = "Rückseite.jpg";
		Image imageLeft = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameLeft));
        ImagePattern patternLeft = new ImagePattern(imageLeft, 0, 0, 514/4,322/4, false);
        rectangleLeft.setFill(patternLeft);
	    
//      rotateLeft.setAngle(-270+5*i); 
//	    rotateLeft.setPivotX(322/4/2); 	
//	    rotateLeft.setPivotY(-514/4); 
//	    rectangleLeft.getTransforms().addAll(rotateLeft);      
        leftHandSide.getChildren().add(rectangleLeft);        
	}
		leftHandSide.setSpacing(-40.0);
		leftHandSide.setMinWidth(100);
		leftHandSide.setMaxHeight(400);
		left.getChildren().add(leftHandSide);
		left.getChildren().add(leftHandLabel);
	    left.setSpacing(10.0);
		left.setAlignment(Pos.CENTER_RIGHT);
	}
	
	private void updateRightPlayer() {
		// TODO reduce cards
		// rightHandSide - cards in VBox, together with name in HBox
		for (int i= 0; i< 9; i++) {
		Rotate rotateRight = new Rotate();
		Rectangle rectangleRight = new Rectangle();
		rectangleRight.setHeight(322/4);
		rectangleRight.setWidth(514/4);		
		rectangleRight.setArcHeight(20);
		rectangleRight.setArcWidth(20);
		String filenameRight = "Rückseite.jpg";
		Image imageRight = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameRight));
        ImagePattern patternRight = new ImagePattern(imageRight, 0, 0, 514/4, 322/4, false);
        rectangleRight.setFill(patternRight);
        
//	    rotateRight.setAngle(-70+5*i); 
//	    rotateRight.setPivotX(322/4/2); 	
//	    rotateRight.setPivotY(-514/4); 
//	    rectangleRight.getTransforms().addAll(rotateRight);      
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
	}
	
	private void updateOppoPlayer() {
		// TODO reduce cards
		// oppositeHandSide - cards in VBox, with name in HBox
	//	oppositeSide.getChildren().add(spacerOppo); 
		for (int i = 0; i<9; i++) {
        Rotate rotateOppo = new Rotate();
        Rectangle rectangleOppo = new Rectangle();
		rectangleOppo.setHeight(514/4);
		rectangleOppo.setWidth(322/4);		
		rectangleOppo.setArcHeight(20);
		rectangleOppo.setArcWidth(20);
        
		String filenameOppo = "Rückseite.jpg";
		Image imageOppo = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameOppo));
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
	
	public void setTrumpOptions() {
		// show all trump options in StackPane window in Front
		for (int i = 0; i< 6; i++) {
			Rectangle rectangleTO = new Rectangle();
			rectangleTO.setHeight(100);
			rectangleTO.setWidth(100);
			rectangleTO.setArcHeight(10);
			rectangleTO.setArcWidth(10);
	        
	        Trump trump;
	        trump = Trump.values()[i];
	        String t = trump.toString(); 
			String filenameTrump = t + ".png";
	        
			Image images = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameTrump));
	        ImagePattern patterns = new ImagePattern(images, 0, 0, 100, 100, false);
	        rectangleTO.setFill(patterns);
	        trumpBox.getChildren().add(rectangleTO);  
	        trumpRects.add(rectangleTO);
		}	
        tablePart.getChildren().add(trumpBox);
		trumpBox.setVisible(true);
		
		//check if this player is the starting player and if trump is not yet set
		Player x = Datastore.getInstance().getMainPlayer();
		if (!x.getRound().getCurrentStartingPlayer().equals(x) && x.getRound().getTrump() == null) {
			changeTopOfStackPane();
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
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + trumpFilename));
		imview.setImage(image);	
	}
	
    private void setPointPane() {
    	pointsLabel = new Label();
    	pointsPlayerLabel = new Label();
    	playedPointsLabel = new Label();
    	revancheButton = new Button();
    	quitButton = new Button();
    	buttons = new HBox();
    	//change
    	buttons.getChildren().addAll(revancheButton, quitButton);
		
    	String label ="";
    	for(int i = 0; i<4; i++) {
    		label += players.get(i).getUsername().toString();
    		label += "\n";
    	}
    	pointsPlayerLabel.setText(label);
		names.getChildren().add(pointsPlayerLabel);
		names.setAlignment(Pos.CENTER_LEFT);
		
		changeTopOfStackPane();
		
		playedPointsLabel.setText("");
	   
		points.getChildren().add(playedPointsLabel);
		points.setAlignment(Pos.CENTER_RIGHT);
		
		pointPane.setTop(pointsLabel);
		pointPane.setLeft(names);
		pointPane.setCenter(points);
		pointPane.setBottom(buttons);	
		pointPane.setPadding(new Insets(20, 20, 20, 20));
		pointPane.setStyle("-fx-background-color: aliceblue");
		tablePart.getChildren().add(pointPane);
			
		pointPane.setVisible(false);
		pointPane.toBack();
    }	
	
	public void updatePointPane(ArrayList<Integer> scores) {
		changeTopOfStackPane();
		pointPane.setVisible(true);
		
		String labela ="";
    	for(int i = 0; i<4; i++) {
    		labela += scores.get(i).toString();
    		labela += "\n";
    	}
		playedPointsLabel.setText(labela);
	}
	
	public void setTurn() {
		trickLabel2.setVisible(true);
	}
	
	public void removeTurn() {
		trickLabel2.setVisible(false);
	}
	
	public void setStartingPlayer() {
		Player s = model.getStartingPlayer();
		startingPlayerLabel.setText(s.getUsername().toString());
	}
	
	// Roesti 
	private void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		// language settings
		menuLanguage.setText(t.getString("program.game.menuLanguage"));
		// screen labels
		trickLabel2.setText(t.getString("program.game.order"));
		pointsLabel.setText(t.getString("program.game.pointsLabel"));
		stage.setTitle(t.getString("program.name"));
		trumpLabel.setText(t.getString("program.game.trump"));
		startingPlayerText.setText(t.getString("program.game.startingPlayer"));
		revancheButton.setText(t.getString("program.game.revancheButton"));
		quitButton.setText(t.getString("program.game.quitButton"));
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
}