package br.gov.scgas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.google.gson.Gson;

import br.gov.scgas.entidade.FirebaseData;
import br.gov.scgas.entidade.FirebaseMensagem;
import br.gov.scgas.entidade.FirebaseNotification;

public class SendNotificationFirebase {

	public static void enviaFirebase(String json) throws IOException{
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" +  "AIzaSyCXjWtrtVvj94PXXm1HyvfAOmAERzEqIsU");//chave publica do firebase
		OutputStream os = null;
		BufferedReader br  = null;
		try {
			os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode()+" "+conn.getResponseMessage());
			}

			 br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Saida do servidor .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}finally {
			if(os != null){
				os.close();				
			}
			if(br != null){
				br.close();				
			}
			conn.disconnect();
		}

	}

	public static String enviaPushFiribase(String to
			,String dataTitle
			,String dataDescription
			, String notificationTitle,
			String notificationBody) throws IOException {
		Gson gson = new Gson();

		FirebaseData data = new FirebaseData();
		data.setDescripcion(dataDescription);
		data.setTitulo(dataTitle);

		FirebaseNotification notification = new FirebaseNotification();
		notification.setBody(notificationBody);
		notification.setTitle(notificationTitle);
		//notification.setIcon("logo_home");

		FirebaseMensagem msg = new FirebaseMensagem(to, notification, data);
		msg.setPriority("Normal");
		String json = gson.toJson(msg);
		enviaFirebase(json);
		return json;

	}

	public String encodeMsgBase64(String msg){
		byte[] byteArray = org.apache.commons.codec.binary.Base64.encodeBase64(msg.getBytes());
		System.out.println(Arrays.toString(byteArray));
		String encodedString = new String(byteArray);
		System.out.println(msg + " = " + encodedString);
		return encodedString;
	}

}
