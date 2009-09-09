package intient.saml2.validator.impl;

import intient.saml2.constants.VersionConstants;
import intient.saml2.identifiers.IdentifierCache;
import intient.saml2.identifiers.exception.IdentifierCollisionException;
import intient.saml2.validator.RequestValidator;
import intient.saml2.validator.bean.RequestRecipient;
import intient.saml2.validator.bean.RequestValidity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.oasis.saml2.protocol.RequestAbstractType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestValidatorImpl implements RequestValidator
{
	private int allowedTimeSkew;
	private IdentifierCache identifierCache;
	
	private Logger logger = LoggerFactory.getLogger(RequestValidatorImpl.class.getName());

	public RequestValidity validate(RequestAbstractType request, RequestRecipient recipient)
	{
		RequestValidity requestValidity = new RequestValidity();

		SimpleTimeZone utc = new SimpleTimeZone(0, "UTC");
		Calendar currentCalendar = new GregorianCalendar(utc);
		long currentTime = currentCalendar.getTimeInMillis();
		
		// Version
		if (!request.getVersion().equals(VersionConstants.saml20)) {
			this.logger.warn("SAML version string was not set to 2.0 and is incompatible with this validation process");
			requestValidity.setStatus(RequestValidity.Status.INVALID);
			requestValidity.setCause(RequestValidity.Cause.VERSION);
			return requestValidity;
		}

		// ID
		if (request.getID() == null) {
			this.logger.warn("Request ID was null");
			requestValidity.setStatus(RequestValidity.Status.INVALID);
			requestValidity.setCause(RequestValidity.Cause.ID);
			return requestValidity;
		}
		try {
			this.identifierCache.registerIdentifier(request.getID());
		} catch (IdentifierCollisionException e) {
			this.logger.debug(e.getLocalizedMessage(), e);
			this.logger.warn("{} - Request ID was duplicated, potential attack", request.getID());
			requestValidity.setStatus(RequestValidity.Status.INVALID);
			requestValidity.setCause(RequestValidity.Cause.ID_DUPLICATE);
			return requestValidity;
		}
		requestValidity.setId(request.getID());
		
		// Issue Instant
		if (request.getIssueInstant() == null) {
			this.logger.warn("{} - Request issue instant was not supplied", requestValidity.getId());
			requestValidity.setStatus(RequestValidity.Status.INVALID);
			requestValidity.setCause(RequestValidity.Cause.ISSUEINSTANT);
			return requestValidity;
		}
		XMLGregorianCalendar issueInstantCalendar = request.getIssueInstant();
		requestValidity.setIssueInstant(issueInstantCalendar.toGregorianCalendar());
		long documentTime = issueInstantCalendar.toGregorianCalendar().getTimeInMillis();
		if (Math.abs(currentTime - documentTime) > this.allowedTimeSkew) {
			this.logger.warn("{} - Request issue instant exceeded allowed time skew", requestValidity.getId());
			requestValidity.setStatus(RequestValidity.Status.INVALID);
			requestValidity.setCause(RequestValidity.Cause.ISSUEINSTANT_EXCEED_SKEW);
			return requestValidity;
		}
		
		// Destination
		if(request.getDestination() != null) {
			if(!recipient.getLocalDestinations().contains(request.getDestination())) {
				this.logger.warn("{} - Request destination was not valid for locally supported URIs", requestValidity.getId());
				requestValidity.setStatus(RequestValidity.Status.INVALID);
				requestValidity.setCause(RequestValidity.Cause.DESTINATION);
				return requestValidity;
			}
		}
		
		// Issuer
		if (request.getIssuer() != null) {
			requestValidity.setIssuer(request.getIssuer().getValue());
		}
		
		requestValidity.setStatus(RequestValidity.Status.VALID);
		return requestValidity;
	}

}
