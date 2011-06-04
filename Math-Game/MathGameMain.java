/*                              Class: ICS 3M1
				Made by: Shardul Upadhyay
				Teacher: Mrs. Karasinska
				Due: January 19th, 2009
				The Math Game

				The Math Game allows the user to enhance their mathematics ability. To play, the user shoots numbers falling
				down the screen and must shoot the correct answer. The numbers fall down at an increasing speed as the game
				progresses.

				Variable dictionary (for variables that are located in the MathGameMain class):
				width - holds the width of the applet
				height - holds the height of the applet
				num1 - holds the first number in the question
				num2 - holds the second number in the question
				answer - holds the integer answer of the question
				enemy - an array of the incorrect numbers as an object
				corans - an object of the correct answer
				enemies - holds the number of incorrect answers that fall down
				t - the thread that executes
				operation - holds which type of operation the question will be
				mouse_x - holds the x value of the mouse
				mouse_y - holds the y value of the mouse
				btn - holds the state of button of the mouse
				timer - holds the timer for shooting
				score - the score of the player
				speed - the speed of the enemies falling down
				level - the level that the player is in
				explode - the object for the explosion
				draw_explosion - draw the explosion or not
				start_explosion - start drawing the explosion
				health - health of the player
				show - which stage the program is in
				won - if the game has been won or not
				toolkit - holds the toolkit for importing images
				cursor_image - holds the cursor image
				center - holds the point for the center of the game
				cursor - holds the unlocked cursor
				cursorlocked_image - holds the locked cursor image
				cursor_locked - holds the locked cursor
				background - the sides for the GUI image
				instructions - the instruction screen image
				backbuffer - the backbuffer image that is drawn to
				backg - where everything is drawn
				graphics_font - holds the font for drawing the GUI
				number_font - holds the font for drawing the number
				smiley - holds the smiley image
				leading - formats the number to always have two digits
*/

//Import statements
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.text.*;

public class MathGameMain extends Applet implements MouseMotionListener, MouseListener, KeyListener, Runnable
{
    //Variables for applet
    int width, height, num1, num2, answer;
    //Answer objects
    public MathIncorrectNumbers enemy[];
    public MathCorrectAnswer corans;
    public int enemies = 0;
    public Thread t;
    public int operation;
    public int mouse_x = 0, mouse_y = 0;
    public boolean btn = false;
    public int timer = 50;
    public int score = 0;
    public double speed = 1.2;
    public int level = 0;
    public MathExplosion explode;
    public boolean draw_explosion = false, start_explosion = false;
    public int health = 100;
    public int show = 0;
    public boolean won = false;
    //Images for cursor
    Toolkit toolkit = Toolkit.getDefaultToolkit ();
    Image cursor_image = toolkit.getImage ("MathCursor.gif");
    Point center = new Point (15, 15);
    Cursor cursor = toolkit.createCustomCursor (cursor_image, center, "Cursor");
    Image cursorlocked_image = toolkit.getImage ("MathCursor_locked.gif");
    Cursor cursor_locked = toolkit.createCustomCursor (cursorlocked_image, center, "Cursor Locked");
    //Images for GUI
    Image background = Toolkit.getDefaultToolkit ().getImage ("Mathbackground.gif");
    Image instructions = Toolkit.getDefaultToolkit ().getImage ("Mathinstructions.gif");
    Image smiley = Toolkit.getDefaultToolkit ().getImage ("Smiley.gif");
    //For drawing to screen
    Image backbuffer;
    Graphics backg;
    //Fonts and number formatting
    public Font graphics_font = new Font ("Calibri", Font.PLAIN, 20);
    public Font number_font = new Font ("Calibri", Font.BOLD, 34);
    DecimalFormat leading = new DecimalFormat ("#00");

    public void init ()
    {
	//Sets size of applet
	setSize (800, 600);
	//Gets height and width
	width = getSize ().width;
	height = getSize ().height;
	//Sets background
	setBackground (Color.black);
	//Starts thread
	t = new Thread (this);
	t.start ();
	//Registers listeners for recieving input in this applet
	addKeyListener (this);
	addMouseListener (this);
	addMouseMotionListener (this);
	//Creates image for buffering
	backbuffer = createImage (width, height);
	backg = backbuffer.getGraphics ();
    }


