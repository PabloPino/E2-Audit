
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;
import domain.PersonalData;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c from Curricula c where c.hacker.id = ?1 and c.original = true")
	Collection<Curricula> findCurriculasByHackerId(int hackerId);

	@Query("select c from Curricula c where c.hacker.id = ?1 and c.original = false")
	Collection<Curricula> findCopiedCurriculasByHackerId(int hackerId);

	@Query("select a.curricula from Application a where a.id = ?1")
	Curricula findCurriculasByApplicationId(int hackerId);

	@Query("select c from Curricula c where c.hacker.id = ?1")
	Collection<Curricula> findSimplyCurriculasByHackerId(int hackerId);

	@Query("select pd from PersonalData pd where pd.curricula.id = ?1")
	PersonalData findPersonalFromCurricula(int curriculaId);

	//QUERY DASHBOARD---------------------------------------------------------------------------
	@Query("select avg(1.0 * (select count(c) from 	Curricula c where c.hacker.id = h.id)) from Hacker h")
	Double queryB1AVG();

	@Query("select max(1.0 * (select count(c) from 	Curricula c where c.hacker.id = h.id)) from Hacker h")
	Double queryB1MAX();

	@Query("select min(1.0 * (select count(c) from 	Curricula c where c.hacker.id = h.id)) from Hacker h")
	Double queryB1MIN();

	@Query("select stddev(1.0 * (select count(c) from Curricula c where c.hacker.id = h.id)) from Hacker h")
	Double queryB1STDDEV();

}
