package intient.saml2.validator.impl;

import intient.saml2.constants.ConfirmationMethodConstants;
import intient.saml2.constants.VersionConstants;
import intient.saml2.identifiers.IdentifierCache;
import intient.saml2.identifiers.exception.IdentifierCollisionException;
import intient.saml2.validator.AssertionValidator;
import intient.saml2.validator.bean.AssertionRecipient;
import intient.saml2.validator.bean.AssertionValidity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.oasis.saml2.assertion.Assertion;
import org.oasis.saml2.assertion.AudienceRestriction;
import org.oasis.saml2.assertion.ConditionAbstractType;
import org.oasis.saml2.assertion.Conditions;
import org.oasis.saml2.assertion.OneTimeUse;
import org.oasis.saml2.assertion.ProxyRestriction;
import org.oasis.saml2.assertion.SubjectConfirmation;
import org.oasis.saml2.assertion.SubjectConfirmationDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3._2000._09.xmldsig_.KeyInfo;

/**
 * Implementation to validate Assertion element to SAML 2 requirements
 * 
 * @author Bradley Beddoes
 */
public class AssertionValidatorImpl implements AssertionValidator {
	private IdentifierCache identifierCache;
	private int allowedTimeSkew;

	boolean processSubject, processSubjectConfirmationRecipient, processSubjectConfirmationAddress;
	boolean processConditions;

	private Logger logger = LoggerFactory.getLogger(AssertionValidatorImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see intient.saml2.validator.AssertionValidator#validate(org.oasis.saml2.assertion.Assertion,
	 * intient.saml2.validator.bean.AssertionRecipient)
	 */
	public AssertionValidity validate(Assertion assertion, AssertionRecipient recipient) {

		AssertionValidity assertionValidity = new AssertionValidity();

		SimpleTimeZone utc = new SimpleTimeZone(0, "UTC");
		Calendar currentCalendar = new GregorianCalendar(utc);
		long currentTime = currentCalendar.getTimeInMillis();

		// Version
		if (!assertion.getVersion().equals(VersionConstants.saml20)) {
			this.logger.warn("SAML version string was not set to 2.0 and is incompatible with this validation process");
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.VERSION);
			return assertionValidity;
		}

