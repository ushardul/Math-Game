
/*      Variable dictionary (for variables that are located in the MathIncorrectNumbers class):
	incorrectanswer_string - holds the incorrect answer
	incorrectanswer_dead - holds whether the incorrect answer is dead or not
	incorrectanswer_x - holds the x value of the incorrect answer
	incorrectanswer_y - holds the y value of the incorrect answer
	methd - object used to access methods of the MathGameMain class
	randomnum1 - used to hold the first random digit
	randomnum2 - used to hold the second random digit
*/

class MathIncorrectNumbers
{
    //Variables for the class
    public int incorrectanswer_x, incorrectanswer_y;
    public String incorrectanswer_string;
    public boolean incorrectanswer_dead;
    public MathGameMain methd = new MathGameMain ();

    //MathIncorrectNumbers - constructor for the incorrect answer objects
    //correctanswer - gets a copy of the correct answer to check so that it does not match
    //width - the width of the screen
    public MathIncorrectNumbers (String correctanswer, int width)
    {
	//random number
	int randomnum1;
	int randomnum2;
	//get random position for the incorrect answer
	incorrectanswer_x = (int) (Math.random () * (width - 237)) + 100;
	incorrectanswer_y = (int) (Math.random () * 50) - 50;
	//do while the incorrect answer is equal to the correct answer
	do
	{
	    //get random number for the incorrect answer
	    randomnum1 = (int) (Math.random () * 9);
	    randomnum2 = (int) (Math.random () * 9);
	    //set the string to the random number
	    incorrectanswer_string = "";
	    incorrectanswer_string += randomnum1;
	    incorrectanswer_string += randomnum2;
	}
	while (incorrectanswer_string.equals (correctanswer));
    }


    //Death - called when the object is supposed to die
    //correctanswer - gets a copy of the correct answer to check so that it does not match
    //width - the width of the screen
    public void Death (String correctanswer, int width)
    {
	//if object is supposed to be dead
	if (incorrectanswer_dead == true)
	{
	    //random number
	    int randomnum1;
	    int randomnum2;
	    //get random position for the incorrect answer
	    incorrectanswer_x = (int) (Math.random () * (width - 237)) + 100;
	    incorrectanswer_y = (int) (Math.random () * 50) - 50;
	    //do while the incorrect answer is equal to the correct answer
	    do
	    {
		//get random number for the incorrect answer
		randomnum1 = (int) (Math.random () * 9);
		randomnum2 = (int) (Math.random () * 9);
		//set the string to the random number
		incorrectanswer_string = "";
		incorrectanswer_string += randomnum1;
		incorrectanswer_string += randomnum2;

	    }
	    while (incorrectanswer_string.equals (correctanswer));
	    //set it so method does not repeat again
	    incorrectanswer_dead = false;
	}
    }
}

