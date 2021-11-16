package api.control.controller;

import api.control.entity.Account;
import api.control.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> put(@PathVariable UUID id, @RequestBody Account newAccount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        BeanUtils.copyProperties(newAccount, account, "id");
        accountRepository.save(account);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable UUID id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return accountRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        accountRepository.deleteById(id);
    }
}
