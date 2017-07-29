package eVoting;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GetEncryptedResult {
	
	public static void main(String [] args) throws Exception {

		JSONParser parser = new JSONParser();
		try {
			VotingSystem vs = new VotingSystem(9, 5);
			JSONObject jsonObject = (JSONObject) parser.parse(new StringReader(args[1]));
			int candidate = ((Long)jsonObject.get("candidate")).intValue();
			System.out.println(vs.encryptVote(candidate));
			System.out.println(vs.decryptVote(vs.encryptVote(candidate)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
