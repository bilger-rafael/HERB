package herb.client.ui.game;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import herb.client.ressources.Card;
import herb.client.ressources.Player;
import herb.client.ressources.core.Rank;
import herb.client.ressources.core.Suit;
import herb.client.ressources.core.Trump;
import herb.client.ui.core.View;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;


public class GameView extends View<GameModel> {
	
	//private BorderPane root;
	private AnchorPane root; 
	private GridPane table, ownCards;
	private VBox leftHandSide, rightHandSide, opposite, bottom, names, points;
	private HBox oppositeSide, left, right, pointPane;
	private HBox tMain, tRight, tOppo, tLeft;
	private Label trickLabel, trickLabel2, playerLabel, leftHandLabel, rightHandLabel, oppositeLabel, nextPlayerLabel;
	private Label playerPoints, leftPoints, rightPoints, oppoPoints, pointsLabel, trumpLabel;
	private Region spacer, spacerTable, spacerTable2, spacerRight, spacerOppo;
	private BorderPane upperPart;
	private StackPane tablePart;
	private Rectangle rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9;
	private ArrayList<Rectangle> rects;
	private ArrayList<Card> cardAreas, playables;
	private Button simButton;
	private ArrayList<Player> plys;
	private MenuBar headMenu;
	private Menu menuLanguage;
	private ArrayList<Card> cards;


	public GameView(Stage stage, GameModel model) {
		super(stage, model);
		stage.setTitle("HERB-Jass > Spieltisch");
		ServiceLocator.getInstance().getLogger().info("GameView initialized");
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getInstance();
		
		this.root = new AnchorPane();
		
		// create MenuBar - language and cardSet (fr vs. de) TODO
		headMenu = new MenuBar();
		menuLanguage = new Menu();	
		menuLanguage.getItems().addAll();
				
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
		headMenu.getMenus().addAll(menuLanguage);
		
		// Roesti - create ui-elements
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
		ownCards.setMinHeight(300);
		ownCards.setMinWidth(1300);
		pointPane = new HBox();
		names = new VBox();
		points = new VBox();		

		tMain = new HBox();
		tRight = new HBox();
		tOppo = new HBox();
		tLeft = new HBox();
				
		spacer = new Region();
		spacerTable = new Region();
		spacerTable2 = new Region();
	//	spacer.setMinWidth(670d);
		spacer.setMinWidth(200d);
		spacerTable.setMinWidth(200d);
		spacerTable2.setMinWidth(200d);
		spacerRight = new Region();
		spacerOppo= new Region();
		spacerOppo.setMinWidth(300d);
		
		playerPoints = new Label("77");
		leftPoints = new Label("88");
		rightPoints = new Label("99");
		oppoPoints = new Label("111");
		
		simButton = new Button("simulation");
	
		// Roesti - get lobby players
		plys = model.getLobbyPlayers();
		
	    rects = new ArrayList();
		
		// Roesti - create labels
		playerLabel = new Label(plys.get(0).getUsername());
		playerLabel.setMinHeight(20);
		leftHandLabel = new Label(plys.get(3).getUsername());
		rightHandLabel = new Label(plys.get(1).getUsername());
		oppositeLabel = new Label(plys.get(2).getUsername());
		oppositeLabel.setMinHeight(60);
		trickLabel2 = new Label();
		trickLabel = new Label();
		trickLabel.setMinHeight(70);
		trumpLabel = new Label();
	    nextPlayerLabel = new Label(" next ---");
		
		
		setMyCards();
		
		trumpLabel.setText(cards.get(0).getTrump().toString());
		trumpLabel.setStyle("-fx-font-weight: bold");
		
		setTrick();
				
		updateLeftPlayer();
		updateRightPlayer();
        updateOppoPlayer();
       		
		//BorderPane opposite Player and Table
		upperPart.setCenter(table);
		upperPart.setBottom(nextPlayerLabel);
		upperPart.setTop(opposite);
		upperPart.setMinWidth(400d);
		
		updatePointPane();
		
		//AnchorPane
		root.getChildren().addAll(upperPart, bottom, left, right, pointPane, simButton, headMenu, trumpLabel);
		root.setLeftAnchor(headMenu, 0d);
		root.setTopAnchor(headMenu, 0d);
		root.setRightAnchor(headMenu, 0d);
		
		root.setLeftAnchor(left, -10d);
		root.setTopAnchor(left, 70d);
		root.setBottomAnchor(left, 200d);
		root.setRightAnchor(right, -10d);
		root.setTopAnchor(right, 70d);
		root.setBottomAnchor(right, 200d);

		root.setId("background");
		root.setTopAnchor(upperPart, -40d);
		root.setLeftAnchor(upperPart, 200d);
		root.setRightAnchor(upperPart, 200d);
		
		root.setBottomAnchor(bottom, 10d);
		root.setLeftAnchor(bottom, 10d);
		root.setRightAnchor(bottom, 10d);
		
		root.setTopAnchor(pointPane, 50d);
		root.setRightAnchor(pointPane, 10d);
		
		root.setTopAnchor(simButton, 50d);
		root.setLeftAnchor(simButton, 10d);
		root.setTopAnchor(trumpLabel, 90d);
		root.setLeftAnchor(trumpLabel, 30d);
	
		Scene scene = new Scene(root, 1000, 1000);
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
		bottom.getChildren().add(playerLabel);
		bottom.getChildren().add(ownCards);
		bottom.setAlignment(Pos.CENTER);
	    updatePlayables();

	}
	
