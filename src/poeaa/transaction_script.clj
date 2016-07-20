(ns poeaa.transaction-script)

(declare find-contract)

(declare allocate)

(declare insert-recognition)

(declare add-days)

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
