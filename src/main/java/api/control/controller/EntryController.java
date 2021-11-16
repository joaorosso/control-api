package api.control.controller;

import api.control.entity.Entry;
import api.control.repository.EntryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    private EntryRepository entryRepository;

    @PostMapping
    public Entry create(@RequestBody Entry entry) {
        return entryRepository.save(entry);
    }

    @GetMapping
    public List<Entry> get() {
        return entryRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entry> put(@PathVariable UUID id, @RequestBody Entry newEntry) {
        Entry entry = entryRepository.findById(id).orElse(null);
        if (entry == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BeanUtils.copyProperties(newEntry, entry, "id");
        entryRepository.save(entry);
        return ResponseEntity.ok(entry);
    }

    @GetMapping("/{id}")
    public Entry getById(@PathVariable UUID id) {
        Entry entry = entryRepository.findById(id).orElse(null);
        if (entry == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return entryRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        Entry entry = entryRepository.findById(id).orElse(null);
        if (entry == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        entryRepository.deleteById(id);
    }
}
