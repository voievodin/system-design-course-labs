/**
 * AccountController.java
 *
 * @author Kostyantin Solod
 * Date of creation: 25-Apr-2023 21:15
 */

package fotius.example.donations.account.controller;

import fotius.example.donations.account.domain.AccountService;
import fotius.example.donations.account.domain.model.Account;
import fotius.example.donations.payment.domain.model.Currency;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {
   private final AccountService accountService;

   public AccountController(AccountService accountService) {
      this.accountService = accountService;
   }

   @PostMapping("/open")
   @ResponseBody
   public Account open(@RequestBody String currency) {
      return accountService.open(Currency.valueOf(currency));
   }
   @PostMapping("/close")
   @ResponseBody
   public Account close(@RequestBody String currency) {
      return accountService.close(Currency.valueOf(currency));
   }
   @GetMapping("/find/all")
   @ResponseBody
   public List<Account> findAll() {
      return accountService.findAll();
   }

   @GetMapping("/find/currency/{currency}")
   @ResponseBody
   public Account findByCurrency(@PathVariable("currency") String currency) {
      return accountService.findByCurrency(Currency.valueOf(currency.toUpperCase()));
   }

}
