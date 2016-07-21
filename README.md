# poeaa

Transaction Script and Domain Model

## Transaction Example

```clojure
(def word-contract-number 12322)

(calculate-revenue-recognitions word-contract-number)

(recognized-revennue word-contract-number (date-time 2015 12 25))
```

## Domain Model

```clojure
;; word
(def word (make-word-processor "Thinking Word"))

(def contract-word1 (make-contract word 70000 (date-time 2015 10 14) 17432 []))

((word :calculate-revenue-recognitions) contract-word1)

((contract-word1 :recognized-revenue) (date-time 2015 05 10))

;; spreadsheet
(def calc (make-spreadsheet "Thinking Calc"))

(def contract-calc1 (make-contract word 120000 (date-time 2015 4 14) 34344 []))

((calc :calculate-revenue-recognitions) contract-calc1)

((contract-calc1 :recognized-revenue) (date-time 2015 10 10))

;; db
(def db (make-database "Thinking DB"))

(def contract-db1 (make-contract word 90000 (date-time 2015 7 14) 23433 []))

((db :calculate-revenue-recognitions) contract-db1)

((contract-db1 :recognized-revenue) (date-time 2015 12 23))
```
