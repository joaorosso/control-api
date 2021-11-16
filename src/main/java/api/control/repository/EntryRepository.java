package api.control.repository;

import api.control.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID> {
}
