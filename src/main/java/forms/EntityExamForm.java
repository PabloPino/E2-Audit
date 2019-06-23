
package forms;

import domain.Application;
import domain.DomainEntity;

public class EntityExamForm extends DomainEntity {

	private String		body;
	private String		picture;
	private boolean		draft;
	private Application	application;


	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

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

	public Application getApplication() {
		return this.application;
	}
	public void setApplication(final Application application) {
		this.application = application;
	}

}
