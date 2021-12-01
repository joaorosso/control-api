package api.control.controller;

import api.control.entity.Account;
import api.control.entity.Entry;
import api.control.repository.EntryRepository;
import api.control.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public Entry create(@RequestBody Entry entry, @RequestHeader (name="Authorization") String token) {
        UUID accountId = tokenService.getSubjectId(token);
        entry.setAccount(new Account(accountId));
        return entryRepository.save(entry);
    }

    @GetMapping
    public List<Entry> get(@RequestHeader (name="Authorization") String token) {
        UUID accountId = tokenService.getSubjectId(token);
        return entryRepository.findByAccountId(accountId);
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
