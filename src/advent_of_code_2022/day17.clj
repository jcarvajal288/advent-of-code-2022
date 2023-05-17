(ns advent-of-code-2022.day17
  (:require [advent-of-code-2022.util :refer :all]))

(def rocks [[[\. \. \# \# \# \# \.]]
            [[\. \. \. \# \. \. \.]
             [\. \. \# \# \# \. \.]
             [\. \. \. \# \. \. \.]]
            [[\. \. \. \. \# \. \.]
             [\. \. \. \. \# \. \.]
             [\. \. \# \# \# \. \.]]
            [[\. \. \# \. \. \. \.]
             [\. \. \# \. \. \. \.]
             [\. \. \# \. \. \. \.]
             [\. \. \# \. \. \. \.]]
            [[\. \. \# \# \. \. \.]
             [\. \. \# \# \. \. \.]]])

(def initial-chamber '([\. \. \. \. \. \. \.]
                       [\. \. \. \. \. \. \.]
                       [\. \. \. \. \. \. \.]))

(defn get-rock [step]
  (nth rocks (mod step (count rocks))))

(defn get-point [chamber x y]
  (nth (nth chamber y) x))

(defn print-chamber [chamber]
  (doseq [line chamber] (println line)))

; a line is supported if any of its rocks are directly above another rock
(defn unsupported? [line-index chamber]
  (let [rock-indicies (fn [line] (keep-indexed #(when (= %2 \#) %1) line))
        rock-locations (rock-indicies (nth chamber line-index))
        rock-locations-below (rock-indicies (nth chamber (inc line-index)))]
    (not-nil? (some #(contains? (set rock-locations) %) rock-locations-below))))

(defn enable-gravity [chamber]
  (doseq [line-index (count (butlast chamber))]
    (if (unsupported? line-index chamber)
      (let [line (nth chamber line-index)
            line-below (nth chamber (inc line-index))]
        (merge-line line line-below)))))

(defn drop-rock [chamber step]
  (let [rock (get-rock step)
        new-chamber (concat rock chamber)]
    (print-chamber new-chamber)))

(defn height-of-rock-tower [filename]
  (let [jet-pattern (first (get-resource-file-by-line filename))]
    (reduce drop-rock initial-chamber (range (count jet-pattern)))))