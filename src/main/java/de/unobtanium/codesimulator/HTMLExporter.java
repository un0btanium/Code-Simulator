package de.unobtanium.codesimulator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.unobtanium.codesimulator.simulator.MainSimulator;

public class HTMLExporter {

	
	public static void export(CodeData codeData) {

		// TURN INFORMATION IN HTML FORMAT
		String htmlString = new MainSimulator(codeData).simulate();
		System.out.println(htmlString);
		
		// EXPORT HTML INFORMATION INTO HTML FILE
		exportHTML(htmlString);
		
		// TODO save HTML file (do basic version first!)

		// TODO save highlighting (simple at first, then with changes to the code e.g. calculations or from vars to values)
		
		// TODO simulation with tables is separate via the code injection ( e.g. id =
		// .createNewTableForBlock(), .endBlock(id)?)

		// TODO maybe dont execute statements twice (in some cases it would lead to wrong data), save it in a temp variable instead and replace the expression in the original statement with the variable
		// TODO or just replace the old code
		// TODO or only try to do it if method calls are used which might lead to changes in data
//		JSONObject jsonObject = new JSONObject();
//		return jsonObject;
	}

	private static void exportHTML(String result) {
		
		String fileContent = "<!doctype html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"  <head>\r\n" + 
				"\r\n" + 
				"    <meta charset=\"utf-8\">\r\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n" + 
				"\r\n" + 
				"    <!-- Bootstrap CSS -->\r\n" + 
				"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\r\n" + 
				"\r\n" + 
				"    <title>Code Simulator!</title>\r\n" + 
				"    \r\n" + 
				"        <style>\r\n" + 
				"		.code {\r\n" + 
				"  			font-family: \"Consolas\", Times, Sans-serif;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.border-memory {\r\n" + 
				"			border: 8px solid grey;\r\n" + 
				"			border-radius: 8px;\r\n" + 
				"			height:300px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.border-code {\r\n" + 
				"			border: 8px solid green;\r\n" + 
				"			border-radius: 8px;\r\n" + 
				"			height:300px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.border-console {\r\n" + 
				"			border: 8px solid lightblue;\r\n" + 
				"			border-radius: 8px;\r\n" + 
				"			height:300px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.box-padding {\r\n" + 
				"			padding: 6px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.bold-text {\r\n" + 
				"  			font-weight: bold;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.medium-text-size {\r\n" + 
				"			font-size: 18px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.center {\r\n" + 
				"		  margin: auto;\r\n" + 
				"		  width: 100%;\r\n" + 
				"		  padding: 1px;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		.debug {\r\n" + 
				"		  border: 3px solid green;\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		div {\r\n" + 
				"		    line-height:1.2;\r\n" + 
				"		}" + 
				"\r\n" + 
				"	</style>" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"  	\r\n" + 
				"	\r\n" + 
				"  	  	<div class=\"container-fluid\" >\r\n" + 
				"		<div class=\"row mt-4 mb-4\">\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"				<div class=\"btn-group center\" role=\"group\" aria-label=\"Basic example\">\r\n" + 
				"					<button type=\"button\" class=\"btn btn-primary\" onclick=\"startContent()\">Goto start</button>\r\n" + 
				"			  		<button type=\"button\" class=\"btn btn-primary\" onclick=\"previousContent()\">Previous</button>\r\n" + 
				"			  		<button type=\"button\" class=\"btn btn-primary\" onclick=\"nextContent()\">Next</button>\r\n" + 
				"			  		<button type=\"button\" class=\"btn btn-primary\" onclick=\"endContent()\">Goto end</button>\r\n" + 
				"				</div>\r\n" + 
				"		  	</div>\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"\r\n" + 
				"		<div class=\"row mt-4 mb-4\">\r\n" + 
				"			<div class=\"col\">\r\n" + 
				"    			<div id=\"progressbarframe\"></div>\r\n" + 
				"		  	</div>\r\n" + 
				"		</div>\r\n" + 
				"\r\n" + 
				"		<div class=\"row mt-4 mb-4\" >\r\n" + 
				"			<div class=\"col ml-2 mr-2 code medium-text-size bold-text\">\r\n" + 
				"				<div class=\"border-memory\">\r\n" + 
				"	    			<div class=\"box-padding\" id=\"memoryframe\"></div>\r\n" + 
				"	    		</div>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"col ml-2 mr-2 code medium-text-size\">\r\n" + 
				"				<div class=\"border-code\">\r\n" + 
				"    				<div class=\"box-padding\" id=\"codeframe\"></div>\r\n" + 
				"	    		</div>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"col ml-2 mr-2 code medium-text-size bold-text\">\r\n" + 
				"				<div class=\"border-console\">\r\n" + 
				"    				<div class=\"box-padding\" id=\"consoleframe\"></div>\r\n" + 
				"	    		</div>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>" + 
				"	\r\n" + 
				"	\r\n" + 
				"    <!-- Optional JavaScript -->\r\n" + 
				"    <!-- jQuery first, then Popper.js, then Bootstrap JS -->\r\n" + 
				"    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>\r\n" + 
				"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>\r\n" + 
				"    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>\r\n" + 
				"\r\n" + 
				"	<script type=\"text/javascript\">\r\n" + 
				"		\r\n" + 
				"		var counter = 0;\r\n" + 
				"\r\n" + 
				"		var contentArray = " + result + "\r\n" + 
				"\r\n" + 
				"		startContent();\r\n" + 
				"\r\n" + 
				"		function nextContent() {\r\n" + 
				"			if (counter < contentArray.length-1) {\r\n" + 
				"				counter++;\r\n" + 
				"			} else {\r\n" + 
				"				startContent();\r\n" + 
				"			}\r\n" + 
				"			writeContent()\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		function previousContent() {\r\n" + 
				"			if (counter > 0) {\r\n" + 
				"				counter--;\r\n" + 
				"			} else {\r\n" + 
				"				endContent()\r\n" + 
				"			}\r\n" + 
				"			writeContent()\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		function startContent() {\r\n" + 
				"			counter = 0;\r\n" + 
				"			writeContent()\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		function endContent() {\r\n" + 
				"			counter = contentArray.length-1;\r\n" + 
				"			writeContent()\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		function writeContent() {\r\n" + 
				"			document.getElementById(\"codeframe\").innerHTML = contentArray[counter].code;\r\n" + 
				"			document.getElementById(\"memoryframe\").innerHTML = contentArray[counter].memory;\r\n" + 
				"			document.getElementById(\"progressbarframe\").innerHTML = contentArray[counter].progressbar;\r\n" + 
				"			document.getElementById(\"consoleframe\").innerHTML = contentArray[counter].console;\r\n" + 
				"	    	$('.progress-bar').tooltip();\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"	</script>	\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		
		
		try (PrintWriter out = new PrintWriter("C:/Users/unobtanium/Desktop/simulationTest.html")) {
		    out.println(fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		Files.writeString(Paths.get("C:/Users/unobtanium/Desktop/simulationTest.html"), fileContent); // JAVA Version 11
		
		// TODO export source code
		// TODO source code highlighting (use AST and export its elements with appropriate css color tags)
		// TODO display console text
		// TODO display memory text
		// TODO display progress bar
		
	}
	
}
