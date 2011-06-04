/*      Variable dictionary (for variables that are located in the MathExplosion class):
	explode_x - the x value of the explosion
	explode_y - the y value of the explosion
	rad - the radius of the explosion
*/


class MathExplosion
{
    //Variables for the class
    public int x, y, rad;
    public MathGameMain methd = new MathGameMain ();
    //MathExplosion - constructor for the explosion object
    //x - the x value of the explosion
    //y - the y value of the explosion
    //rad - the radius of the explosion
    public MathExplosion (int get_x, int get_y, int get_rad)
    {
	//give x, y, and radius value of the explosion
	x = get_x;
	y = get_y;
	rad = get_rad;
    }
}
