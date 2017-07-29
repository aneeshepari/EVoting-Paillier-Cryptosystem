package all;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FetchTallyResults {

	public static void main(String[] args) throws Exception {
		JSONParser parser = new JSONParser();

		try {
			// get keys from database
			VotingSystem vs = new VotingSystem();
//			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("tally.json"));

			JSONObject jsonObject = (JSONObject) parser.parse(new StringReader(args[0]));

			List<String> votes = (List<String>) jsonObject.get("votes");
			List<BigInteger> v = new ArrayList<>();
			for(String vote: votes) {
				System.out.println(vote);
				v.add(new BigInteger(vote));
			}
			System.out.println(vs.tallyVotes(v));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
