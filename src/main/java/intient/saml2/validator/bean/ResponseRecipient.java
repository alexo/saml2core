package intient.saml2.validator.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponseRecipient {

	private List<String> localDestinations;
	
	public ResponseRecipient() {
		this.localDestinations = new ArrayList<String>();
	}

	public List<String> getLocalDestinations() {
		return localDestinations;
	}

	public void setLocalDestinations(List<String> localDestinations) {
		this.localDestinations = localDestinations;
	}
	
	
}
