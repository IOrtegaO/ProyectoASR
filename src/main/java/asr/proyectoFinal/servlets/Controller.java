package asr.proyectoFinal.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import asr.proyectoFinal.dao.Clasificado;


/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/mostrar", "/hablar"})
public class Controller extends HttpServlet {
	
	
	private static Logger logger = Logger.getLogger(Controller.class.getName());
	private static final long serialVersionUID = 1L;
	
	private static final String apiKeyClasificar = "nXCubQxNrzRAJR_TJ6iZFugB-ZiRq9xSzW_fSV-RnYol";
	
	private static String apikeyHablar = "cpIf1xZ9tiSRXuYUI_SvzxUmuXIIaznD7ZfO1rBVJeKw";
	
	private static String urlHablar = "https://gateway-lon.watsonplatform.net/text-to-speech/api";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		System.out.println(request.getServletPath());
		
		String nombreImagen = request.getParameter("imagen");
		
		Clasificado clasificado = clasificar(nombreImagen);
		
		switch(request.getServletPath())
		{
		    case "/mostrar":
		    			    	
		    	// WATSON VIRTUAL RECOGNITION
				
				request.setAttribute("clasificado", clasificado);
				request.getRequestDispatcher("portfolio-item.jsp?imageName=" + nombreImagen).forward(request, response);
		    	
			case "/hablar":
					  
				hablar(response, clasificado);
					  
				break;

				
		}

	}

	private void hablar(HttpServletResponse response, Clasificado clasificado) throws IOException {
		InputStream inVoice = null;
		OutputStream outVoice = null;	
		try {
				
			IamOptions optionsVoice = new IamOptions.Builder()
				    .apiKey(apikeyHablar)
				    .build();
			
			TextToSpeech textService = new TextToSpeech(optionsVoice);
			textService.setEndPoint(urlHablar);
		    String voice = "en-US_AllisonVoice";
		    String text = "This is a " + clasificado.getClase() + " with a precision of " + clasificado.getScore();
		    String format = "audio/webm";
		    SynthesizeOptions synthesizeOptions =
			    new SynthesizeOptions.Builder()
				.text(text)
		        .accept(format)
			    .voice(voice)
		        .build();
		    inVoice = textService.synthesize(synthesizeOptions).execute();
		         
		    outVoice = response.getOutputStream();
		    byte[] buffer = new byte[2048];
		    int read;
		    while ((read = inVoice.read(buffer)) != -1) {
		    	outVoice.write(buffer, 0, read);
		    }
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} finally {
			inVoice.close();
			outVoice.close();
		}
	}

	private Clasificado clasificar(String nombreImagen) {
		IamOptions options = new IamOptions.Builder()
				  .apiKey(apiKeyClasificar)
				  .build();

		@SuppressWarnings("deprecation")
		VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", options);
		
		String respath = "/images/" + nombreImagen + ".png";
		InputStream in = Controller.class.getResourceAsStream(respath);
		if ( in == null )
			try {
				throw new Exception("resource not found: " + respath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
				.imagesFile(in)
				.imagesFilename("dog.png")
				.build();
		
		@SuppressWarnings("deprecation")
		ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();
		
		Clasificado clasificado = new Clasificado();
		clasificado.setClase(result.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getClassName().toString());
		
		clasificado.setScore(result.getImages().get(0).getClassifiers().get(0).getClasses().get(0).getScore().toString());
		return clasificado;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
