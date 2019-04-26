
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Integer> {

	@Query("select p from PersonalData p where p.curricula.id = ?1")
	PersonalData findPersonalDataByCurriculaId(int curriculaId);

	@Query("select p from PersonalData p where p.curricula.hacker.id = ?1 and p.original = true")
	Collection<PersonalData> findAllPersonalDatasByHackerId(int hackerId);

	@Query("select p from PersonalData p where p.curricula.hacker.id = ?1 and p.original = false")
	Collection<PersonalData> findAllCopiedPersonalDatasByHackerId(int hackerId);

}
