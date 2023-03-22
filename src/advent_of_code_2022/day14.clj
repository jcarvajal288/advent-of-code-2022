(ns advent-of-code-2022.day14
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]))

(defn initialize-2d-vector [width height value]
  (vec (repeat height (vec (repeat width value)))))

(defn set-2d-coord [grid coord value]
  (let [x (first coord)
        y (last coord)
        old-row (nth grid y)
        new-row (assoc old-row x value)]
    (assoc grid y new-row)))

(defn get-2d-coord [grid coord]
  (nth (nth grid (last coord)) (first coord)))

(defn parse-rock-paths [filename]
  (->> (get-resource-file-by-line filename)
       (map #(str/split % #" -> "))
       (map (fn [str-coords]
              (->> str-coords
                   (map #(str/split % #","))
                   (map #(map str-to-int %)))))))

(defn find-dimension [rock-paths dimension]
  (let [position (if (= dimension :width) first last)]
    (->> rock-paths
         (map #(map position %))
         (flatten)
         (apply max))))

(defn print-map [rock-map]
  (for [line rock-map]
    (println (apply str line))))

(defn get-all-points-between [p1 p2]
  (if (= (first p1) (first p2))
    (if (<= (last p1) (last p2))
      (rest (map
              (fn [y] [(first p1) y])
              (range (last p1) (inc (last p2)))))
      (map
        (fn [y] [(first p1) y])
        (range (last p1) (dec (last p2)) -1)))
    (if (<= (first p1) (first p2))
      (rest (map
              (fn [x] [x (last p1)])
              (range (first p1) (inc (first p2)))))
      (map
        (fn [x] [x (last p1)])
        (range (first p1) (dec (first p2)) -1)))))

(defn expand-path [path]
  (reduce (fn [expanded-path coord]
            (concat expanded-path (get-all-points-between (last expanded-path) coord)))
          [(first path)]
          (rest path)))

(defn apply-rock-path [rock-map rock-path]
  (let [expanded-path (expand-path rock-path)]
    (reduce (fn [rock-map coord]
              (set-2d-coord rock-map coord \#))
            rock-map
            expanded-path)))

(defn apply-rock-paths [rock-paths rock-map]
  (reduce (fn [rock-map rock-path]
            (apply-rock-path rock-map rock-path))
          rock-map
          rock-paths))

(defn build-map [filename]
  (let [rock-paths (parse-rock-paths filename)
        width (find-dimension rock-paths :width)
        height (find-dimension rock-paths :height)]
    (->> (initialize-2d-vector (inc width) (inc height) \.)
         (apply-rock-paths rock-paths))))

(defn drop-sand [rock-map sand-coord]
  (let [x (first sand-coord)
        y (last sand-coord)
        down-left [(dec x) (inc y)]
        down [x (inc y)]
        down-right [(inc x) (inc y)]
        is-air? #(= % \.)]
    (cond
      (>= (inc y) (count rock-map)) :void-reached
      (is-air? (get-2d-coord rock-map down)) (drop-sand rock-map down)
      (is-air? (get-2d-coord rock-map down-left)) (drop-sand rock-map down-left)
      (is-air? (get-2d-coord rock-map down-right)) (drop-sand rock-map down-right)
      :else (set-2d-coord rock-map sand-coord \o))))

(defn run-simulation [sand-outlet filename]
  (let [rock-map (build-map filename)]
    (reduce (fn [rock-map num-sand]
              (if (= rock-map :void-reached)
                (reduced (dec num-sand))
                (drop-sand rock-map sand-outlet)))
            rock-map
            (range))))