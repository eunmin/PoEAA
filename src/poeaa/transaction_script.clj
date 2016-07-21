(ns poeaa.transaction-script)

(declare find-contract)

(declare allocate)

(declare insert-recognition)

(declare add-days)

(declare next)

(declare find-recognitions-for)

(declare date-time)

(defn calculate-revenue-recognitions [contract-number]
  (let [contracts (find-contract contract-number)
        total-revenue (:revenue contracts)
        recognition-date (:date-signed contracts)
        type (:type contracts)]
    (case type
      "S" (let [allocation (allocate total-revenue 3)]
            (insert-recognition contract-number (nth allocation 0) recognition-date)
            (insert-recognition contract-number (nth allocation 1) (add-days recognition-date 60))
            (insert-recognition contract-number (nth allocation 2) (add-days recognition-date 90)))
      "W" (insert-recognition contract-number total-revenue recognition-date)
      "D" (let [allocation (allocate total-revenue 3)]
            (insert-recognition contract-number (nth allocation 0) recognition-date)
            (insert-recognition contract-number (nth allocation 1) (add-days recognition-date 30))
            (insert-recognition contract-number (nth allocation 2) (add-days recognition-date 60))))))
          
(defn recognized-revennue [contract-number as-of]
  (loop [result 0
         rs (find-recognitions-for contract-number as-of)]
    (if rs
        (recur (+ result (:amount rs)) (next rs))
        result)))
      
(calculate-revenue-recognitions 12322)

(recognized-revennue 12322 (date-time 2015 12 25))
