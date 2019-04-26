
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Hacker;

@Component
@Transactional
public class HackerToStringConverter implements Converter<Hacker, String> {

	@Override
	public String convert(final Hacker h) {
		String result;

		if (h == null)
			result = null;
		else
			result = String.valueOf(h.getId());

		return result;
	}

}
