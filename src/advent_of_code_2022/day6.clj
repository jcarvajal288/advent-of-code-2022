(ns advent-of-code-2022.day6)

(defn- duplicates? [coll]
  (= (count coll) (count (distinct coll))))

(defn find-marker-position [signal marker-length]
  (let [reducer (fn [position rest-of-signal]
                  (if (duplicates? (take marker-length rest-of-signal))
                    (+ position marker-length)
                    (recur (+ position 1) (rest rest-of-signal))))]
    (reducer 0 signal)))
