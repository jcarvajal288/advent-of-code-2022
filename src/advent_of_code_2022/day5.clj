(ns advent-of-code-2022.day5
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [clojure.core.matrix :as mtx]
            [taoensso.timbre :as log]
            ))

(defn parse-stacks [raw-data]
  (let [raw-stacks (reverse (take-while #(not= % "") raw-data))
        max-length (apply max (map count raw-stacks)) ]
    (->> raw-stacks
         (map #(pad max-length \space %))
         (mtx/transpose)
         (filter #(Character/isDigit ^char (first %)))
         (reduce (fn [state next]
                   (assoc state
                     (char-to-int (first next))
                     (filter #(Character/isUpperCase ^char %) (rest next))))
                 {}))))

(defn parse-instructions [raw-data start-row]
  (as-> (subvec raw-data start-row) data
        (map #(str/split % #" ") data)
        (map (fn [row] (filter is-number? row)) data)
        (map #(map str-to-int %) data)))

(defn move-crates [stacks instructions]
  (reduce (fn [state inst]
            (let [num-move (first inst)
                  source (nth inst 1)
                  dest (last inst)
                  moved-crates (reverse (take-last num-move (get state source)))
                  ]
              (as-> state st
                    (assoc st dest (concat (get st dest) moved-crates))
                    (assoc st source (drop-last num-move (get st source))))))
          stacks
          instructions))

(defn sort-values-by-key [stacks]
  (->> stacks
       (keys)
       (sort)
       (map #(get stacks %))))

(defn find-stack-tops [file]
  (let [raw-data (get-resource-file-by-line file)
        stacks (parse-stacks raw-data)
        instructions (parse-instructions raw-data (+ (count stacks) 1))
        final-stacks (move-crates stacks instructions)
        ]
    (->> final-stacks
         (sort-values-by-key)
         (map last)
         (str/join))))

