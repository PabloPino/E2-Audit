
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EntityExam extends DomainEntity {

	private String	ticker;
	private Date	publicationMoment;
	private String	body;
	private String	picture;
	private boolean	draft;


	@NotBlank
	@Pattern(regexp = "^\\d{6}-([A-z]){4}$")
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}
	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	@Length(max = 100)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public boolean isDraft() {
		return this.draft;
	}
	public void setDraft(final boolean draft) {
		this.draft = draft;
	}


	private Audit	audit;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Audit getAudit() {
		return this.audit;
	}
	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

}
