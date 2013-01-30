(ns com.olabini.sequences)

(defn group-into
  "Returns a map of the elements of coll keyed by the result of
   keyfn on each element. The value at each key will be a collection
   based on the start-element given with each entry being the result
   of calling valfn on the value. In the basic case with only a
   keyfn and coll given, will do the same thing as group-by.

      (group-into count second [[1 2 3]
                                [3 2 1]
                                [4 5 6]
                                [7 8]
                                [1 2 3 4 5]
                                [5 5 5 5 5]])
             ;=> {3 [2 2 5], 2 [8], 5 [2 5]}

  with an explicit start value:

      (group-into count second #{} [[1 2 3]
                                    [3 2 1]
                                    [4 5 6]
                                    [7 8]
                                    [1 2 3 4 5]
                                    [5 5 5 5 5]])
             ;=> {3 #{2 5}, 2 #{8}, 5 #{2 5}}
"
  ([keyfn coll] (group-into keyfn identity [] coll))
  ([keyfn valfn coll] (group-into keyfn valfn [] coll))
  ([keyfn valfn start coll]
     (persistent!
      (reduce
       (fn [ret x]
         (let [k (keyfn x)]
           (assoc! ret k (conj (get ret k start) (valfn x)))))
       (transient {}) coll))))
