# RESTfulTdd
Simple RESTful API implemented to practice TDD.

This Project is my very first attempt at coding with TDD outside of the scope of Textbook Case.

Coming from a Telecommunication background, I have always been passionate about Clean architecture, modularity and separation of responsabilities.
However as easy as it is to contemplate a beautiful architecture, building one from scratch is a different story.

The idea behind learning TDD is to have an automatic coverage and decoupled implementation, 
that would allow me to refactor at will.
Ultimately allowing me to experient with the teachings of code crafting masters such as Uncle bob, Kent beck, Martin fowler.

But we're looking at lots of years down the line, for now : lots of words for not much . . . . what we're looking at here is the most basic server you could think of ^_^

## Specs
PUT /transactionservice/transaction/$transaction_id <br/>
==> "{ "status": "ok" } (Save transaction on server)"

GET /transactionservice/transaction/$transaction_id <br/>
==> "{ "amount":double,"type":string,"parent_id":long }"

GET /transactionservice/types/$type <br/>
==> "[ long, long, .... ]" (list of transactions id of a specific type)

GET /transactionservice/sum/$parent_id <br/>
==> "{ "sum", double }" (Sum of all the transaction sharing the same parent)
