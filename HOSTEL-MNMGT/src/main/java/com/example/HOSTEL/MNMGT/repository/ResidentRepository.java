
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface ResidentRepository
        extends JpaRepository<Resident, UUID> {
            
List<Resident> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

}

