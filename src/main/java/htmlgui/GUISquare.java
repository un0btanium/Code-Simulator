package htmlgui;

import org.json.JSONObject;

public class GUISquare implements GUIElement {

	private String labelText;
	private GUIColor backgroundColor;
	private boolean isBorderRounded;
	

	public GUISquare(String labelText) {
		this.labelText = labelText;
		this.backgroundColor = new GUIColor(0, 0, 255);
		this.isBorderRounded = false;
	}
	
	public GUISquare(String labelText, int red, int green, int blue, boolean isBorderRounded) {
		this.labelText = labelText;
		this.backgroundColor = new GUIColor(red, green, blue);
		this.isBorderRounded = isBorderRounded;
	}
	
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "square");
		jsonObject.put("labelText", labelText);
		jsonObject.put("color", backgroundColor.toJSONObject());
		jsonObject.put("isBorderRounded", isBorderRounded);
		return jsonObject;
	}
	
}