		// ID
		if (assertion.getID() == null) {
			this.logger.warn("Assertion ID was null");
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.ID);
			return assertionValidity;
		}
		try {
			this.identifierCache.registerIdentifier(assertion.getID());
		} catch (IdentifierCollisionException e) {
			this.logger.debug(e.getLocalizedMessage(), e);
			this.logger.warn("{} - Assertion ID was duplicated, potential attack", assertion.getID());
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.ID_DUPLICATE);
			return assertionValidity;
		}
		assertionValidity.setId(assertion.getID());

		// Issue Instant
		if (assertion.getIssueInstant() == null) {
			this.logger.warn("{} - Assertion issue instant was not supplied", assertionValidity.getId());
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.ISSUEINSTANT);
			return assertionValidity;
		}
		XMLGregorianCalendar issueInstantCalendar = assertion.getIssueInstant();
		assertionValidity.setIssueInstant(issueInstantCalendar.toGregorianCalendar());
		long documentTime = issueInstantCalendar.toGregorianCalendar().getTimeInMillis();
		if (Math.abs(currentTime - documentTime) > this.allowedTimeSkew) {
			this.logger.warn("{} - Assertion issue instant exceeded allowed time skew", assertionValidity.getId());
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.ISSUEINSTANT_EXCEED_SKEW);
			return assertionValidity;
		}

		// Issuer
		if (assertion.getIssuer() == null) {
			this.logger.warn("{} - Assertion issuer was null", assertionValidity.getId());
			assertionValidity.setStatus(AssertionValidity.Status.INVALID);
			assertionValidity.setCause(AssertionValidity.Cause.ISSUER);
			return assertionValidity;
		}
		assertionValidity.setIssuer(assertion.getIssuer().getValue());

		// Subject - strictly speaking subject may be left out but only for non standard bindings so really this should
		// always be evaluated
		if (processSubject || assertion.getSubject() != null) {

			if (assertion.getSubject() == null) {
				this.logger.warn("{} - Assertion was required to present a subject but it was not present", assertionValidity.getId());
				assertionValidity.setStatus(AssertionValidity.Status.INVALID);
				assertionValidity.setCause(AssertionValidity.Cause.SUBJECT);
				return assertionValidity;
			}

			if (assertion.getSubject().getBaseID() == null && assertion.getSubject().getNameID() == null
					&& assertion.getSubject().getEncryptedID() == null) {
				assertionValidity.setStatus(AssertionValidity.Status.INVALID);
				assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_IDENTIFIER);
				return assertionValidity;
			}

			// Subject -> SubjectConfirmation
			for (SubjectConfirmation confirmation : assertion.getSubject().getSubjectConfirmation()) {

				boolean requiresKeyInfo = false;

				if (!recipient.getValidConfirmationMethods().contains(confirmation.getMethod())) {
					this.logger.warn("{} - Assertion specifies a subject confirmation method that is not acceptable", assertionValidity.getId());
					assertionValidity.setStatus(AssertionValidity.Status.INVALID);
					assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_METHOD);
					return assertionValidity;
				}

				assertionValidity.getSubjectConfirmationMethods().add(confirmation.getMethod());
				if (confirmation.getMethod().equals(ConfirmationMethodConstants.holderOfKey)) {
					requiresKeyInfo = true;
				}

				SubjectConfirmationDataType subjectConfirmationData = confirmation.getSubjectConfirmationData();
				if (subjectConfirmationData != null) {

					// NotBefore
					if (subjectConfirmationData.getNotBefore() != null) {
						XMLGregorianCalendar notBeforeCal = subjectConfirmationData.getNotBefore();
						long notBeforeTime = notBeforeCal.toGregorianCalendar().getTimeInMillis();
						if (currentTime < notBeforeTime) {
							this.logger.warn("{} - Assertion specifies that it is not valid before " + notBeforeTime
									+ ", processing attempted before this time", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_NOTBEFORE);
							return assertionValidity;
						}
					}

					// NotOnOrAfter
					if (subjectConfirmationData.getNotOnOrAfter() != null) {
						XMLGregorianCalendar notAfterCal = subjectConfirmationData.getNotOnOrAfter();
						long notAfterTime = notAfterCal.toGregorianCalendar().getTimeInMillis();
						if (currentTime >= notAfterTime) {
							this.logger.warn("{} - Assertion NotOnOrAfter timestamp is outside of allowable range", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_NOTONAFTER);
							return assertionValidity;
						}
					}

					// Recipient
					if (processSubjectConfirmationRecipient) {
						String suppliedRecipientURI = subjectConfirmationData.getRecipient();
						if (!recipient.getRecipientURI().equals(suppliedRecipientURI)) {
							this.logger.warn("{} - Assertion subject confirmation recipient did match local recipient", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_RECIPIENT);
							return assertionValidity;
						}
					}

					// InResponseTo
					if (subjectConfirmationData.getInResponseTo() != null) {
						if (!this.identifierCache.containsIdentifier(subjectConfirmationData.getInResponseTo())) {
							this.logger
									.warn("{} - Assertion subject confirmation ID was not generated locally. Possible attack.", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_INRESPONSETO);
							return assertionValidity;
						}
					}

					// Address
					if (processSubjectConfirmationAddress) {
						String suppliedAddress = subjectConfirmationData.getAddress();
						if (!recipient.getTrustedAddresses().contains(suppliedAddress)) {
							this.logger
									.warn("{} - Assertion subject confirmation address did match address specified for this client", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_ADDRESS);
							return assertionValidity;
						}
					}

					// Meet requires for confirmation using urn:oasis:names:tc:SAML:2.0:cm:holder-of-key
					if (requiresKeyInfo) {
						boolean processedKey = false;
						if (subjectConfirmationData.getContent() != null) {
							for (Object obj : subjectConfirmationData.getContent()) {
								if (obj instanceof KeyInfo) {
									processedKey = true;
									assertionValidity.getSubjectConfirmationKeyInfo().add((KeyInfo) obj);
								}
							}
						}
						if (!processedKey) {
							this.logger
									.warn("{} - Assertion subject confirmation method requires KeyInfo to be presented but none present", assertionValidity.getId());
							assertionValidity.setStatus(AssertionValidity.Status.INVALID);
							assertionValidity.setCause(AssertionValidity.Cause.SUBJECT_CONFIRMATION_METHOD_HOLDEROFKEY);
							return assertionValidity;
						}
					}
				}
			}
		}

		// Conditions
		Conditions conditions = assertion.getConditions();
		if (processConditions || conditions != null) {
			if (conditions == null) {
				this.logger.warn("{} - Assertion was expected to present conditions but it was not present", assertionValidity.getId());
				assertionValidity.setStatus(AssertionValidity.Status.INVALID);
				assertionValidity.setCause(AssertionValidity.Cause.CONDITIONS);
				return assertionValidity;
			}

			// NotBefore
			if (conditions.getNotBefore() != null) {
				XMLGregorianCalendar notBeforeCal = conditions.getNotBefore();
				long notBeforeTime = notBeforeCal.toGregorianCalendar().getTimeInMillis();
				if (currentTime < notBeforeTime) {
					this.logger.warn("{} - Assertion condtions specify that it is not valid before " + notBeforeTime
							+ ", processing attempted before this time", assertionValidity.getId());
					assertionValidity.setStatus(AssertionValidity.Status.INVALID);
					assertionValidity.setCause(AssertionValidity.Cause.CONDITIONS_NOTBEFORE);
					return assertionValidity;
				}
			}

			// NotOnOrAfter
			if (conditions.getNotOnOrAfter() != null) {
				XMLGregorianCalendar notAfterCal = conditions.getNotOnOrAfter();
				long notAfterTime = notAfterCal.toGregorianCalendar().getTimeInMillis();
				if (currentTime >= notAfterTime) {
					this.logger.warn("{} - Assertion conditions NotOnOrAfter timestamp is outside of allowable range", assertionValidity.getId());
					assertionValidity.setStatus(AssertionValidity.Status.INVALID);
					assertionValidity.setCause(AssertionValidity.Cause.CONDITIONS_NOTONAFTER);
					return assertionValidity;
				}
			}

			// Specified Conditions
			List<ConditionAbstractType> specifiedConditons = conditions
					.getConditionsAndOneTimeUsesAndAudienceRestrictions();
			if (specifiedConditons != null && specifiedConditons.size() > 0) {

				boolean audienceRestricted = false;
				boolean validAudience = true;

				for (ConditionAbstractType specificCondition : specifiedConditons) {
					if (specificCondition instanceof AudienceRestriction) {
						audienceRestricted = true;
						boolean localValidAudience = false;
						AudienceRestriction audienceRestriction = (AudienceRestriction) specificCondition;
						for (String audience : audienceRestriction.getAudiences()) {
							// Individual Audiences form a disjunction
							if (recipient.getAudienceMemberships().contains(audience)) {
								this.logger
										.debug("{} - Validated that audienceRestriction for assertion contained a local audienceMembership URI", assertionValidity.getId());
								localValidAudience = true;
								break;
							}
						}
						// Multiple AudienceRestriction blocks form a conjunction
						validAudience = validAudience && localValidAudience;
					} else if (specificCondition instanceof OneTimeUse) {
						// OneTimeUse has no validity constraints we can verify but caching implementations must still
						// adhere to it
						assertionValidity.setOneTimeUse(true);
						continue;
					} else if (specificCondition instanceof ProxyRestriction) {
						// ProxyRestriction has no validity constraints we can verify but implementations will be
						// interested in content
						assertionValidity.setProxyRestriction(true);
						continue;
					} else {

						// This is some generic extension to saml:Condition that we can't validate
						assertionValidity.setStatus(AssertionValidity.Status.INDETERMINATE);
						assertionValidity.setCause(AssertionValidity.Cause.CONDITIONS_EXTENDED);
					}
				}
				if (audienceRestricted) {
					if (!validAudience) {
						assertionValidity.setStatus(AssertionValidity.Status.INVALID);
						assertionValidity.setCause(AssertionValidity.Cause.CONDITIONS_AUDIENCE_RESTRICTION);
						return assertionValidity;
					}
				}
			}
		}

		// Advice - Currently no local support for validating advice blocks.

		// Invalid assertions will return immediately though indeterminate generally continue processing
		// If we've reached this point and status is not indeterminate then assertion is valid
		if (assertionValidity.getStatus() != AssertionValidity.Status.INDETERMINATE)
			assertionValidity.setStatus(AssertionValidity.Status.VALID);

		return assertionValidity;
	}

	public IdentifierCache getIdentifierCache() {
		return identifierCache;
	}

	public void setIdentifierCache(IdentifierCache identifierCache) {
		this.identifierCache = identifierCache;
	}

	public int getAllowedTimeSkew() {
		return allowedTimeSkew;
	}

	public void setAllowedTimeSkew(int allowedTimeSkew) {
		this.allowedTimeSkew = allowedTimeSkew;
	}

	public boolean isProcessSubject() {
		return processSubject;
	}

	public void setProcessSubject(boolean processSubject) {
		this.processSubject = processSubject;
	}

	public boolean isProcessSubjectConfirmationRecipient() {
		return processSubjectConfirmationRecipient;
	}

	public void setProcessSubjectConfirmationRecipient(boolean processSubjectConfirmationRecipient) {
		this.processSubjectConfirmationRecipient = processSubjectConfirmationRecipient;
	}

	public boolean isProcessSubjectConfirmationAddress() {
		return processSubjectConfirmationAddress;
	}

	public void setProcessSubjectConfirmationAddress(boolean processSubjectConfirmationAddress) {
		this.processSubjectConfirmationAddress = processSubjectConfirmationAddress;
	}

	public boolean isProcessConditions() {
		return processConditions;
	}

	public void setProcessConditions(boolean processConditions) {
		this.processConditions = processConditions;
	}
}
