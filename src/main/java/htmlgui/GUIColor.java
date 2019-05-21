package htmlgui;

import org.json.JSONObject;

public class GUIColor {
	
	private int red;
	private int green;
	private int blue;
	
	public GUIColor (int red, int green, int blue) {
		this.red = red > 255 ? 255 : red < 0 ? 0 : red;
		this.green = green > 255 ? 255 : green < 0 ? 0 : green;
		this.blue = blue > 255 ? 255 : blue < 0 ? 0 : blue;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("red", red);
		jsonObject.put("green", green);
		jsonObject.put("blue", blue);
		return jsonObject;
	}
	
}
