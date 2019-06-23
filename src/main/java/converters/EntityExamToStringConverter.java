
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EntityExam;

@Component
@Transactional
public class EntityExamToStringConverter implements Converter<EntityExam, String> {

	@Override
	public String convert(final EntityExam entityExam) {
		String result;

		if (entityExam == null)
			result = null;
		else
			result = String.valueOf(entityExam.getId());

		return result;
	}

}
