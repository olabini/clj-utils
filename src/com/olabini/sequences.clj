(ns com.olabini.sequences)

(defn group-into [key f coll]
  (persistent!
   (reduce
    (fn [ret x]
      (let [k (key x)]
        (assoc! ret k (conj (get ret k []) (f x)))))
    (transient {}) coll)))
