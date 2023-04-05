(ns advent-of-code-2022.day15
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]))

(defn parse-sensor-map [filename]
  (let [lines (get-resource-file-by-line filename)]
    (->> lines
         (map #(re-seq #"-?\d+" %))
         (map #(map str-to-int %))
         (map #(partition 2 %)))))

(defn manhattan-distance [p1 p2]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (+ (abs (- x1 x2)) (abs (- y1 y2)))))

(defn calculate-sensor-radii [sensor-map]
  (->> sensor-map
       (map (fn [line]
              (let [distance (manhattan-distance (first line) (last line))]
              [(first line) (last line) distance])))))

(defn points-on-row-within-sensor-range [row sensor-radius]
  (let [sensor (first sensor-radius)
        radius (last sensor-radius)
        distance-from-row (abs (- row (last sensor)))
        min-x (- (first sensor) (- radius distance-from-row))
        max-x (+ (first sensor) (- radius distance-from-row))]
    (range min-x (inc max-x))))

(defn count-beaconless-positions-in-row [row filename]
  (let [sensor-radii (calculate-sensor-radii (parse-sensor-map filename))
        beacons (map #(nth % 1) sensor-radii)
        points-in-sensor-range (->> sensor-radii
                                    (map (partial points-on-row-within-sensor-range row))
                                    (flatten)
                                    (distinct))
        beacons-on-row (->> beacons
                            (filter #(= (last %) row))
                            (distinct)
                            (map first)
                            (filter #(coll-contains? % points-in-sensor-range)))]
    (- (count points-in-sensor-range) (count beacons-on-row))))