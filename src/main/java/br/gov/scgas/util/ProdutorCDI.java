package br.gov.scgas.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ProdutorCDI {
	
	@Produces
	public Gson instanciaGson(){
		GsonBuilder b = new GsonBuilder();

		b.registerTypeAdapter(Date.class, new GsonDateDeSerializer());
		b.setDateFormat("dd/MM/yyyy");
		Gson gson = b.create();

		return gson;
	}
	
	
	public class GsonDateDeSerializer implements JsonDeserializer<Date>,JsonSerializer<Date> {


		private SimpleDateFormat format1 = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
		private SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		private SimpleDateFormat format3 = new SimpleDateFormat("MMM dd, yyyy");
		private SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy");
		private SimpleDateFormat format5 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
		private String strFormat1 = "MMM dd, yyyy hh:mm:ss";
		private String strFormat2 = "dd/MM/yyyy hh:mm:ss";
		private String strFormat3 = "MMM dd, yyyy";
		private String strFormat4 = "dd/MM/yyyy";



		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			try {
				String j = json.getAsJsonPrimitive().getAsString();
				return parseDate(j);
			} catch (ParseException e) {
				throw new JsonParseException(e.getMessage(), e);
			}
		}

		private Date parseDate(String dateString) throws ParseException {
			
			if (dateString != null && !dateString.isEmpty() ) {
				try {
					
					Date dataAux = format2.parse(dateString);
					String strDate = format2.format(dataAux);
					return format2.parse(strDate);

				} catch (ParseException pe) {
					try {
					Date dataAux = format3.parse(dateString);
					String strDate = format2.format(dataAux);
					return format2.parse(strDate);
					} catch (ParseException p) {
						try{
							Date dataAux = format4.parse(dateString);
							String strDate = format2.format(dataAux);
							return format2.parse(strDate);							
						}catch(ParseException p4){
							Date dataAux = format5.parse(dateString);
							String strDate = format2.format(dataAux);
							return format2.parse(strDate);	
						}
					}
				}
			} else {
				return null;
			}


		}

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			// TODO Auto-generated method stub
			SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
			return src == null ? null : new JsonPrimitive(formata.format(src));
		}

	}



}