	public void updatePlayables() {
	    for(int j= 0; j<cards.size();j++) {
	   		if (cards.get(j).isPlayable()) {
	    	rects.get(j).setStroke(Color.GOLD);
	    	}
	    }
	}
	
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
	}
	
	// Roesti - card images from herb / client / ui / images / fr OR de TODO	
	public void updateImagePatterns() {
    	cards = model.getMyCards(); 
		
		for (int d = 0; d<9; d++)   {		
			rects.get(d).setFill(null);
		}
		
		cards = model.getMyCards();
		
		for(int j=0; j<cards.size(); j++) {
		String rank = cards.get(j).getRank().toStringDE();
		String suit = cards.get(j).getSuit().toStringFr();
	    String filename = suit + "_" + rank + ".jpg";
	    Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
		
	    ImagePattern pattern = new ImagePattern(image, 0, 0, 322/2, 514/2, false);
	    rects.get(j).setFill(pattern);
	    
	    updatePlayables();
		}
	}
	
	// Roesti - create trick: table GridPane 
	public void updateTrick(ArrayList<Card> cardSet) {
		ArrayList<Card> trick = new ArrayList();
		trick = cardSet;
	    
		tOppo.getChildren().clear();
		tLeft.getChildren().clear();
		tMain.getChildren().clear();
		tRight.getChildren().clear();
	    	    
		// set played cards
		for (int i = 0; i< trick.size(); i++) {
		Rectangle rectangle = new Rectangle();
		rectangle.setHeight(514/3);
		rectangle.setWidth(322/3);		
		rectangle.setArcHeight(20);
		rectangle.setArcWidth(20);
		String r1 = trick.get(i).getRank().toStringDE();
		String s1 = trick.get(i).getSuit().toStringFr();
		Trump t1 = Trump.TopsDown;
		String filename = s1 + "_" + r1 + ".jpg";
		Image imageTable = new Image(this.getClass().getClassLoader().getResourceAsStream("herb/client/ui/images/fr/" + filename));
		ImagePattern pattern = new ImagePattern(imageTable, 0, 0, 322/3, 514/3, false);
		rectangle.setFill(pattern);
		rectangle.setId("cardimage");             
	    // Roesti - implement rotation  TODO implement Random()
//		Rotate rotate = new Rotate();  
//		rotate.setAngle(((10+i) % 2)+183-2*i); 
//		rotate.setPivotX(30); 
//		rotate.setPivotY(322/3+30); 
//	    rectangle.getTransforms().addAll(rotate); 			
	    if(i == 2)
	    	tOppo.getChildren().add(rectangle);
	    if(i==3)
	    	tLeft.getChildren().add(rectangle);
	    if(i==0)
	    	tMain.getChildren().add(rectangle);
	    if(i==1)
	    	tRight.getChildren().add(rectangle);
	    }
				
		table.setHgap(10);
		table.setVgap(10);
		table.setStyle("-fx-alignement: center");
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
	}
	
	private void updatePointPane() {
		// TODO 
		// realise with StackPane? TODO
		// realise with Binding TODO
		//names.getChildren().addAll(oppositeLabel, leftHandLabel, rightHandLabel, playerLabel);
		pointsLabel = new Label();
		names.setMinSize(80, 20);
		points.getChildren().addAll(oppoPoints, leftPoints, rightPoints, playerPoints);
		points.setMinSize(40, 20);
		points.setAlignment(Pos.CENTER_RIGHT);
		pointPane.getChildren().addAll(pointsLabel, names, points);
		pointPane.toFront();
		pointPane.setPadding(new Insets(20, 20, 20, 20));
		pointPane.setStyle("-fx-background-color: aliceblue");
	}
	
	public void setNextPlayerLabel(String s) {
		nextPlayerLabel.setText(s);
	}
	
	// Roesti 
	protected void updateLabels() {
		Translator t = ServiceLocator.getInstance().getTranslator();
		
		// language settings
		menuLanguage.setText(t.getString("program.game.menuLanguage"));
		// screen labels
		trickLabel2.setText(t.getString("program.game.order"));
		pointsLabel.setText(t.getString("program.game.pointsLabel"));
		stage.setTitle(t.getString("program.name"));
		// tbd
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
	public Button getSimulationButton() {
		return simButton;
	}
}
