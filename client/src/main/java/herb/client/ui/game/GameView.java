package herb.client.ui.game;

import javafx.stage.Stage;

import java.util.ArrayList;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
import herb.client.utils.ServiceLocator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import javafx.geometry.Insets;


public class GameView extends View<GameModel> {
	
	private AnchorPane root; 
	private GridPane table;
	private GridPane ownCards;
	private VBox leftHandSide, rightHandSide, opposite, bottom, names, points;
	private HBox oppositeSide, left, right, pointPane;
	private Label trickLabel, trickLabel2, playerLabel, leftHandLabel, rightHandLabel, oppositeLabel;
	private Label playerPoints, leftPoints, rightPoints, oppoPoints;
	private Region spacer, spacerTable, spacerTable2, spacerRight, spacerOppo;
	private BorderPane upperPart;
	private StackPane tablePart;
	private Rectangle rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9;
	private Rectangle[] rects;
	private Card[] cardAreas;
	private ArrayList<Card> cardAreas1;


	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("Application view initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		// get players, myCards and my playable cards from server (GameModel)  TODO
		
		// Roesti - get lobby players
    	Player[] plys = new Player[4];
    	plys = model.getLobbyPlayers();
	
		// Roesti - card images from herb / client / ui / images / fr OR de TODO
//		cardAreas = new Card[9];
//		cardAreas = model.getMyCards();
    	cardAreas1 = new ArrayList();
    	cardAreas1 = model.getMyCards();
		
		Card[] trickCardAreas = new Card[4];
		trickCardAreas = model.getTrickCards();
		
	    rects = new Rectangle[cardAreas1.size()];
		
		// Roesti - create gui elements
		this.root = new AnchorPane();
		
		upperPart = new BorderPane();
		tablePart = new StackPane();
		left = new HBox();
		right = new HBox();
		opposite = new VBox();
		bottom = new VBox();
		leftHandSide = new VBox();
		rightHandSide = new VBox();
		oppositeSide = new HBox();
		table = new GridPane();
		ownCards = new GridPane();
		pointPane = new HBox();
		names = new VBox();
		points = new VBox();
		
		ownCards.setMinHeight(300);
		ownCards.setMinWidth(1300);
		
		leftHandLabel = new Label(plys[3].getUsername());
		rightHandLabel = new Label(plys[1].getUsername());
		oppositeLabel = new Label(plys[2].getUsername());
		trickLabel2 = new Label("Oh come and play with me...   Lets play till we go crazy");
		trickLabel = new Label();
		trickLabel.setMinHeight(70);
		playerLabel = new Label(plys[0].getUsername());
		playerLabel.setMinHeight(20);
		playerLabel.setTextAlignment(TextAlignment.CENTER);
		
		spacer = new Region();
		spacerTable = new Region();
		spacerTable2 = new Region();
		spacer.setMinWidth(670d);
		spacerTable.setMinWidth(200d);
		spacerTable2.setMinWidth(200d);
		spacerRight = new Region();
	//	spacerRight.setMinWidth(100d);
		spacerOppo= new Region();
		spacerOppo.setMinWidth(300d);
		
		playerPoints = new Label("77");
		leftPoints = new Label("88");
		rightPoints = new Label("99");
		oppoPoints = new Label("111");
		
	
		// create MenuBar - language and cardSet (fr vs. de) TODO
		
	

		
		// show, if it works
//		String writeCardsOut = "Jetzt";
//		for (int i = 0; i < 9; i++) {
//			writeCardsOut += cardAreas[i].getSuit();
//			writeCardsOut += cardAreas[i].getRank();	
//			} System.out.println(writeCardsOut);
		
		
		// Roesti - create hand: ownCards GridPane
		for (int i=0; i< cardAreas1.size(); i++) {
			String rank = ""+cardAreas1.get(i).getRank().toStringDE();
			String suit = ""+cardAreas1.get(i).getSuit().toStringFr();
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
		    rectangleCard.setStroke(Color.BROWN);
		     
		      //  fan out cards
		    Rotate rotate = new Rotate();
		    rotate.setAngle(-40+10*i); 
		    rotate.setPivotX(322/2/2); 	
		    rotate.setPivotY(257*2.2);
		    rectangleCard.getTransforms().addAll(rotate);   
		    ownCards.add(rectangleCard, i+1, 1);	
		    rects[i] =rectangleCard;  
		}
		rect1 = rects[0];
		rect2 = rects[1];
		rect3 = rects[2];
		rect4 = rects[3];
		rect5 = rects[4];
		rect6 = rects[5];
		rect7 = rects[6];
		rect8 = rects[7];
		rect9 = rects[8];
		

		ownCards.setMinHeight(250);
		ownCards.setHgap(-150);
		bottom.setStyle("-fx-background-color: beige");
		bottom.getChildren().add(playerLabel);
		bottom.getChildren().add(ownCards);
		bottom.setAlignment(Pos.CENTER);


		// center Cards
		ownCards.getChildren().add(spacer);
		
		// Roesti - create trick: table GridPane 
	    trickLabel.setStyle("-fx-font-weight: bold");
	    table.add(trickLabel, 1, 0, 1, 1);
	    table.add(spacerTable, 0, 0, 1, 1);
	    table.add(spacerTable2, 3, 0, 1, 1);
		
		for (int i = 0; i<4; i++) {
	//	Card[] trickcards = new Card[4];
			
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(514/3);
		rectangle.setWidth(322/3);		
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

		String r1 = ""+trickCardAreas[i].getRank().toStringDE();
		String s1 = ""+trickCardAreas[i].getSuit().toStringFr();
		Trump t1 = Trump.TopsDown;
		String filename = s1 + "_" + r1 + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
        ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/3, 514/3, false);
        rectangle.setFill(pattern);
        rectangle.setId("cardimage");
        
	    // better ImagePattern or ImageView? TODO
//        ImageView imView = new ImageView(imageTable);
//        imView.setPreserveRatio(true);
        
	    // Roesti - implement rotation  TODO implement Random()
        Rotate rotate = new Rotate();  
        rotate.setAngle(((10+i) % 2)+183-2*i); 
        rotate.setPivotX(30); 
        rotate.setPivotY(322/3+30); 
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
		leftHandSide.setMaxHeight(400);
		left.getChildren().add(leftHandSide);
		left.getChildren().add(leftHandLabel);
	    left.setSpacing(10.0);
		left.setAlignment(Pos.CENTER_RIGHT);
        
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
	//    rightHandLabel.setTextAlignment(TextAlignment.CENTER);
		right.getChildren().add(rightHandSide);
		right.setSpacing(10.0);
		right.setPadding(new Insets(10, 10, 10, 10));
		right.setAlignment(Pos.CENTER_RIGHT);
        
		// oppositeHandSide - cards in VBox, with name in HBox
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
		oppositeSide.setMinHeight(100);
		opposite.getChildren().add(oppositeSide);
		opposite.getChildren().add(oppositeLabel);	
		opposite.setAlignment(Pos.CENTER);
		
		//BorderPane
		upperPart.setCenter(table);
		upperPart.setBottom(trickLabel2);
		upperPart.setTop(opposite);
		upperPart.setMinWidth(400d);
		
		// realise with StackPane? TODO
		// realise with Binding TODO
		//names.getChildren().addAll(oppositeLabel, leftHandLabel, rightHandLabel, playerLabel);
		names.setMinSize(80, 20);
		points.getChildren().addAll(oppoPoints, leftPoints, rightPoints, playerPoints);
		points.setMinSize(40, 20);
		points.setAlignment(Pos.CENTER_RIGHT);
		pointPane.getChildren().addAll(names, points);
		pointPane.toFront();
		pointPane.setPadding(new Insets(20, 20, 20, 20));
		pointPane.setStyle("-fx-background-color: aliceblue");

		
		//AnchorPane
		root.getChildren().addAll(upperPart, bottom, left, right, pointPane);
		root.setLeftAnchor(left, -10d);
		root.setTopAnchor(left, 70d);
		root.setBottomAnchor(left, 200d);
		root.setRightAnchor(right, -10d);
		root.setTopAnchor(right, 70d);
		root.setBottomAnchor(right, 200d);

		root.setStyle("-fx-background-color: darksalmon");
//		upperPart.setStyle("-fx-background-color: red");
		root.setTopAnchor(upperPart, -10d);
		root.setLeftAnchor(upperPart, 200d);
		root.setRightAnchor(upperPart, 200d);
	//	root.setBottomAnchor(upperPart, 200d);
		
		root.setBottomAnchor(bottom, 10d);
		root.setLeftAnchor(bottom, 10d);
		root.setRightAnchor(bottom, 10d);
		
		root.setTopAnchor(pointPane, 10d);
		root.setRightAnchor(pointPane, 10d);
	
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
	
	// Roesti - chosen card sending back to controller
	public Rectangle getPlayedCard1() {
		return rect1;
	}
	public Rectangle getPlayedCard2() {
		return rect2;
	}
	public Rectangle getPlayedCard3() {
		return rect3;
	}
	public Rectangle getPlayedCard4() {
		return rect4;
	}
	public Rectangle getPlayedCard5() {
		return rect5;
	}
	public Rectangle getPlayedCard6() {
		return rect6;
	}
	public Rectangle getPlayedCard7() {
		return rect7;
	}
	public Rectangle getPlayedCard8() {
		return rect8;
	}
	public Rectangle getPlayedCard9() {
		return rect9;
	}
	
	public Card[] getCardAreas() {
		return cardAreas;
	}
	public ArrayList<Card> getCardAreas1(){
		return cardAreas1;
	}
	
	// evtl. mit Liste
	public Rectangle[] getRects() {
		return rects;
	}
}
