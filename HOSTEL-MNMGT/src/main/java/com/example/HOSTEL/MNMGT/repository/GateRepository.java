
package com.example.HOSTEL.MNMGT.repository;

import com.example.HOSTEL.MNMGT.domain.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface GateRepository extends JpaRepository<Gate, UUID> {
      Optional<Gate> findByGateNumber(int gateNumber);
    @Query("SELECT g FROM Gate g WHERE NOT EXISTS " +
           "(SELECT guard FROM Guard guard WHERE guard.gate = g) " +
           "ORDER BY g.gateNumber ASC")
    List<Gate> findFreeGates();


}
