package application;

import java.io.Serializable;

/**
 * This class is part of the solution for assignment 3. The class represents a pizza, containing
 * the size, the amount of cheese, mushrooms and pepperoni. It can also produce the cost of that pizza
 * using hard-coded prices.
 * @author Alan McLeod
 * @version 1.2
 */
public class Pizza implements Serializable {

	private static final long serialVersionUID = 5735694084835042047L;
	
	private String checkNone(String topping) {
		if (topping.equals("None"))
			return "No";
		return topping;
	}
	
	/**
	 * Returns a string representation of the current object.
	 * @return A string description of the current Pizza object.
	 */
	public String toString(String size, String cheese, String pepperoni, String mushrooms) {
		String out = size + " pizza, " + cheese + " cheese, ";
		out += checkNone(pepperoni) + " pepperoni, ";
		out += checkNone(mushrooms) + " mushrooms.";
		return out;
	} // end toString
	
} // end Pizza class