    public void keyReleased (KeyEvent e)
    {
	//If stage of applet is that the user has lost or won
	if (show == 2)
	{
	    //If user pressed y set it to game stage
	    if (e.getKeyCode () == e.VK_Y)
	    {
		show = 1;
	    }
	    //If user pressed n set it to quiting stage
	    else if (e.getKeyCode () == e.VK_N)
	    {
		show = 3;
		setSize (552, 537);
	    }
	}
    }


    public void mousePressed (MouseEvent e)
    {
	//go to next stage
	if (show == 0)
	{
	    show = 1;
	}
	//if it is game stage set btn to true
	if (show == 1)
	{
	    btn = true;
	}
    }


    public void mouseReleased (MouseEvent e)
    {
	//set button to false if mouse button has been released
	if (show == 1)
	{
	    btn = false;
	}
    }





    public void mouseMoved (MouseEvent e)
    {
	//Gets mouse coordinates
	mouse_x = e.getX ();
	mouse_y = e.getY ();
    }


    public void mouseDragged (MouseEvent e)
    {
	//Gets mouse coordinates
	mouse_x = e.getX ();
	mouse_y = e.getY ();
    }


    //Unused methods required to be implemented
    public void keyTyped (KeyEvent e)
    {
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void keyPressed (KeyEvent e)
    {
    }


    //Thread run
    public void run ()
    {
	//Tries execution
	try
	{
	    do
	    {
		//If stage is instruction stage
		while (show == 0)
		{
		    repaint ();
		}
		//Sets all values to default values
		MathOperationGenerate ((int) (Math.random () * 4));
		enemies = 0;
		timer = 50;
		speed = 1.2;
		level = 0;
		health = 100;
		score = 0;
		draw_explosion = false;
		start_explosion = false;
		btn = false;
		//If stage is game stage
		while (show == 1)
		{
		    //for loop for array of enemy objects
		    for (int i = 0 ; i < enemies ; i++)
		    {
			//makes enemies fall down
			enemy [i].incorrectanswer_y += speed;
			//Check if answer has been clicked
			IncorrectAnswerClicked (i);
			//Check if answer should respawn
			IncorrectAnswerRespawn (i);
		    }
		    //If cursor is inside an enemy set it to locked
		    if (CursorSet () == true)
		    {
			setCursor (cursor_locked);
		    }
		    //If cursor is not inside an enemy set it not locked
		    else
		    {
			setCursor (cursor);
		    }
		    //If level is 0
		    if (level == 0)
		    {
			//Check method
			LevelControl (-1);
		    }
		    //If level is 1
		    if (level == 1)
		    {
			//Check method
			LevelControl (119);
		    }
		    //If level is 2
		    if (level == 2)
		    {
			//Check method
			LevelControl (299);
		    }
		    //If level is 3
		    if (level == 3)
		    {
			//Check method
			LevelControl (539);
		    }
		    //If level is 4
		    if (level == 4)
		    {
			//Check method
			LevelControl (839);
		    }
		    //If level is 5
		    if (level == 5)
		    {
			//Check method
			LevelControl (1179);
		    }
		    //If level is 6
		    if (level == 6)
		    {
			//Check method
			LevelControl (1649);
		    }
		    //If level is 7
		    if (level == 7)
		    {
			//Check method
			LevelControl (2399);
		    }
		    //If level is 8
		    if (level == 8)
		    {
			//Check method
			LevelControl (3149);
		    }
		    //If level is 9
		    if (level == 9)
		    {
			//Go to finish screen
			show = 2;
			//They have won
			won = true;
		    }
		    //Moves correct answer down
		    corans.correctanswer_y += speed;
		    //Respawns answer
		    CorrectAnswerRespawn ();
		    //Checks if answer is clicked
		    CorrectAnswerClicked ();
		    //Check if answer are colliding
		    CheckColliding ();
		    //Reload shooting
		    Reload ();
		    //Calls repaint method and holds execution of thread
		    repaint ();
		    t.sleep (33);
		    //If health is less than 0, then user loses
		    if (health <= 0)
		    {
			//Move to finish screen
			show = 2;
			won = false;
		    }
		}
		//If it is finish screen
		while (show == 2)
		{
		    //Repaint screen
		    repaint ();
		}
		//If it is exit screen
		if (show == 3)
		{
		    //Repaint screen
		    repaint ();
		}
	    }
	    while (show != 3);
	}
	//Catch exception
	catch (InterruptedException e)
	{
	}
    }


    //Update method with Graphics has a parameter
    public void update (Graphics g)
    {
	//If it is instruction screen
	if (show == 0)
	{
	    //Draw instruction image
	    backg.drawImage (instructions, 0, 0, this);
	    //Draw backbuffer image
	    g.drawImage (backbuffer, 0, 0, this);
	    //Clear screen
	    backg.clearRect (0, 0, width, height);
	}
	//If it is game screen
	if (show == 1)
	{
	    //Set color to red and change font
	    backg.setColor (Color.red);
	    backg.setFont (number_font);
	    //For number of enemies
	    for (int i = 0 ; i < enemies ; i++)
	    {
		//Draw enemies
		if (enemy [i] != null)
		{
		    backg.drawString ("" + enemy [i].incorrectanswer_string, enemy [i].incorrectanswer_x, enemy [i].incorrectanswer_y);
		}
	    }
	    //Draw correct answer
	    if (corans != null)
	    {
		backg.drawString (corans.correctanswer, corans.correctanswer_x, corans.correctanswer_y);
	    }
	    //Draw explosion
	    DrawExplosion ();
	    //Draws side images
	    backg.drawImage (background, 0, 0, this);
	    //Changes color and sets font
	    backg.setColor (new Color (0, 11, 97));
	    backg.setFont (new Font ("Calibri", Font.BOLD, 19));
	    //Draws timer state according to value
	    if (timer < 50)
	    {
		//Draw reloading string
		backg.drawString ("RELOADING", 0, 180);
	    }
	    else
	    {
		//Draws ready to fire string
		backg.drawString ("READY TO", 0, 180);
		backg.drawString ("FIRE", 0, 200);
	    }
	    //Puts low health warning
	    if (health < 31)
	    {
		backg.drawString ("WARNING", 705, 180);
		backg.drawString ("LOW", 705, 200);
		backg.drawString ("HEALTH", 705, 220);
	    }
	    //Sets font
	    backg.setFont (new Font ("Calibri", Font.BOLD, 40));
	    //If it is addition operation
	    if (operation == 0)
	    {
		backg.drawString ("" + num1 + " + " + num2 + " = ???", 335, height - 37);
	    }
	    //If it is subtraction operation
	    if (operation == 1)
	    {
		backg.drawString ("" + num1 + " - " + num2 + " = ???", 335, height - 37);
	    }
	    //If it is multiplication operation
	    if (operation == 2)
	    {
		backg.drawString ("" + num1 + " x " + num2 + " = ???", 335, height - 37);
	    }
	    //If it is division operation
	    if (operation == 3)
	    {
		backg.drawString ("" + num1 + " ÷ " + num2 + " = ???", 335, height - 37);
	    }
	    //Draws score
	    backg.drawString ("" + score, 15, 350);
	    //Draws reload timer
	    backg.fillRect (20, 125, timer, 10);
	    backg.fillRect (710, 125, (int) (health / 1.2), 10);
	    backg.drawRect (709, 124, 83, 11);
	    //Draws backbuffer image
	    g.drawImage (backbuffer, 0, 0, this);
	    //Clears screen
	    backg.clearRect (0, 0, width, height);
	}
	//If it is finished screen
	if (show == 2)
	{
	    //Changes color and font
	    backg.setColor (Color.pink);
	    backg.setFont (new Font ("Calibri", Font.BOLD, 40));
	    //If user has won draw winning screen
	    if (won == true)
	    {
		backg.drawString ("CONGRATULATIONS!", 100, 200);
		backg.drawString ("YOU WIN!", 100, 250);
	    }
	    //If user has lost, draw losing screen
	    if (won == false)
	    {
		backg.drawString ("YOU LOSE WITH " + score + " POINTS", 100, 200);
		backg.drawString ("BETTER LUCK NEXT TIME", 100, 250);
	    }
	    //Draw question to play again or not
	    backg.drawString ("PRESS Y TO PLAY AGAIN OR N TO EXIT", 100, 300);
	    //Draws backbuffer image
	    g.drawImage (backbuffer, 0, 0, this);
	    //Clears the screen
	    backg.clearRect (0, 0, width, height);
	}
	if (show == 3)
	{
	    setBackground (Color.white);
	    backg.drawImage (smiley, 0, 0, this);
	    //Change color
	    backg.setColor (Color.red);
	    //Put goodbye
	    backg.drawString ("GOODBYE", 100, 200);
	    //Draw backbuffer image
	    g.drawImage (backbuffer, 0, 0, this);
	}
    }


    public void paint (Graphics g)
    {
	//Calls update method
	update (g);
    }


    //Method draws explosion
    public void DrawExplosion ()
    {
	//If enemy is exploding
	if (draw_explosion == true)
	{
	    //Draw oval and increase radius
	    backg.drawOval (explode.x - (explode.rad), explode.y - (explode.rad), explode.rad * 2, explode.rad * 2);
	    explode.rad++;
	}
	//If enemy is not exploding and explosion is too big
	if (draw_explosion == true && explode.rad > 35)
	{
	    //Stop drawing explosion
	    draw_explosion = false;
	    explode = null;
	}
    }


    //Generate random number with random operation
    public void MathOperationGenerate (int random)
    {
	//If random operation is 0
	if (random == 0)
	{
	    //Generate random numbers and store them in variables
	    num1 = (int) (Math.random () * 49);
	    num2 = (int) (Math.random () * 49);
	    answer = num1 + num2;
	    operation = 0;
	}
	//If random operation is 1
	else if (random == 1)
	{
	    //Generate random numbers and store them in variables and make sure answer is not negative value
	    do
	    {
		num1 = (int) (Math.random () * 99);
		num2 = (int) (Math.random () * 99);
	    }
	    while (num1 < num2);
	    //Set answer
	    answer = num1 - num2;
	    operation = 1;
	}
	//If random operation is 2
	else if (random == 2)
	{
	    //Generate random numbers and store them in variables with answer
	    num1 = (int) (Math.random () * 9);
	    num2 = (int) (Math.random () * 9);
	    answer = num1 * num2;
	    operation = 2;
	}
	//If random operation is 3
	else if (random == 3)
	{
	    //Do while random numbers do not generate a decimal
	    do
	    {
		//Do while second random number is not 0 and answer is less than 1
		do
		{
		    num1 = (int) (Math.random () * 99);
		    num2 = (int) (Math.random () * 9) + 1;
		}
		while (num2 == 0 || num1 < num2);
	    }
	    while (num1 % num2 != 0);
	    //Stores answer and operations
	    answer = num1 / num2;
	    operation = 3;
	}
    }


    //InitalizeFill with proper values
    public void InitalizeFill ()
    {
	//Creates array of enemies
	enemy = new MathIncorrectNumbers [enemies];
	//Gives value to enemy objects
	for (int i = 0 ; i < enemies ; i++)
	{
	    enemy [i] = new MathIncorrectNumbers (leading.format (answer), width);
	}
	//Creates correct answer object
	corans = new MathCorrectAnswer (answer, width);
    }


    //CursorSet - checks if the mouse is inside enemies or correct answer
    public boolean CursorSet ()
    {
	//For each enemy
	for (int i = 0 ; i < enemies ; i++)
	{
	    //If enemy is inside then return true
	    if (mouse_x > enemy [i].incorrectanswer_x - 3 && mouse_y > (enemy [i].incorrectanswer_y - 25) && mouse_x < (enemy [i].incorrectanswer_x + 37) && mouse_y < enemy [i].incorrectanswer_y + 3)
	    {
		return true;
	    }
	}
	if (corans != null)
	{
	    //If enemy is inside correct answer return true
	    if (mouse_x > corans.correctanswer_x - 3 && mouse_y > (corans.correctanswer_y - 25) && mouse_x < (corans.correctanswer_x + 37) && mouse_y < corans.correctanswer_y + 3)
	    {
		return true;
	    }
	}
	//Return false if cursor is not inside anything
	return false;
    }


    //Reload - controls reloading of shooting
    public void Reload ()
    {
	//If timer is less than 0 then increase value
	if (timer < 50)
	{
	    timer++;
	}
	//If button is pressed and timer is full then set it to 0
	if (btn == true && timer == 50)
	{
	    timer = 0;
	}
    }


    //CheckColliding - Checks if enemies are colliding
    public void CheckColliding ()
    {
	//For each enemy
	for (int i = 0 ; i < enemies ; i++)
	{
	    //Compare against other enemies
	    for (int i2 = 0 ; i2 < enemies ; i2++)
	    {
		//If enemies are inside each other than change their places
		if (Inside (enemy [i].incorrectanswer_x - 3, 40, enemy [i2].incorrectanswer_x - 3, 40) == true && i != i2)

		    {
			enemy [i].incorrectanswer_x = (int) (Math.random () * (width - 237)) + 100;
			enemy [i].incorrectanswer_y = (int) (Math.random () * 50) - 50;
		    }
	    }
	}
	//For each enemy
	for (int i = 0 ; i < enemies ; i++)
	{
	    //If enemies are inside correct answer get new place
	    if (Inside (enemy [i].incorrectanswer_x - 3, 40, corans.correctanswer_x - 3, 40) == true)
	    {
		corans.correctanswer_x = (int) (Math.random () * (width - 237)) + 100;
		corans.correctanswer_y = (int) (Math.random () * 50) - 50;
	    }
	}
    }


    //Inside - checks if two boxes are colliding
    //x1 - first x value
    //width1 - first width value
    //x2 - second x value
    //width2 - second x value
    public boolean Inside (int x1, int width1, int x2, int width2)
    {
	//If boxes are inside each other return true
	if (x1 < (x2 + width2) && (x1 + width1) > x2)
	{
	    return true;
	}
	//Otherwise return false
	return false;
    }


    //CorrectAnswerRespawn - respawns correct answer
    public void CorrectAnswerRespawn ()
    {
	//If answer y is outside spawning area
	if (corans.correctanswer_y > (height + 30))
	{
	    //create new answer
	    corans.correctanswer = "";
	    MathOperationGenerate ((int) (Math.random () * 4));
	    corans.correctanswer += leading.format (answer);
	    corans.correctanswer_x = (int) (Math.random () * (width - 237)) + 100;
	    corans.correctanswer_y = (int) (Math.random () * 50) - 50;
	    //put down health
	    health -= 10;
	    //Reset all enemies as well
	    for (int i = 0 ; i < enemies ; i++)
	    {
		enemy [i].incorrectanswer_dead = true;
		enemy [i].Death (leading.format (answer), width);
	    }
	}
    }


    //IncorrectAnswerRespawn - respawns incorrect answer
    //i - index of the array
    public void IncorrectAnswerRespawn (int i)
    {
	//if incorrect answer is outside spawning area
	if (enemy [i].incorrectanswer_y > (height + 30))
	{
	    //create new incorrect answer
	    enemy [i].incorrectanswer_dead = true;
	    enemy [i].Death (leading.format (answer), width);
	    enemy [i].incorrectanswer_x = (int) (Math.random () * (width - 237)) + 100;
	    enemy [i].incorrectanswer_y = (int) (Math.random () * 50) - 50;
	}
    }


    //IncorrectAnswerClicked - checks if any incorrect answer has been clicked
    //i - index of the array
    public void IncorrectAnswerClicked (int i)
    {
	//if mouse is inside box of incorrect answer
	if (mouse_x > enemy [i].incorrectanswer_x - 3 && mouse_y > (enemy [i].incorrectanswer_y - 25) && mouse_x < (enemy [i].incorrectanswer_x + 37) && mouse_y < enemy [i].incorrectanswer_y + 3 && btn == true && timer == 50)
	{
	    //take health
	    health -= 10;
	    //start drawing explosion
	    draw_explosion = true;
	    //create new explosion object
	    explode = new MathExplosion (enemy [i].incorrectanswer_x + 15, enemy [i].incorrectanswer_y - 7, 5);
	    //kill incorrect answer and create a new one
	    enemy [i].incorrectanswer_dead = true;
	    enemy [i].Death (leading.format (answer), width);
	}
    }


    //CorrectAnswerClicked - checks if correct answer has been clicked
    public void CorrectAnswerClicked ()
    {
	//if mouse is inside correct answer
	if (mouse_x > corans.correctanswer_x - 3 && mouse_y > (corans.correctanswer_y - 25) && mouse_x < (corans.correctanswer_x + 37) && mouse_y < corans.correctanswer_y + 3 && btn == true && timer == 50)
	{
	    //generate new equation
	    MathOperationGenerate ((int) (Math.random () * 4));
	    //kill old correct answer
	    corans.dead = true;
	    //draw explosion
	    draw_explosion = true;
	    //create new explosion object
	    explode = new MathExplosion (corans.correctanswer_x + 17, corans.correctanswer_y - 7, 5);
	    //call corans object's method
	    corans.Death (answer, width);
	    //add score
	    score += 30;
	    //kill all other incorrect answers
	    for (int i = 0 ; i < enemies ; i++)
	    {
		enemy [i].incorrectanswer_dead = true;
		enemy [i].Death (leading.format (answer), width);
	    }
	}
    }


    //LevelControl - controls the movement of the levels
    //reqscore - the required score for the next level
    public void LevelControl (int reqscore)
    {
	//if the score is more than the required score
	if (score > reqscore)
	{
	    //add speed to falling down
	    speed += 0.6;
	    //one more enemy
	    enemies++;
	    //call InitalizeFill method
	    InitalizeFill ();
	    //change the level
	    level++;
	}
    }
}