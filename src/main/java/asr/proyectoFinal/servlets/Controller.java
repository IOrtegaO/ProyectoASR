package asr.proyectoFinal.servlets;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import asr.proyectoFinal.dao.Clasificado;
import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Palabra;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar", "/insertar", "/pulsar"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/listar":
				if(store.getDB() == null)
					  out.println("No hay DB");
				else
					out.println("Palabras en la BD Cloudant:<br />" + store.getAll());
				break;
				
			case "/insertar":
				Palabra palabra = new Palabra();
				String parametro = request.getParameter("palabra");

				if(parametro==null)
				{
					out.println("usage: /insertar?palabra=palabra_a_traducir");
				}
				else
				{
					if(store.getDB() == null) 
					{
						out.println(String.format("Palabra: %s", palabra));
					}
					else
					{
						palabra.setName(parametro);
						store.persist(palabra);
					    out.println(String.format("Almacenada la palabra: %s", palabra.getName()));			    	  
					}
				}
				break;
			case "/pulsar":
				String nombreImagen = request.getParameter("imagen");
				
				if(nombreImagen==null)
				{
					out.println("usage: /insertar?imagen=nombre_imagen");
				}
				else
				{
					
					
					IamOptions options = new IamOptions.Builder()
							  .apiKey("nXCubQxNrzRAJR_TJ6iZFugB-ZiRq9xSzW_fSV-RnYol")
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
					
					request.setAttribute("clasificado", clasificado);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				break;

				
		}
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
