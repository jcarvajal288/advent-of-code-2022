(ns advent-of-code-2022.day6)

(defn- duplicates? [coll]
  (= (count coll) (count (distinct coll))))

(defn find-marker-position [signal]
  (let [reducer (fn [position rest-of-signal]
                  (if (duplicates? (take 4 rest-of-signal))
                    (+ position 4)
                    (recur (+ position 1) (rest rest-of-signal))))]
    (reducer 0 signal)))
