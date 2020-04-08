package herb.client.ui.game;

import javafx.stage.Stage;
import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color; 


public class GameView extends View<GameModel> {
	
	private AnchorPane root; 
	private GridPane table;
	private GridPane ownCards;
	private VBox leftHandSide, rightHandSide;
	private HBox oppositeSide;
	private Label trickLabel, trickLabel2, playerLabel, leftHand, rightHand, opposite;
	private Region spacer, spacerTable, spacerTable2, spacerRight, spacerOppo;
	private BorderPane upperPart;
	private StackPane tablePart;

	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		// Roesti - create gui elements
		this.root = new AnchorPane();
		
		upperPart = new BorderPane();
		tablePart = new StackPane();
		leftHandSide = new VBox();
		rightHandSide = new VBox();
		oppositeSide = new HBox();
		table = new GridPane();
		ownCards = new GridPane();
		
		ownCards.setMinHeight(300);
		ownCards.setMinWidth(1300);
		
		leftHand = new Label("Spieler vor mir");
		rightHand = new Label("Spieler nach mir");
		opposite = new Label("Spieler gegenüber");
		trickLabel2 = new Label("Oh come and play with me...\n" + 
				"Lets play till we go crazy");
		trickLabel = new Label();
		trickLabel.setMinHeight(70);
		playerLabel = new Label("I'm the best Jasser in the world!");
		playerLabel.setMinHeight(20);
		//playerLabel.setMinWidth(1300);
		playerLabel.setTextAlignment(TextAlignment.CENTER);
		
		spacer = new Region();
		spacerTable = new Region();
		spacerTable2 = new Region();
		spacer.setMinWidth(500d);
		spacerTable.setMinWidth(200d);
		spacerTable2.setMinWidth(200d);
		spacerRight = new Region();
		spacerRight.setMinWidth(100d);
		spacerOppo= new Region();
		spacerOppo.setMinWidth(400d);
		
		leftHandSide.getChildren().add(leftHand);
		rightHandSide.getChildren().add(rightHand);
		oppositeSide.getChildren().add(opposite);
		
	
		// create MenuBar - language and cardSet (fr vs. de) TODO
		
	
		// get players, myCards and my playable cards from server (GameModel)  TODO
		model.getLobbyPlayers();
		
		// Roesti - card images from herb / client / ui / images / fr OR de TODO
		Card[] cardAreas = new Card[9];
		cardAreas = model.getMyCards();
		
		// show, if it works
//		String writeCardsOut = "Jetzt";
//		for (int i = 0; i < 9; i++) {
//			writeCardsOut += cardAreas[i].getSuit();
//			writeCardsOut += cardAreas[i].getRank();	
//			} System.out.println(writeCardsOut);
		
		
		// Roesti - create hand: ownCards GridPane
		for (int i=0; i<9; i++) {
			String rank = ""+cardAreas[i].getRank().toStringDE();
			String suit = ""+cardAreas[i].getSuit().toStringFr();
		    String filename = suit + "_" + rank + ".jpg";
		    Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));

		    // rectangle with ImagePattern (imageview cannot be fanned out)
		    Rectangle rectangleCard = new Rectangle();
		    rectangleCard.setHeight(514/2);
		    rectangleCard.setWidth(322/2);		
		    rectangleCard.setArcHeight(20);
		    rectangleCard.setArcWidth(20);
		    ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
		    rectangleCard.setFill(pattern);
		     
		      //  fan out cards
		    Rotate rotate = new Rotate();
		    rotate.setAngle(-40+10*i); 
		    rotate.setPivotX(322/2/2); 	
		    rotate.setPivotY(257*2.2);
		    rectangleCard.getTransforms().addAll(rotate); 

		    ownCards.add(rectangleCard, i+1, 1);	
		}

		ownCards.setMinHeight(300);
		ownCards.setHgap(-150);
		ownCards.setStyle("-fx-background-color: beige");

		// show playername
		ownCards.add(playerLabel, 5, 0, 1, 1);
		// center Cards
		ownCards.getChildren().add(spacer);
		
		// Roesti - create trick: table GridPane 
	    trickLabel.setStyle("-fx-font-weight: bold");
	    trickLabel.setTextFill(Color.RED);
	    trickLabel.setTextAlignment(TextAlignment.CENTER);
	    table.add(trickLabel, 1, 0, 1, 1);
	    table.add(spacerTable, 0, 0, 1, 1);
	    table.add(spacerTable2, 3, 0, 1, 1);
		
		for (int i = 0; i<4; i++) {
		Card[] trickcards = new Card[4];
			
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(514/3);
		rectangle.setWidth(322/3);		
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        Rank r1 = Rank.Ace;
		Suit s1 = Suit.Hearts;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1.toString(), "Ace", t1.toString());
		trickcards[i] = c1;
		String filename = s1.toStringFr() + "_" + r1.toStringDE() + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
        ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/3, 514/3, false);
        rectangle.setFill(pattern);
        rectangle.setId("cardimage");
        
	    // better ImagePattern or ImageView? TODO
