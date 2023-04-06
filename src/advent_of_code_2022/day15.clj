(ns advent-of-code-2022.day15
  (:require [advent-of-code-2022.util :refer :all]
            [taoensso.timbre :as log]))

(import java.lang.Double)

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

(defn slope-intercept [p1 p2]
  (let [[x1 y1] p1
        [x2 y2] p2
        slope (/ (- y2 y1) (- x2 x1))
        intercept (- y1 (* slope x1))]
    [slope intercept]))

(defn radius-points [sensor radius]
  (let [[x y] sensor]
    [[(+ x radius) y]
     [(- x radius) y]
     [x (+ y radius)]
     [x (- y radius)]]))

(defn calculate-sensor-lines [sensor radius]
  (let [[right left top bottom] (radius-points sensor radius)]
    [(slope-intercept left top)
     (slope-intercept top right)
     (slope-intercept right bottom)
     (slope-intercept bottom left)]))

(defn find-intersection [[m1 b1] [m2 b2]]
  (let [x (if (= m1 m2)
            nil
            (/ (- b2 b1) (- m1 m2)))
        y (if (nil? x)
            nil
            (+ (* m1 x) b1))]
    [x y]))

(defn find-sensor-boundary-intersection-points [bound filename]
  (->> filename
       parse-sensor-map
       calculate-sensor-radii
       (map #(calculate-sensor-lines (first %) (last %)))
       (apply concat)
       all-pairs
       (map #(find-intersection (first %) (last %)))
       (filter (fn [i] (every? #(= (type %) Long) i)))
       (filter (fn [i] (every? #(and (>= % 0) (<= % bound)) i)))))

(defn is-target? [[x y] intersections]
  (some #(= [x (+ y 2)] %) intersections))

(defn find-beacon-location [bound filename]
  (let [intersections (find-sensor-boundary-intersection-points bound filename)]
    (filter #(is-target? % intersections) intersections)))