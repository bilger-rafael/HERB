package herb.client.ui.game;

import javafx.stage.Stage;
import herb.client.ressources.Card;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;

public class GameView extends View<GameModel> {
	
	private GridPane root; 
	private GridPane table;
	private HBox ownCards;
	private VBox leftHandSide, rightHandSide;
	private HBox oppositeSide;
	private Label trickLabel, playerLabel, leftHand, rightHand, opposite;
	private Region spacer, spacerTable, spacerRight, spacerOppo;
	
// etc.
	
	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		// create Panes and Controls
		this.root = new GridPane();
		
		leftHandSide = new VBox();
		rightHandSide = new VBox();
		oppositeSide = new HBox();
		leftHand = new Label("Spieler vor mir");
		rightHand = new Label("Spieler nach mir");
		opposite = new Label("Spieler gegenüber");
		spacer = new Region();
		spacerTable = new Region();
		spacer.setMinWidth(400);
		spacerTable.setMinWidth(200);
		spacerRight = new Region();
		spacerRight.setMinWidth(100);
		spacerOppo= new Region();
		spacerOppo.setMinWidth(400);
		
		leftHandSide.getChildren().add(leftHand);
		rightHandSide.getChildren().add(rightHand);
		oppositeSide.getChildren().add(opposite);
		
		table = new GridPane();
		ownCards = new HBox();
		ownCards.setMinHeight(300);
		ownCards.setMinWidth(1300);
		ownCards.getChildren().add(spacer);
		
		// image
		//String rank = Card.getRank();
		//String suit = Card.getSuit().toString();
		
 
		// create MenuBar - language and cardSet (fr vs. de) TODO
		
		
		// create hand (myCards)
		for (int i=0; i<9; i++) {
		      Rotate rotate = new Rotate();
		      String rank = "Bube";
		      String suit = "Ecke";
		      String filename = suit + "_" + rank + ".jpg";
		      System.out.println(filename);
		      Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));

		      Rectangle rectangle = new Rectangle();
		      rectangle.setHeight(514/2);
		      rectangle.setWidth(322/2);		
		      rectangle.setArcHeight(20);
		      rectangle.setArcWidth(20);
		      ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
		      rectangle.setFill(pattern);
		      rotate.setAngle(-10+2*i); 
		      rotate.setPivotX(150); 	
		      rotate.setPivotY(225); 
		      rectangle.getTransforms().addAll(rotate); 

		      ownCards.getChildren().add(rectangle);	
		}
		ownCards.setSpacing(-70.0);
		ownCards.setStyle("-fx-background-color: tomato");
		
		// create trick (table)
		for (int i = 0; i<4; i++) {
		Card[] trickcards = new Card[4];
			
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(259);
		rectangle.setWidth(161);		
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        Rank r1 = Rank.Ten;
		Suit s1 = Suit.Diamonds;
		Trump t1 = Trump.TopsDown;
		Card c1 = new Card(s1.toString(), "Ten", t1.toString());
		trickcards[i] = c1;
		String filename = s1.toStringFr() + "_" + r1.toStringDE() + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
        ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/2, 514/2, false);
        rectangle.setFill(pattern);
        rectangle.setId("cardimage");
        
	    // Roesti - implement rotation 
        Rotate rotate = new Rotate();  
        rotate.setAngle(((10+i) % 2)+1 *50); 
        rotate.setPivotX(150); 
	    rotate.setPivotY(225); 
	    rectangle.getTransforms().addAll(rotate); 	
		
	    if(i == 0)
	    	table.add(rectangle, 1, 0);
	    if(i==1)
	    	table.add(rectangle, 0, 1);
	    if(i==2)
	    	table.add(rectangle, 1, 2);
	    if(i==3) {
	    	table.add(rectangle, 2, 1);
	    }
		table.setStyle("-fx-background-color: gold");
		}
		
		// leftHandSide TODO magic number!
		for (int i= 0; i< 9; i++) {
		Rotate rotateLeft = new Rotate();
		Rectangle rectangleLeft = new Rectangle();
		rectangleLeft.setHeight(514/4);
		rectangleLeft.setWidth(322/4);		
        rectangleLeft.setArcHeight(20);
        rectangleLeft.setArcWidth(20);
        
		String filenameLeft = "Rückseite.jpg";
		Image imageLeft = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameLeft));
        ImagePattern patternLeft = new ImagePattern(imageLeft, 0, 0, 322/4, 514/4, false);
        rectangleLeft.setFill(patternLeft);
	    rotateLeft.setAngle(-270+5*i); 
	    rotateLeft.setPivotX(50); 	
	    rotateLeft.setPivotY(50); 
	    rectangleLeft.getTransforms().addAll(rotateLeft);      
        leftHandSide.getChildren().add(rectangleLeft);        
	}
		leftHandSide.setSpacing(-70.0);
		leftHandSide.setStyle("-fx-background-color: blue");
		leftHandSide.setMinWidth(200);
        
		// rightHandSide
		for (int i= 0; i< 9; i++) {
		Rotate rotateRight = new Rotate();
		Rectangle rectangleRight = new Rectangle();
		rectangleRight.setHeight(514/4);
		rectangleRight.setWidth(322/4);		
		rectangleRight.setArcHeight(20);
		rectangleRight.setArcWidth(20);
        
		String filenameRight = "Rückseite.jpg";
		Image imageRight = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameRight));
        ImagePattern patternRight = new ImagePattern(imageRight, 0, 0, 322/4, 514/4, false);
        rectangleRight.setFill(patternRight);
        
	    rotateRight.setAngle(-80+5*i); 
	    rotateRight.setPivotX(150); 	
	    rotateRight.setPivotY(150); 
	    rectangleRight.getTransforms().addAll(rotateRight);      
        rightHandSide.getChildren().add(rectangleRight);        
	}
		rightHandSide.setSpacing(-70.0);
		rightHandSide.setMinWidth(200);
		rightHandSide.setStyle("-fx-background-color: blue");
        
		// oppositeHandSide
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
	    rotateOppo.setAngle(-10+5*i); 
	    rotateOppo.setPivotX(50); 	
	    rotateOppo.setPivotY(50); 
	    rectangleOppo.getTransforms().addAll(rotateOppo);      
        oppositeSide.getChildren().add(rectangleOppo);  
		}
		oppositeSide.setSpacing(-70.0);
		oppositeSide.setStyle("-fx-background-color: red");
		oppositeSide.setMinHeight(200);
		
		root.add(table, 1, 1);
		root.add(leftHandSide, 0, 1);
		root.add(rightHandSide, 2, 1);
		root.add(oppositeSide, 1, 0);
		root.add(ownCards, 1, 2);
		
//		root.getChildren().addAll(table, leftHandSide, rightHandSide, oppositeSide, ownCards);
//		root.setLeftAnchor(table, 100d);
//		root.setTopAnchor(table, 100d);
//		root.setLeftAnchor(leftHandSide, 10d);
//		root.setTopAnchor(leftHandSide, 40d);
//		root.setRightAnchor(rightHandSide, 10d);
//		root.setTopAnchor(rightHandSide, 100d);
//		root.setTopAnchor(oppositeSide, 10d);
//		root.setLeftAnchor(oppositeSide, 60d);
//		root.setBottomAnchor(ownCards, 10d);
//		root.setLeftAnchor(ownCards, 60d);
		
		Scene scene = new Scene(root);
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

}
