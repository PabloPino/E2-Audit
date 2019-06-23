/*
 * StringToEntityExamConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EntityExamRepository;
import domain.EntityExam;

@Component
@Transactional
public class StringToEntityExamConverter implements Converter<String, EntityExam> {

	@Autowired
	EntityExamRepository	entityExamRepository;


	@Override
	public EntityExam convert(final String text) {
		EntityExam result;
		int id;

		try {
			if (text == "")
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.entityExamRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
