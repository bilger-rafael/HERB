package herb.client.ui.game;

import javafx.stage.Stage;
import herb.client.ressources.Card;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;

public class GameView extends View<GameModel> {
	
	private BorderPane root; 
	private GridPane table;
	private HBox ownCards;
	private GridPane leftHandSide, oppositeSide, rightHandSide;
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
		this.root = new BorderPane();
		
		leftHandSide = new GridPane();
		rightHandSide = new GridPane();
		oppositeSide = new GridPane();
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
		
		leftHandSide.add(leftHand, 1, 1);
		rightHandSide.add(rightHand, 1, 1);
		oppositeSide.add(opposite, 1, 1);
		
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
		      rectangle.setHeight(259);
		      rectangle.setWidth(161);		
		      rectangle.setArcHeight(20);
		      rectangle.setArcWidth(20);
		      ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
		      rectangle.setFill(pattern);
		      rotate.setAngle(-10+2*i); 
//		      rotate.setPivotX(150); 	
//		      rotate.setPivotY(225); 
		      rotate.setPivotX(150);
		      rotate.setPivotY(100);
		      rectangle.getTransforms().addAll(rotate); 

		      ownCards.getChildren().add(rectangle);	
		}
		ownCards.setSpacing(-70.0);

		
		// create trick (table)
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(259);
		rectangle.setWidth(161);		
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        String rank = "Bube";
		String suit = "Kreuz";
		String filename = suit + "_" + rank + ".jpg";
		System.out.println(filename);
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
        ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/2, 514/2, false);
        rectangle.setFill(pattern);
        rectangle.setId("cardimage");
        
	    // Roesti - implement rotation 
        Rotate rotate = new Rotate();  
        rotate.setAngle(10); 
        rotate.setPivotX(150); 
	    rotate.setPivotY(225); 
	    rectangle.getTransforms().addAll(rotate); 	
	    table.add(spacerTable, 0, 0);
		table.add(rectangle, 3, 0);

		
		// leftHandSide
		Rectangle rectangleLeft = new Rectangle();
		rectangleLeft.setHeight(259);
		rectangleLeft.setWidth(161);		
        rectangleLeft.setArcHeight(20);
        rectangleLeft.setArcWidth(20);
        
		String filenameLeft = "Rückseite.jpg";
		Image imageLeft = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameLeft));
        ImagePattern patternLeft = new ImagePattern(imageLeft, 0, 0, 322/2, 514/2, false);
        rectangleLeft.setFill(patternLeft);
        leftHandSide.add(rectangleLeft, 1, 1);
		
		// rightHandSide
		Rectangle rectangleRight = new Rectangle();
		rectangleRight.setHeight(259);
		rectangleRight.setWidth(161);		
		rectangleRight.setArcHeight(20);
		rectangleRight.setArcWidth(20);
        
		String filenameRight = "Rückseite.jpg";
		Image imageRight = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameRight));
        ImagePattern patternRight = new ImagePattern(imageRight, 0, 0, 322/2, 514/2, false);
        rectangleRight.setFill(patternRight);
        rightHandSide.add(spacerRight, 0, 0);
        rightHandSide.add(rectangleRight, 1, 1);
        
		// oppositeHandSide
		Rectangle rectangleOppo = new Rectangle();
		rectangleOppo.setHeight(259);
		rectangleOppo.setWidth(161);		
		rectangleOppo.setArcHeight(20);
		rectangleOppo.setArcWidth(20);
        
		String filenameOppo = "Rückseite.jpg";
		Image imageOppo = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filenameOppo));
        ImagePattern patternOppo = new ImagePattern(imageOppo, 0, 0, 322/2, 514/2, false);
        rectangleOppo.setFill(patternOppo);
        oppositeSide.add(spacerOppo, 0, 0);
        oppositeSide.add(rectangleOppo, 1, 1);
        
        
		root.setCenter(table);
		root.setLeft(leftHandSide);
		root.setRight(rightHandSide);
		root.setTop(oppositeSide);
		root.setBottom(ownCards);
		
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