//        ImageView imView = new ImageView(imageTable);
//        imView.setPreserveRatio(true);
        
	    // Roesti - implement rotation 
        Rotate rotate = new Rotate();  
        rotate.setAngle(((10+i) % 2)+1 *1); 
        rotate.setPivotX(150); 
	    rotate.setPivotY(225); 
	    rectangle.getTransforms().addAll(rotate); 	
				
	    if(i == 0)
	    	table.add(rectangle, 2, 0, 1, 2);
	    if(i==1)
	    	table.add(rectangle, 1, 1, 1, 2);
	    if(i==2)
	    	table.add(rectangle, 2, 2, 1, 2);
	    if(i==3) {
	    	table.add(rectangle, 3, 1, 1, 2);
	    }

	    // better ImagePattern or ImageView? TODO
//	    table.add(imView, 4, 0, 1, 2);
	    
		table.setHgap(10);
		table.setVgap(10);
		table.setStyle("-fx-alignement: center");
		}

		
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
        
		// rightHandSide
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
        
		// oppositeHandSide
		oppositeSide.getChildren().add(spacerOppo); 
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
		oppositeSide.setMinHeight(70);
		
		//BorderPane
		upperPart.setCenter(trickLabel2);
		upperPart.setBottom(table);
//		upperPart.setLeft(leftHandSide);
		upperPart.setTop(oppositeSide);
//		upperPart.setRight(rightHandSide);
		
//		tablePart.getChildren().addAll(upperPart, table);
//		tablePart.setAlignment(table, Pos.CENTER);
//		tablePart.setAlignment(upperPart, Pos.TOP_RIGHT);

		//GridPane
//		root.add(table, 1, 1, 1, 1);
//		root.add(leftHandSide, 0, 1, 1, 1);
//		root.add(rightHandSide, 3, 1, 1, 1);
//		root.add(oppositeSide, 1, 0, 1, 1);
//		root.add(ownCards, 0, 2, 3, 1);
		
		//AnchorPane
		root.getChildren().addAll(upperPart, ownCards, leftHandSide, rightHandSide);
//		root.getChildren().addAll(table, leftHandSide, rightHandSide, oppositeSide, ownCards);
//		root.setLeftAnchor(table, 200d);
//		root.setTopAnchor(table, 150d);
//		root.setRightAnchor(table, 200d);
//
		root.setLeftAnchor(leftHandSide, -10d);
		root.setTopAnchor(leftHandSide, 200d);
		root.setBottomAnchor(leftHandSide, 200d);
		root.setRightAnchor(rightHandSide, -10d);
		root.setTopAnchor(rightHandSide, 200d);
		root.setBottomAnchor(rightHandSide, 200d);
//		root.setTopAnchor(oppositeSide, 10d);
//		root.setLeftAnchor(oppositeSide, 200d);

		root.setStyle("-fx-background-color: bisque");
		root.setTopAnchor(upperPart, -10d);
		root.setLeftAnchor(upperPart, 100d);
		root.setBottomAnchor(upperPart, 200d);
		
		root.setBottomAnchor(ownCards, 10d);
		root.setLeftAnchor(ownCards, 10d);
		root.setRightAnchor(ownCards, 10d);

		
		Scene scene = new Scene(root, 1000, 1000);
		return scene;
		
    	
		
	}
	
	private void showPlayerPane() {
		//TODO - nine cards, ...
	}
	
	private void updatePlayerPane() {
		//TODO - update playable cards after every played card
	}
	
	private void showTrick() {
		//TODO - four cards, one of each player
	}
	private void updateTrick() {
		//TODO - add every played card
	}
	
	private void clearTrick() {
		//TODO let the played cards disappear, show the next player
	}
	public Rectangle getPlayedCard() {
		// do I need 9 specific rectangles?
		return null;
	}

}
