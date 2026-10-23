
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Guard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GuardRepository
        extends JpaRepository<Guard, UUID> {
}

