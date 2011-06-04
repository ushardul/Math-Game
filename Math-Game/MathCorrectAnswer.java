/*      Variable dictionary (for variables that are located in the MathCorrectAnswer class):
	correctanswer - holds the correctanswer
	dead - holds whether the correct answer is dead or not
	correctanswer_x - holds the x value of the correct answer
	correctanswer_y - holds the y value of the correct answer
	methd - object used to access methods of the MathGameMain class
*/

class MathCorrectAnswer
{
    //Variables for the class
    public String correctanswer = "";
    public boolean dead = false;
    public int correctanswer_x, correctanswer_y;
    //Create new object to access methods of MathGameMain
    public MathGameMain methd = new MathGameMain ();
    //MathCorrectAnswer - constructor for when the object is created
    //answer - the answer that is currently in use
    //width - the width of the screen
    public MathCorrectAnswer (int answer, int width)
    {
	//properly format the number
	correctanswer += methd.leading.format (answer);
	//get random position for it
	correctanswer_x = (int) (Math.random () * (width - 237)) + 100;
	correctanswer_y = (int) (Math.random () * 50) - 50;

    }


    public void Death (int answer, int width)
    {
	//if answer is dead
	if (dead == true)
	{
	    correctanswer = "";
	    //properly format the number
	    correctanswer += methd.leading.format (answer);
	    //get random position for it
	    correctanswer_x = (int) (Math.random () * (width - 237)) + 100;
	    correctanswer_y = (int) (Math.random () * 50) - 50;
	}
    }
}