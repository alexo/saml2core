package intient.saml2.validator.impl;

import intient.saml2.constants.StatusCodeConstants;
import intient.saml2.constants.VersionConstants;
import intient.saml2.identifiers.IdentifierCache;
import intient.saml2.identifiers.exception.IdentifierCollisionException;
import intient.saml2.validator.StatusResponseValidator;
import intient.saml2.validator.bean.ResponseRecipient;
import intient.saml2.validator.bean.ResponseValidity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.oasis.saml2.protocol.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusResponseValidatorImpl implements StatusResponseValidator {
	private IdentifierCache identifierCache;
	private int allowedTimeSkew;

	private Logger logger = LoggerFactory.getLogger(StatusResponseValidatorImpl.class.getName());

	public ResponseValidity validate(StatusResponseType response, ResponseRecipient recipient) {
		ResponseValidity responseValidity = new ResponseValidity();

		SimpleTimeZone utc = new SimpleTimeZone(0, "UTC");
		Calendar currentCalendar = new GregorianCalendar(utc);
		long currentTime = currentCalendar.getTimeInMillis();

		// Version
		if (!response.getVersion().equals(VersionConstants.saml20)) {
			this.logger.warn("SAML version string was not set to 2.0 and is incompatible with this validation process");
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.VERSION);
			return responseValidity;
		}

		// ID
		if (response.getID() == null) {
			this.logger.warn("Request ID was null");
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.ID);
			return responseValidity;
		}
		try {
			this.identifierCache.registerIdentifier(response.getID());
		} catch (IdentifierCollisionException e) {
			this.logger.debug(e.getLocalizedMessage(), e);
			this.logger.warn("{} - Request ID was duplicated, potential attack", response.getID());
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.ID_DUPLICATE);
			return responseValidity;
		}
		responseValidity.setId(response.getID());

		// Issue Instant
		if (response.getIssueInstant() == null) {
			this.logger.warn("{} - Request issue instant was not supplied", responseValidity.getId());
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.ISSUEINSTANT);
			return responseValidity;
		}
		XMLGregorianCalendar issueInstantCalendar = response.getIssueInstant();
		responseValidity.setIssueInstant(issueInstantCalendar.toGregorianCalendar());
		long documentTime = issueInstantCalendar.toGregorianCalendar().getTimeInMillis();
		if (Math.abs(currentTime - documentTime) > this.allowedTimeSkew) {
			this.logger.warn("{} - Request issue instant exceeded allowed time skew", responseValidity.getId());
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.ISSUEINSTANT_EXCEED_SKEW);
			return responseValidity;
		}

		// Destination
		if (response.getDestination() != null) {
			if (!recipient.getLocalDestinations().contains(response.getDestination())) {
				this.logger.warn("{} - Request destination was not valid for locally supported URIs", responseValidity.getId());
				responseValidity.setStatus(ResponseValidity.Status.INVALID);
				responseValidity.setCause(ResponseValidity.Cause.DESTINATION);
				return responseValidity;
			}
		}

		// InResponseTo
		if (response.getInResponseTo() != null) {
			if (!this.identifierCache.containsIdentifier(response.getInResponseTo())) {
				this.logger.warn("{} - InResponseTo did not contain an identifier that originated locally", responseValidity.getId());
				responseValidity.setStatus(ResponseValidity.Status.INVALID);
				responseValidity.setCause(ResponseValidity.Cause.INRESPONSETO);
				return responseValidity;
			}
		}

		// Issuer
		if (response.getIssuer() != null) {
			responseValidity.setIssuer(response.getIssuer().getValue());
		}
		
		// Status
		if (!response.getStatus().getStatusCode().getValue().equals(StatusCodeConstants.success)) {
			this.logger.warn("{} - Status code on this response document was not success", responseValidity.getId());
			responseValidity.setStatus(ResponseValidity.Status.INVALID);
			responseValidity.setCause(ResponseValidity.Cause.STATUS);
			responseValidity.setStatusCode(response.getStatus().getStatusCode().getValue());
			if (response.getStatus().getStatusCode().getStatusCode() != null)
				responseValidity.setSecondLevelStatusCode(response.getStatus().getStatusCode().getStatusCode()
						.getValue());
			responseValidity.setStatusMessage(response.getStatus().getStatusMessage());
			return responseValidity;
		}
		
		responseValidity.setStatus(ResponseValidity.Status.VALID);
		return responseValidity;
	}
}
