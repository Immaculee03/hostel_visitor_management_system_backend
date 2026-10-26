
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ShiftRepository
        extends JpaRepository<Shift, UUID> {
            
@Query("SELECT s FROM Shift s WHERE s.guards IS EMPTY")
List<Shift> findUnusedShifts();

}
