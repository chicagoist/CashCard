package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {
    private final CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {

        Optional<CashCard> cashCardOptional =
                cashCardRepository.findById(requestedId);

        if(cashCardOptional.isPresent()) {
            return ResponseEntity.ok(cashCardOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }


/*        if(requestedId.equals(99L)) {
            CashCard cashCard = new CashCard(99L, 123.45);
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }*/
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }


}

/*
6: Summary
Congratulations! In this lesson you learned how to use test driven development
to create your first Family Cash Card REST endpoint: a GET that returns a
CashCard of a certain ID.
* */

/*
8: Summary
You've now successfully refactored the way the Family Cash Card API manages
its data. Spring Data is now creating an in-memory H2 database and loading it
 with test data, which our tests utilize to exercise our API.

Furthermore, we didn't change any of our tests! They actually guided us to a
correct implementation. How awesome is that?!


Learning Moment: main vs test resources
Have you noticed that src/main/resources/schema.sql and
src/test/resources/data.sql are in different resources locations?

Can you guess why this is?

Remember that our Cash Card with ID 99 and Amount 123.45 is a fake, made-up
Cash Card that we only want to use in our tests. We don't want our "real" or
production system to load Cash Card 99 into the system... what would happen
to the real Cash Card 99?

Spring has provided a powerful feature for us: it allows us to separate our
test-only resources from our main resources when needed.

Our scenario here is a common example of this: our database schema is always
the same, but our data is not!

Thanks again, Spring!
* */

/*
8: Summary
In this lab you learned how simple it is to add another endpoint to our API
-- the POST endpoint. You also learned how to use that endpoint to create and
 save a new CashCard to our database using Spring Data. Not only that, but
 the endpoint accurately implements the HTTP POST specification, which we
 verified using test driven development. The API is starting to be useful!

 */