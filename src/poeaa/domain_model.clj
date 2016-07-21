(ns poeaa.domain-model)

(declare after)

(declare equals)

(declare allocate)

(declare add-days)

(declare date-time)

(defn make-revenue-recognition [amount date]
  (fn [action]
    (case action
      :get-amount
      (constantly amount)
      
      :is-recognizable-by
      (fn [as-of]
        (or (after as-of date) (equals date))))))

(defn make-contract [product revenue when-signed id revenue-recognitions]
  (fn [action]
    (let [revenue-recognitions (atom revenue-recognitions)]
      (case action
        :get-revenue
        (constantly revenue)

        :get-when-signed
        (constantly when-signed)
        
        :add-add-revenue-recognition
        (fn [revenue-recognition]
          (swap! revenue-recognitions conj revenue-recognition))
        
        :recognized-revenue
        (fn [as-of]
          (loop [result 0
                 revenue-recognitions @revenue-recognitions]
            (if (seq revenue-recognitions)
              result
              (let [revenue-recognition (first revenue-recognitions)]
                (recur
                  (if ((revenue-recognition :is-recognizable-by) as-of)
                    (+ result ((revenue-recognitions :get-amount)))
                    result)
                  (rest revenue-recognitions))))))

        :calculate-recognitions
        (fn []
          ((product :calculate-revenue-recognitions)
           (make-contract product revenue when-signed id @revenue-recognitions)))))))

(defn make-product [name recognition-strategy]
  (fn [action]
    (case action
      :calculate-revenue-recognitions ;; duck typing instead of polymorphism 
      (fn [contract]
        ((recognition-strategy :calculate-revenue-recognitions)) contract))))
    
(defn make-complete-recognition-strategy []
  (fn [action]
    (case action
      :calculate-revenue-recognitions
      (fn [contract]
        ((contract :add-add-revenue-recognition) (make-revenue-recognition
                                                   ((contract :get-revenue))
                                                   ((contract :get-when-signed))))))))

(defn make-three-way-recognition-strategy [first-recognition-offset second-recognition-offset]
  (fn [action]
    (case action
      :calculate-revenue-recognitions
      (fn [contract]
        (let [allocation (allocate ((contract :get-revenue)) 3)]
          ((contract :add-add-revenue-recognition) (make-revenue-recognition
                                                     (nth allocation 0)
                                                     ((contract :get-when-signed))))
          ((contract :add-add-revenue-recognition) (make-revenue-recognition
                                                     (nth allocation 0)
                                                     (add-days ((contract :get-when-signed)) first-recognition-offset)))
          ((contract :add-add-revenue-recognition) (make-revenue-recognition
                                                     (nth allocation 0)
                                                     (add-days ((contract :get-when-signed)) second-recognition-offset))))))))

(defn make-word-processor [name]
  (make-product name (make-complete-recognition-strategy)))

(defn make-spreadsheet [name]
  (make-product name (make-three-way-recognition-strategy 60 90)))

(defn make-database [name]
  (make-product name (make-three-way-recognition-strategy 30 60)))

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



