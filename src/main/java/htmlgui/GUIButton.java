package htmlgui;

import org.json.JSONObject;

public class GUIButton implements GUIElement {

	private int id;
	private String labelText;
	private GUIColor backgroundColor;
	private boolean isBorderRounded;
	
	
	public GUIButton(int id, String labelText) {
		this.id = id;
		this.labelText = labelText;
		this.backgroundColor = new GUIColor(0, 0, 255);
		this.isBorderRounded = false;
	}
	
	public GUIButton(int id, String labelText, int red, int green, int blue, boolean isBorderRounded) {
		this.id = id;
		this.labelText = labelText;
		this.backgroundColor = new GUIColor(red, green, blue);
		this.isBorderRounded = isBorderRounded;
	}


	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", "button");
		jsonObject.put("id", id);
		jsonObject.put("labelText", labelText);
		jsonObject.put("color", backgroundColor.toJSONObject());
		jsonObject.put("isBorderRounded", isBorderRounded);
		return jsonObject;
	}
	
}
