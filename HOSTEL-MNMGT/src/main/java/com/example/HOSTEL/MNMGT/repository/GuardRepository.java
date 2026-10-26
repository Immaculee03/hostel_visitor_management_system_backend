
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Guard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuardRepository
        extends JpaRepository<Guard, UUID> {
            
@Query("SELECT DISTINCT g FROM Guard g JOIN g.shifts s " +
       "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
List<Guard> findByShiftNameContainingIgnoreCase(@Param("name") String name);

}

