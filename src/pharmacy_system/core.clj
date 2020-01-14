(ns pharmacy-system.core)

(def paracetamol "Paracetamol")
(def ibuprofen "Ibuprofen")
(def syringe "Syringe")
(def scalpel "Scalpel")
(def dispensary "Dispensary")
(def icu "ICU")
(def warehouse "Warehouse")

(defn get-medical-items []
  (vec [paracetamol ibuprofen syringe scalpel]))

(defn add-quantities [quantities]
  (zipmap (get-medical-items) quantities))

(defn create-initial-for-location [location quantities]
  (hash-map location (add-quantities quantities)))

(defn create-initials []
  (conj
    (create-initial-for-location warehouse [10 50 80 100])
    (create-initial-for-location icu [0 20 7 8])
    (create-initial-for-location dispensary [50 33 10 80])
    ))


(defn create-transaction [from-location to-location medical-item quantity]
  (hash-map :from from-location, :to to-location, :item medical-item, :quantity quantity))

(defn create-transaction-logs []
  (conj []
        (create-transaction warehouse dispensary paracetamol 2)
        (create-transaction warehouse dispensary ibuprofen 5)
        (create-transaction dispensary icu syringe 2)
        (create-transaction warehouse icu scalpel 10)
        ))


(defn update-items-of-location [initials curr-transaction location operator]
  (update-in initials
             [location (curr-transaction :item)]
             operator (curr-transaction :quantity)
             )
  )

(defn update-items [initials curr-transactions]
  (let [from-location (curr-transactions :from)
        to-location (curr-transactions :to)]
    (let [new-initials (update-items-of-location initials curr-transactions from-location -)]
      (update-items-of-location new-initials curr-transactions to-location +))
    )
  )

(defn calculate-final-items [initials transactions]
  (loop [initials initials
         transactions transactions]
    (let [curr-transaction (first transactions)]
      (if (empty? transactions)
        initials
        (recur
          (update-items initials curr-transaction)
          (rest transactions)
          )))))

(calculate-final-items (create-initials) (create-transaction-logs))