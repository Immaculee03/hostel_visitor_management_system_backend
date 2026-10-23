
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ShiftRepository
        extends JpaRepository<Shift, UUID> {
}
