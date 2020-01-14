(ns pharmacy-system.core)

(def paracetamol "Paracetamol")
(def ibuprofen "Ibuprofen")
(def syringe "Syringe")
(def scalpel "Scalpel")
(def dispensary "Dispensary")
(def icu "ICU")
(def warehouse "Warehouse")

(defn get-medical-items []
  (vec '(paracetamol ibuprofen syringe scalpel)))

(defn add-quantities [quantities]
  (zipmap (get-medical-items) quantities))

(defn create-initial-for-location [location quantities]
  (hash-map location (add-quantities quantities)) )

(defn create-initials []
  (conj
    (create-initial-for-location warehouse [10 50 80 100])
    (create-initial-for-location icu [0 20 7 8])
    (create-initial-for-location dispensary [50 33 10 80])
    ))


