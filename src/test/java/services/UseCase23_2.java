
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class UseCase23_2 extends AbstractTest {

	// An actor who is authenticated must be able to manage  his  or  her  messages,  which  includes  
	// listing  them  grouped  by  tag,  showing them,  sending  a  message  to  an actor,  deleting  
	// a message  that  her she's got. If  a message is deleted and it doesn't have tag "DELETED" then 
	// it gets tag "DELETED", but it's not actually deleted from the system; if a message with tag "DELETED" 
	// is deleted, then it's actually removed from the system

	// Services 

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	// Tests

	@Test
	public void testsUseCase23_2() {
		// Un usuario autenticado filtra sus mensajes por etiqueta (POSITIVO)
		this.driverListByTag("admin1", null);
		// Un usuario no autenticado filtra sus mensajes por etiqueta (NEGATIVO)
		this.driverListByTag(null, IllegalArgumentException.class);
		// Un usuario autenticado manda un mensaje
		this.driverSend("admin1", null);
		// Un usuario no autenticado manda un mensaje
		this.driverSend("admin1", IllegalArgumentException.class);
		// Un usuario autenticado elimina uno de sus mensajes
		this.driverDelete("admin1", "message1", null);
		// Un usuario autenticado elimina un mensaje que no es suyo
		this.driverDelete("company1", "message2", IllegalArgumentException.class);
	}

	// Driver

	private void driverListByTag(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Collection<Message> result = this.messageService.findMyMessagesByTag("TestTag");
			Assert.isTrue(result.size() == 1);
			for (final Message m : result)
				Assert.isTrue(m.getTags().contains("TestTag"));
		} catch (final Throwable oops) {
			caught = oops.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}

	private void driverSend(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Message newMessage = this.messageService.create();
			final Collection<String> tags = newMessage.getTags();
			final Actor receiver = this.actorService.findOne(super.getEntityId("hacker1"));
			tags.add("newTag");
			newMessage.setBody("body");
			newMessage.setReceiver(receiver);
			newMessage.setSubject("subject");
			newMessage.setTags(tags);
			final Message savedMessage = this.messageService.save(newMessage);
			Assert.notNull(this.messageService.findOne(savedMessage.getId()));
		} catch (final Throwable oops) {
			caught = oops.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}

	private void driverDelete(final String username, final String messageBeanId, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Message message = this.messageService.findOne(super.getEntityId(messageBeanId));
			this.messageService.delete(message);
			final Message deletedMessage = this.messageService.findOne(message.getId());
			Assert.notNull(deletedMessage);
			Assert.isTrue(deletedMessage.getTags().contains("DELETED"));
			this.messageService.delete(deletedMessage);
			Assert.isNull(this.messageService.findOne(deletedMessage.getId()));
		} catch (final Throwable oops) {
			caught = oops.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}
}