package asr.proyectoFinal.services;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.GetVoiceOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;



public class Habla {
	
	private static String apikey = "cpIf1xZ9tiSRXuYUI_SvzxUmuXIIaznD7ZfO1rBVJeKw";
	
	private static String url = "https://gateway-lon.watsonplatform.net/text-to-speech/api";
	
	private static String voice = "es-ES_LauraVoice";
	
	public Habla() {
		super();
	}

	public static OutputStream speak(String texto) {

			
		
		IamOptions options = new IamOptions.Builder()
			    .apiKey(apikey)
			    .build();
	
		TextToSpeech textToSpeech = new TextToSpeech(options);
		
		textToSpeech.setEndPoint(url);
		
		
		try {
			  SynthesizeOptions synthesizeOptions =
			    new SynthesizeOptions.Builder()
			      .text(texto)
			      .accept("audio/wav")
			      .voice(voice)
			      .build();

			  InputStream inputStream = textToSpeech.synthesize(synthesizeOptions).execute();
			  InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

			  FileOutputStream out = new FileOutputStream("/Users/beatriztorreiromosquera/Downloads/hello_world.wav");
			  
			  byte[] buffer = new byte[1024];
			  int length;
			  while ((length = in.read(buffer)) > 0) {
			    out.write(buffer, 0, length);
			  }

			  out.close();
			  in.close();
			  inputStream.close();
			  
			  FileOutputStream outNoLocal = new FileOutputStream("/audios/hello_world.wav");
			  outNoLocal = out;
			  
			  return out;
			} catch (IOException e) {
			  e.printStackTrace();
			}
		return null;
	}
//	String text = "It's beginning to look a lot like Christmas";
//	SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
//	  .text(text)
//	  .accept(SynthesizeOptions.Accept.AUDIO_OGG_CODECS_OPUS)
//	  .build();

	
	

	
}
