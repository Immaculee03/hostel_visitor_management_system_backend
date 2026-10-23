
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GateRepository
        extends JpaRepository<Gate, UUID> {
}
